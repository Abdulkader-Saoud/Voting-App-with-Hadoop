package org.saoud;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.conf.Configuration;

public class wcMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private String jobName;

    public void setup(Context context) {
        Configuration conf = context.getConfiguration();
        jobName = conf.get("job.name");
    }
    private final static IntWritable one = new IntWritable(1);
    private Text name = new Text();
    private IntWritable age = new IntWritable();

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();

        String[] fields = line.split(",");

        String nameString = fields[0];
        String countryString = fields[2];

        if (jobName.compareTo("1") == 0 || jobName.compareTo("5") == 0){
            name.set(nameString);
            context.write(name, one);
        }
        else if (jobName.compareTo("2") == 0 || jobName.compareTo("4") == 0 ){
            name.set(nameString);
            age.set(Integer.parseInt(fields[1]));
            context.write(name, age);
        }
        else if (jobName.compareTo("3") == 0){
            name.set(nameString+ " : " +countryString);
            context.write(name, one);
        }

    }
}
