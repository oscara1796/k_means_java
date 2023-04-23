package presentation;


import javax.swing.*;

// import Neuron;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.ArrayList;



public class GUI {
    JFrame frame;
    JButton startButton;
    JButton clearButton;
    JTextField w1Field;
    JTextField w2Field;
    JTextField bField;
    JLabel w1;
    JLabel w2;
    JLabel b;
    Graph g = new Graph();
    // Neuron neuron;

    GUI(){
        frame = new JFrame("k Means");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,500);
        frame.setLocationRelativeTo(null);
        
        clearButton = new JButton("clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                //Here goes the action (method) you want to execute when clicked
                System.out.println("clear button ");
                g.points = new ArrayList<>();
                g.stopExecution();
                g.repaint();
            }
        }); 
        clearButton.setBounds(500,200,100,30);

        // Creating start button
        startButton = new JButton("start");
        startButton.setBounds(600,200,100,30);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)            {
                //Here goes the action (method) you want to execute when clicked
                System.out.println("start button ");
                g.trainPerceptron();
                // g.repaint();
            }
        }); 
        
        
        


        //Input boxes 
       
        g.setBounds(50,50,400,400);
        
       
        
        // Adding elements to the GUI
        
        frame.add(startButton); // Adds Button to content pane of frame
        frame.add(clearButton); // Adds Button to content pane of frame
        frame.add(g);
        frame.setLayout(null);
        // frame.pack();
        frame.setVisible(true);
    }



    public static void main(String args[]){
        GUI gui = new GUI();
    }
}
