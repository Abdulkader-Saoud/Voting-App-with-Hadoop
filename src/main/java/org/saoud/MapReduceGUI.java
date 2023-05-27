package org.saoud;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MapReduceGUI extends JFrame {

    public MapReduceGUI() {
        initComponents();
    }


    private void initComponents() {
        setTitle("Candidates APP");
        setSize(500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JButton runButton = new ButtonCom("Calculate each candidates votes","1");
        JButton runButton2 = new ButtonCom("Calculate the Max age","2");
        JButton runButton3 = new ButtonCom("Calculate the Votes from Cities","3");
        JButton runButton4 = new ButtonCom("Calculate the Voters Average Age","4");
        JButton runButton5 = new ButtonCom("Calculate the Candidates Percentage","5");

        panel.add(runButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(runButton2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(runButton3);
        panel.add(Box.createVerticalStrut(10));
        panel.add(runButton4);
        panel.add(Box.createVerticalStrut(10));
        panel.add(runButton5);
        fsPart(panel);
        add(panel);
        setVisible(true);
    }
    public void fsPart(JPanel panel){
        JLabel label = new JLabel("Input Path");
        label.setPreferredSize(new Dimension(400, 35));
        label.setMaximumSize(new Dimension(400, 35));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(450, 35));
        textField.setMaximumSize(new Dimension(450, 35));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("Set Input");
        button.setPreferredSize(new Dimension(200, 35));
        button.setMaximumSize(new Dimension(200, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(textField.getText());
                if (file.exists())
                    HDFSfun.setInput(textField.getText());
                else
                    textField.setText("Enter a Valid path");
            }
        });
        JLabel label2 = new JLabel("Delete Path");
        label2.setPreferredSize(new Dimension(400, 35));
        label2.setMaximumSize(new Dimension(400, 35));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(450, 35));
        textField2.setMaximumSize(new Dimension(450, 35));
        textField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button2 = new JButton("Delete");
        button2.setPreferredSize(new Dimension(200, 35));
        button2.setMaximumSize(new Dimension(200, 35));
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HDFSfun.deleteDir(textField2.getText());
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(textField2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button2);
    }

    public static void main(String[] args) {
        MapReduceGUI gui = new MapReduceGUI();
    }
}
