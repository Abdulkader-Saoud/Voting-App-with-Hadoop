package org.saoud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.log4j.PropertyConfigurator;

public class Runner {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Path inputPath =  new Path(args[1]);

        conf.set("job.name", args[0]);
        PropertyConfigurator.configure("C:/Users/abdul/OneDrive/Desktop/YTU/BVIA/Proje/log4j.xml");
        Job job = Job.getInstance(conf, "VotesCounterApp");

        job.setJarByClass(Runner.class);
        job.setReducerClass(Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setMapperClass(Mapper.class);
        job.setOutputValueClass(IntWritable.class);


        FileInputFormat.addInputPath(job,inputPath);
        FileOutputFormat.setOutputPath(job, new Path(args[2]));


        boolean result = job.waitForCompletion(true);
        if (result) {
            System.out.println("Job completed successfully.");
        } else {
            System.out.println("Job failed.");
        }
    }
}