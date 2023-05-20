package org.saoud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ButtonCom extends JButton {

    public ButtonCom(String text,String job){
        super(text);


        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!checkOutput(job)){
                        hadoopJob(job, "/input", "/output" + job);

                    }
                    showOut(job);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public static void showOut(String job){
        JTextArea outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        readFileFromHDFS(outputTextArea,job);
        JFrame frame = new JFrame("Hadoop Output");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }
    public static void hadoopJob(String job,String inputPath,String outputPath) throws IOException {
        String hadoopHome = "C:\\big-data\\hadoop-3.3.0";
        String command = "hadoop jar C:\\Users\\abdul\\IdeaProjects\\WordCount\\target\\WordCount-1.0-SNAPSHOT.jar org.saoud.wcRunner " + job + " " + inputPath + " " + outputPath;
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        Map<String, String> env = pb.environment();
        env.put("HADOOP_HOME", hadoopHome);
        Process p = pb.start();

        try (BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
            String s;
            while ((s = stdError.readLine()) != null) {
                System.err.println(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public static void readFileFromHDFS(JTextArea outputTextArea, String job) {
        try {

            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://192.168.230.1:9000");

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
            conf.set("fs.defaultFS", "hdfs://192.168.230.1:9000");
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

