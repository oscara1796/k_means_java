//The code is part of a package named practica_2
package presentation;

//Importing required classes from the packages javax.swing, java.awt, and java.util
import javax.swing.*;


// import Neuron;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Class Graph extends JPanel
public class Graph extends JPanel{

    //Declaration of static boolean array arr
    static boolean arr [] = {false};

    //Declaration of integer array chartPoints and ArrayList of Integer labels, and Neuron neuron
    // public Neuron neuron;
    KMeansClustering kmeans;

    //Declaration of List points as ArrayList of ChartPoint
    public List<ChartPoint> points = new ArrayList<>();


    //Constructor Graph()
    Graph(){
        //Setting the background color
        setBackground(new Color(204, 245, 255));
        //Instantiating ArrayList labels

        //Adding MouseAdapter to the Graph object
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                //Adding new ChartPoint to the ArrayList points
                ChartPoint point = new ChartPoint(e.getX(), e.getY());
                //Displaying a dialog box with message and YES/NO options, and adding the user's input to ArrayList labels
                // 
                points.add(point);
                //Redraw the panel
                repaint();
            }
        });

        
    }

    //Method to train the perceptron using points and labels
    public void trainPerceptron() {

        kmeans = new KMeansClustering(3, 50, points);
        kmeans.cluster(this);

        // neuron.train(points, 1000, this);
    }

    public void stopExecution() {
        kmeans.stopClustering();
    }

    

   
    

    //Overriding paintComponent method to draw the panel
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        //Casting Graphics object to Graphics2D object
        Graphics g2 = (Graphics2D) g;
        boolean drawLine = false;
        //Setting the color of Graphics object to RED
        g2.setColor(Color.RED);
        //Iterating over each ChartPoint in ArrayList points
        for (ChartPoint p : points){
            if (p.is_centroid) {
                GeneralPath star = new GeneralPath();
        
                // move to the first point of the star
                star.moveTo(p.x, p.y - 10);

                // add the five points of the star
                for (int i = 1; i < 5; i++) {
                    double angle = i * 4 * Math.PI / 5;
                    star.lineTo((float) (p.x + 10 * Math.sin(angle)), (float) (p.y - 10 * Math.cos(angle)));
                }

                // close the path
                star.closePath();

                // set the color of the star
                g2.setColor(Color.RED);

                // fill the star
                ((Graphics2D) g2).fill(star);
                System.out.println("star");
            }else{
                g2.setColor(p.getColor());
                g2.fillOval(p.x, p.y, 10, 10);
                g2.setColor(Color.RED);
            }
        }
        g2.setColor(Color.BLACK);
        Dimension d = this.getSize();
        int height = (int) d.getHeight()/2;
        int width = (int) d.getWidth()/2;
        g.drawLine(width, 0, width, height+height);
        g.drawLine(0, height, width+width, height);
        for (int i = 0; i < height*2 ; i+=40) {
            g.drawLine(width-10, i, width+10, i);
            g.drawString(String.valueOf(200-i), width-15, i);
        }
        for (int i = 0; i < width*2 ; i+=40) {
            g.drawLine(i, height-10, i, height+10);
            g.drawString(String.valueOf((200-i)*-1), i, height-15);
        }
        
        
        
    }

    

    
}
