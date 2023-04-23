
package presentation;


import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

public class Cluster {

    public ChartPoint centroid;
    public List<ChartPoint> points;

    Color color;

    public Cluster(int[] centroid) {
        this.centroid = new ChartPoint();
        this.centroid.is_centroid= true;
        this.centroid.setPointX(centroid[0]);
        this.centroid.setPointY(centroid[1]);
        this.points = new ArrayList<>();
        this.color = getRandomColor();
    }


    boolean compareCentroid(ChartPoint point){
        return this.centroid.equals(point);
    }

   
    public static Color getRandomColor() {
        Random random = new Random();

        // generate random values for red, green, and blue
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // create a new Color object with the random values
        Color color = new Color(red, green, blue);

        // calculate the perceived brightness of the color
        double brightness = Math.sqrt(
            color.getRed() * color.getRed() * .241 +
            color.getGreen() * color.getGreen() * .691 +
            color.getBlue() * color.getBlue() * .068
        );

        // check if the color is bright enough
        if (brightness < 130) {
            // if the color is too dark, increase the brightness by adding white
            int max = Math.max(Math.max(red, green), blue);
            int white = random.nextInt(256 - max);
            color = new Color(
                Math.min(red + white, 255),
                Math.min(green + white, 255),
                Math.min(blue + white, 255)
            );
        }

        return color;
    }

    public Color getColor() {
        return this.color;
    }

    

   

    public int [] getCentroid() {
        return centroid.getPoints();
    }

    public void setCentroid(int[] point) {
        this.centroid = new ChartPoint();
        this.centroid.is_centroid= true;
        this.centroid.setPointX(point[0]);
        this.centroid.setPointY(point[1]);
    }

    public List<ChartPoint> getPoints() {
        return points;
    }

    public void addPoint(int[] point) {

        ChartPoint newpoint = new ChartPoint();
        newpoint.setPointX(point[0]);
        newpoint.setPointY(point[1]);
        points.add( newpoint);
    }

    public void clear() {
        points.clear();
    }

    
}
