package org.saoud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.saoud.HDFSfun.checkOutput;
import static org.saoud.HDFSfun.readFileFromHDFS;

public class ButtonCom extends JButton {

    public ButtonCom(String text,String job){
        super(text);
        setPreferredSize(new Dimension(400, 35));
        setMaximumSize(new Dimension(400, 35));
        setAlignmentX(Component.CENTER_ALIGNMENT);

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
        String command = "hadoop jar C:\\Users\\abdul\\IdeaProjects\\WordCount\\target\\WordCount-1.0-SNAPSHOT.jar org.saoud.Runner " + job + " " + inputPath + " " + outputPath;
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



}

