package br.com.socialbase.ml.digester.services.ml.classification;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.linalg.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NaiveBayes {

    NaiveBayesModel model;

    private static SparkConf conf = new SparkConf()
            .setAppName("Naive Bayes")
            .setMaster("spark://luiz-Inspiron-3542:7077");

    private static JavaSparkContext jsc = new JavaSparkContext(conf);

    public void train(List<LabeledPoint> trainList){

        JavaRDD<LabeledPoint> training = jsc.parallelize(trainList, 2).cache();

        model = org.apache.spark.mllib.classification.NaiveBayes.train(training.rdd(), 1.0);
    }

    public double classify(Vector testData){
        return model.predict(testData);
    }

    public static void main(String [] args){

        NaiveBayes naiveBayes = new NaiveBayes();

        HashingTF tf = new HashingTF();

        List<LabeledPoint> labeledPoints = new ArrayList<>();

        String transito1 = "Estou dirigindo na highway";
        String transito2 = "Trânsito na highway";
        String naoTransito1 = "Na sala de aula";
        String naoTransito2 = "Começando aula de Java";

        labeledPoints.add(new LabeledPoint(1, tf.transform(Arrays.asList(transito1.split(" ")))));
        labeledPoints.add(new LabeledPoint(1, tf.transform(Arrays.asList(transito2.split(" ")))));
        labeledPoints.add(new LabeledPoint(0, tf.transform(Arrays.asList(naoTransito1.split(" ")))));
        labeledPoints.add(new LabeledPoint(0, tf.transform(Arrays.asList(naoTransito2.split(" ")))));

        naiveBayes.train(labeledPoints);

        System.out.println(naiveBayes.classify(tf.transform(Arrays.asList("Acho que vou pela highway".split(" ")))));
        System.out.println(naiveBayes.classify(tf.transform(Arrays.asList("Aula de Java é a melhor".split(" ")))));
        System.out.println(naiveBayes.classify(tf.transform(Arrays.asList("Vou para aula de Java pela highway".split(" ")))));

    }
}
