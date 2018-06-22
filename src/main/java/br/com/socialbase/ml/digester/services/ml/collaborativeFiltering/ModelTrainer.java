package br.com.socialbase.ml.digester.services.ml.collaborativeFiltering;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.*;

public class ModelTrainer {
    private static ModelTrainer ourInstance = new ModelTrainer();
    public static ModelTrainer getInstance() {
        return ourInstance;
    }

    private static SparkConf conf = new SparkConf()
            .setAppName("Collaborative Filtering")
            .setMaster("spark://luiz-Inspiron-3542:7077");

    private ModelTrainer() {
    }

    private static JavaRDD<Rating> ratings = null;
    private static MatrixFactorizationModel model = null;


    private static JavaSparkContext jsc = new JavaSparkContext(conf);

    public static void train(List<Rating> likes){

        ratings = jsc.parallelize(likes);

        int rank = 10;
        int numIterations = 10;
        double alpha = 0.01;
        double lambda = 0.01;

        model = ALS.trainImplicit(JavaRDD.toRDD(ratings), rank, numIterations, alpha, lambda);
    }

    public static List<Integer> recommend(Integer user) {

        /*
        // Evaluate the model on rating data
        JavaRDD<Tuple2<Object, Object>> userProducts =
                ratings.map(r -> new Tuple2<>(r.user(), r.product()));

        JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD.fromJavaRDD(
                model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
                        .map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating()))
        );*/

        List<Integer> recommendations = new ArrayList<>();

        try {

            Rating[] ratings = model.recommendProducts(user, 50);

            for (Rating recommendProduct : Arrays.asList(ratings)) {
                recommendations.add(recommendProduct.product());
            }
        }catch(Exception e){
            //e.printStackTrace();
        }

        return recommendations;
        /*

        JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD.fromJavaRDD(
                ratings.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())))
                .join(predictions).values();

        double MSE = ratesAndPreds.mapToDouble(pair -> {
            double err = pair._1() - pair._2();
            return err * err;
        }).mean();

        System.out.println("Mean Squared Error = " + MSE);

        return  null;
        */
    }

    private void saveModel(){

        // Save and load model
        model.save(jsc.sc(), "target/tmp/myCollaborativeFilter");
        MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),
                "target/tmp/myCollaborativeFilter");

    }
}
