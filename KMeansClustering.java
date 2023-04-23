package presentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingWorker;

public class KMeansClustering {

    private int k;
    private int maxIterations;
    private List<ChartPoint> points;
    private List<Cluster> clusters;
    SwingWorker<Void, Integer> worker;

    public KMeansClustering(int k, int maxIterations, List<ChartPoint> points) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.points = points;
        clusters = new ArrayList<>();
    }

    public void cluster(Graph g) {
        // Initialize k clusters with random centroids
        

        this.worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Your background task goes here

                initializeClusters();

                int iteration = 0;
                boolean clustersChanged = true;

                while (iteration < maxIterations && clustersChanged) {
                    // Assign points to nearest cluster
                    clustersChanged = assignPoints();

                    // Recalculate centroids of each cluster
                    for (Cluster cluster : clusters) {
                        g.points.remove(cluster.centroid);
                    }
                    recalculateCentroids();

                    iteration++;
                    System.out.println("=======");
                    for (Cluster cluster : clusters) {
                        for (ChartPoint point : g.points) {

                            boolean contains = cluster.getPoints().contains(point);
                            if (contains) {
                                // System.out.println("pertence");
                                point.setColor(cluster.getColor());
                            }
                            
                        }

                        g.points.add(cluster.centroid);
                        
                        
                        System.out.println("Cluster " + Arrays.toString(cluster.getCentroid()) );
                        
                    }
                    System.out.println("=======");
                    try {
                        // Sleep for 500 milliseconds
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // Handle the exception if the thread is interrupted while sleeping
                    }

                    publish(iteration);
                }


                return null;
            }
    
            @Override
            protected void process(List<Integer> chunks) {
                // Code to execute when the background task is complete goes here
                for (Integer num : chunks) {
                    System.out.println("Paint " + num);
                    g.repaint();
                }
            }
        };
    
        this.worker.execute();
    }

    void stopClustering(){
        this.worker.cancel(true);
    }

    private void initializeClusters() {
        Random random = new Random();

        ChartPoint randomPoint = points.get(random.nextInt(points.size()));
        clusters.add(new Cluster(randomPoint.getPoints()));
        for (int i = 1; i < k; i++) {
            // calculate distances to the nearest existing centroid for each data point
            Map<ChartPoint, Double> distances = new HashMap<>();
            for (ChartPoint point : points) {
                double minDistance = Double.MAX_VALUE;
                for (Cluster cluster : clusters) {
                    double distance = euclideanDistance(point.getPoints(), cluster.getCentroid());
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
                distances.put(point, minDistance);
            }

            // calculate the sum of squared distances
            double sumOfSquaredDistances = 0.0;
            for (double distance : distances.values()) {
                sumOfSquaredDistances += Math.pow(distance, 2);
            }
            // select a new centroid based on the calculated probabilities
            double maxProbability = 0.0;
            ChartPoint selectedDataPoint = null;

            for (ChartPoint point : points) {
                boolean clusterContain = false;
                for (Cluster cluster : clusters) {
                    if (cluster.compareCentroid(point)) {
                        clusterContain= true;
                        break;
                    }
                }
                if (!clusterContain) {
                    double distance = distances.get(point);
                    double probability = Math.pow(distance, 2) / sumOfSquaredDistances;
                    if (probability > maxProbability) {
                        maxProbability = probability;
                        selectedDataPoint = point;
                    }
                }
            }
            clusters.add(new Cluster(selectedDataPoint.getPoints()));
        }
    }

    private boolean assignPoints() {
        boolean clustersChanged = false;

        for (Cluster cluster : clusters) {
            cluster.points = new ArrayList<>();
        }
        for (ChartPoint point : points) {
            Cluster nearestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double distance = euclideanDistance(point.getPoints(), cluster.getCentroid());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCluster = cluster;
                }
            }
            if (!nearestCluster.getPoints().contains(point)) {
                // Assign point to nearest cluster
                // System.out.println("hello");
                nearestCluster.addPoint(point.getPoints());
                clustersChanged = true;
            }
        }
        return clustersChanged;
    }

    private void recalculateCentroids() {
        for (Cluster cluster : clusters) {
            List<ChartPoint> points = cluster.getPoints();
            if (!points.isEmpty()) {
                int[] centroid = new int[points.get(0).getPoints().length];
                for (ChartPoint point : points) {
                    for (int i = 0; i < centroid.length; i++) {
                        centroid[i] += point.getPoints()[i];
                    }
                }
                for (int i = 0; i < centroid.length; i++) {
                    centroid[i] /= points.size();
                }
                cluster.setCentroid(centroid);
            }
        }
    }

    private double euclideanDistance(int[] point1, int[] point2) {
        int sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return  Math.sqrt(sum);
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    

}