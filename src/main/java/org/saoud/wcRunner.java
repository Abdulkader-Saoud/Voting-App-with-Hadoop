package org.saoud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.log4j.PropertyConfigurator;

public class wcRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("hoooooooo");
        Configuration conf = new Configuration();
        Path inputPath;

        conf.set("job.name", args[0]);
        PropertyConfigurator.configure("C:/Users/abdul/OneDrive/Desktop/YTU/BVIA/Proje/log4j.xml");
        Job job = Job.getInstance(conf, "wordcount");
        job.setJarByClass(wcRunner.class);
        job.setReducerClass(wcReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setMapperClass(wcMapper.class);
        job.setOutputValueClass(IntWritable.class);
        inputPath =  new Path(args[1]);
        /*
        if (args[0].equals("5")){
            if (!checkOutput("1"))
                hadoopJob("1","/input/input1.txt","/output1");
            inputPath = new Path("/output1/part-r-00000");
        }else {

        }
        */
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
