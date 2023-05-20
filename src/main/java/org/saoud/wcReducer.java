package org.saoud;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;


public class wcReducer extends Reducer<Text, IntWritable, Text, Text> {
    private String jobName;
    private Text text = new Text();
    private Map<Text, Text> candidateVotes;

    @Override
    public void setup(Context context) {
        Configuration conf = context.getConfiguration();
        jobName = conf.get("job.name");
        candidateVotes = new HashMap<>();
    }
    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum =  0;
        if (jobName.compareTo("1") == 0 || jobName.compareTo("3") == 0 || jobName.compareTo("5") == 0){
            sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            text.set(String.valueOf(sum));
        }
        else if (jobName.compareTo("2") == 0){
            int max = 0;
            for (IntWritable value : values) {
                if (value.get() > max)
                    max = value.get();
            }
            text.set(String.valueOf(max));
        }
        else if (jobName.compareTo("4") == 0){
            int sumAge = 0;
            int count = 0;
            for (IntWritable value : values) {
                sumAge += value.get();
                count++;
            }
            double avr = (sumAge / (double)count );
            text.set(String.format("%.2f", avr) + "%");
        }
        if (jobName.compareTo("5") == 0)
            candidateVotes.put(new Text(key), new Text(String.valueOf(sum)));
        else
            context.write(key, text);
    }
    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        if (jobName.equals("5")){
            int total = 0;
            for (Text voteCount : candidateVotes.values()) {
                total += Integer.parseInt(voteCount.toString());
            }

            for (Map.Entry<Text, Text> entry : candidateVotes.entrySet()) {
                Text candidate = entry.getKey();
                int voteCount = Integer.parseInt(entry.getValue().toString());
                double per = (voteCount / (double) total) * 100;

                text.set(String.format("%.2f", per) + "%");
                context.write(candidate, text);
            }
        }
    }
}
