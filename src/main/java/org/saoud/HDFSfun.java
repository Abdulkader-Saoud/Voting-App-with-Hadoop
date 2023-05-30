package org.saoud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HDFSfun {
    public static void deleteDir(String dirPath){
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://namenode:9000");

            FileSystem fs = FileSystem.get(conf);
            Path mPath = new Path(dirPath);
            if (!fs.exists(mPath)){
                System.out.println("There is no Dir with this name !");
                return;
            }
            fs.delete(mPath,true);
            System.out.println("All content deleted from HDFS.");

            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setInput(String inPath){
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://namenode:9000");

            FileSystem fs = FileSystem.get(conf);
            Path directoryPath = new Path("/input");
            if (fs.exists(directoryPath)) {
                fs.delete(directoryPath, true);
                System.out.println("Old input Directory was deleted from HDFS: ");
            }
            fs.copyFromLocalFile(new Path(inPath)
                    , new Path("/input/"));

            System.out.println("File uploaded to HDFS successfully.");

            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readFileFromHDFS(JTextArea outputTextArea, String job) {
        try {

            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://namenode:9000");

            FileSystem fileSystem = FileSystem.get(conf);
            String pathS = "/output" + job +  "/part-r-00000";
            Path hdfsReadPath = new Path(pathS);
            FSDataInputStream inputStream = fileSystem.open(hdfsReadPath);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String s;
            outputTextArea.append("  The hadoop output :\n");
            while ((s = bufferedReader.readLine()) != null) {
                outputTextArea.append(s + "\n");
            }

            bufferedReader.close();
            fileSystem.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static boolean checkOutput(String job) {
        try {
            Configuration conf = new Configuration();
            String pathS = "/output" + job +  "/part-r-00000";
            Path path = new Path(pathS);
            conf.set("fs.defaultFS", "hdfs://namenode:9000");
            FileSystem fs = FileSystem.get(conf);
            if (fs.exists(path)){
                fs.close();
                return true;
            }
            return false;
        } catch (IOException ex) {

            ex.printStackTrace();
            return false;
        }
    }
}
