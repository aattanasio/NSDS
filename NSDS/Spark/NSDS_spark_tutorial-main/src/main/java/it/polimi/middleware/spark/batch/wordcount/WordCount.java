package it.polimi.middleware.spark.batch.wordcount;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class WordCount {

    public static void main(String[] args) {
        final String master = args.length > 0 ? args[0] : "local[4]"; // local environment with four threads
        final String filePath = args.length > 1 ? args[1] : "./";

        final SparkConf conf = new SparkConf().setMaster(master).setAppName("WordCount");
        final JavaSparkContext sc = new JavaSparkContext(conf);

        final JavaRDD<String> lines = sc.textFile(filePath + "files/wordcount/in.txt");
        final JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        final JavaPairRDD<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        // given two different values for the same key, we reduce those values by doing the sum of them
        final JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
        // .collect is an action (returns values to the driver program)
        System.out.println(counts.collect());

        sc.close();
    }

}