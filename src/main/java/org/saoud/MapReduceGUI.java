package org.saoud;


import javax.swing.*;
import java.awt.*;

public class MapReduceGUI extends JFrame {

    public MapReduceGUI() {
        initComponents();
    }


    private void initComponents() {
        setTitle("Candidates APP");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new FlowLayout());


        JButton runButton = new ButtonCom("Calculate each candidates votes","1");
        JButton runButton2 = new ButtonCom("Calculate the Max age","2");
        JButton runButton3 = new ButtonCom("Calculate the Votes from Cities","3");
        JButton runButton4 = new ButtonCom("Calculate the Voters Average Age","4");
        JButton runButton5 = new ButtonCom("Calculate the Candidates Percentage","5");
        panel.add(runButton);
        panel.add(runButton2);
        panel.add(runButton3);
        panel.add(runButton4);
        panel.add(runButton5);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("hiiiiiiiiiii");
        MapReduceGUI gui = new MapReduceGUI();
    }
}
