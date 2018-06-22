package br.com.socialbase.ml.digester.services.ml.groupping;

import br.com.socialbase.ml.digester.model.Post;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.linalg.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMeansSocialbase {

    private static SparkConf conf;

    private static JavaSparkContext jsc;

    public KMeansSocialbase(){
        conf = new SparkConf()
                .setAppName("Grouping")
                .setMaster("spark://luiz-Inspiron-3542:7077");
        jsc = new JavaSparkContext(conf);
    }

    private static HashingTF tf = new HashingTF();

    private static KMeansModel clusters = null;

    public void train(List<Post> posts){

        List<Vector> vectors = new ArrayList<>();

        for(Post post:posts){
            vectors.add(tf.transform(Arrays.asList(post.getBody().split(" "))));
        }

        JavaRDD<Vector> training = jsc.parallelize(vectors, 2).cache();


        clusters = KMeans.train(training.rdd(), 5, 5);
    }

    public int predict(Post post){
        return clusters.predict(tf.transform(Arrays.asList(post.getBody().split(" "))));
    }

    private void saveModel(){
        /*

        System.out.println("Cluster centers:");
        for (Vector center: clusters.clusterCenters()) {
            System.out.println(" " + center);
        }
        double cost = clusters.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

        // Save and load model
        clusters.save(jsc.sc(), "target/org/apache/spark/JavaKMeansExample/KMeansModel");
        KMeansModel sameModel = KMeansModel.load(jsc.sc(),
                "target/org/apache/spark/JavaKMeansExample/KMeansModel");
                */
    }
}
