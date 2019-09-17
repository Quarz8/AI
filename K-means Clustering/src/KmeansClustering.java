// Course:          CS4242
// Name:            Warren Davis
// Student ID:      000545250
// Assignment #:    #2
// Due Date:        04/24/2019

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class KmeansClustering
{

    public static void main(String[] args) throws IOException
    {
        // K number of centroids/clusters
        final int K = 3;

        // Data set scanner
        Scanner s = new Scanner(new File("IrisSpecies"));
        s.useDelimiter(",|\\s+"); // comma and new line set as delimiters

        // Array list to hold data points
        ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();

        // Array list to hold training set of data points
        ArrayList<DataPoint> trainingList = new ArrayList<DataPoint>();

        // Array list to hold centroids
        ArrayList<Centroid> centroidList = new ArrayList<Centroid>();

        // Create list of all data points for use in code
        while (s.hasNext())
        {
            DataPoint temp = new DataPoint(s.nextDouble(), s.nextDouble(), s.nextDouble(), s.nextDouble(), s.next());
            dataList.add(temp);
        }
        s.close();

        /**
         * Note: in this instance, there are 50 examples of 3 iris species. 1-50
         * iris-setosa 51-100 iris-versicolor 101-150 iris-virginica The first 40 of
         * each species (80%) will be used to train. The remaining 10 of each species
         * (20%) will be used to test.
         */

        // Add first 40 iris-setosa to training list
        for (int i = 0; i < 40; i++)
            trainingList.add(dataList.get(i));
        // Add first 40 iris-versicolor to training list
        for (int i = 50; i < 90; i++)
            trainingList.add(dataList.get(i));
        // Add first 40 iris-virginica to training list
        for (int i = 100; i < 140; i++)
            trainingList.add(dataList.get(i));

        // Generate K Centroids DataPoints labeled Centroid*K* for training (values are
        // random within reasonable ranges)
        for (int i = 0; i < K; i++)
            centroidList.add(new Centroid((Math.random() * 4) + 4, (Math.random() * 3) + 2, Math.random() * 7,
                    Math.random() * 3));

        // We try to get the centroids to converge for 1000 iterations
        for (int iterations = 0; iterations < 1000; iterations++)
        {
            // Compare each training data point to each Centroid and assign it to closest
            // centroid's cluster
            for (int i = 0; i < trainingList.size(); i++) // for every data point in the training list...
            {
                double distance = Double.POSITIVE_INFINITY; // shortest distance between centroid and test point
                                                            // (default
                                                            // infinity)
                int closest = 0; // set index of nearest centroid in centroidList

                for (int j = 0; j < K; j++) // compare data point distance from every centroid
                {

                    // Calculates distance (d) between centroid and training data point
                    double d = Math.sqrt(Math
                            .pow(centroidList.get(j).getSepalLength() - trainingList.get(i).getSepalLength(), 2)
                            + Math.pow(centroidList.get(j).getSepalWidth() - trainingList.get(i).getSepalWidth(), 2)
                            + Math.pow(centroidList.get(j).getPetalLength() - trainingList.get(i).getPetalLength(), 2)
                            + Math.pow(centroidList.get(j).getPetalWidth() - trainingList.get(i).getPetalWidth(), 2));

                    if (d < distance) // if distance (d) from this centroid is shorter than current distance...
                    {
                        distance = d; // set current distance to d
                        closest = j; // set closest to index of this centroid
                    }

                } // end j loop
                centroidList.get(closest).addData(trainingList.get(i)); // add data point to closest centroid's cluster

            } // end i loop

            // Update Centroid values to reflect mean value of its cluster
            for (int j = 0; j < K; j++) // for each centroid...
            {
                double sLen = 0, sWid = 0, pLen = 0, pWid = 0;
                for (int g = 0; g < centroidList.get(j).getCluster().size(); g++) // for each data point of its cluster
                {
                    // Sum each iris value
                    sLen += centroidList.get(j).getCluster().get(g).getSepalLength();
                    sWid += centroidList.get(j).getCluster().get(g).getSepalWidth();
                    pLen += centroidList.get(j).getCluster().get(g).getPetalLength();
                    pWid += centroidList.get(j).getCluster().get(g).getPetalWidth();
                }
                // Set centroid values to average for each iris value
                centroidList.get(j).setSepalLength(sLen / centroidList.get(j).getCluster().size());
                centroidList.get(j).setSepalWidth(sWid / centroidList.get(j).getCluster().size());
                centroidList.get(j).setPetalLength(pLen / centroidList.get(j).getCluster().size());
                centroidList.get(j).setPetalWidth(pWid / centroidList.get(j).getCluster().size());
                // Clear cluster values for next iteration if applicable
                if (iterations < 999)
                    centroidList.get(j).getCluster().clear();
            }

        }

        // Returns first 5 data point labels of each cluster for human reference
        System.out.println(centroidList.get(0).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(0).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(1).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(2).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(3).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(4).getLabel());
        System.out.println(centroidList.get(0).getCluster().get(5).getLabel());

        System.out.println(centroidList.get(1).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(0).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(1).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(2).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(3).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(4).getLabel());
        System.out.println(centroidList.get(1).getCluster().get(5).getLabel());

        System.out.println(centroidList.get(2).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(0).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(1).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(2).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(3).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(4).getLabel());
        System.out.println(centroidList.get(2).getCluster().get(5).getLabel());

        /** Testing for K-means cluster algorithm */
        // dataListIndex 41-50 is iris-setosa, 91-100 is iris-versicolor, 141-150 is
        // iris virginica
        int dataListIndex = 141;
        double d1 = Math.sqrt(Math
                .pow(centroidList.get(0).getSepalLength() - dataList.get(dataListIndex).getSepalLength(), 2)
                + Math.pow(centroidList.get(0).getSepalWidth() - dataList.get(dataListIndex).getSepalWidth(), 2)
                + Math.pow(centroidList.get(0).getPetalLength() - dataList.get(dataListIndex).getPetalLength(), 2)
                + Math.pow(centroidList.get(0).getPetalWidth() - dataList.get(dataListIndex).getPetalWidth(), 2));
        double d2 = Math.sqrt(Math
                .pow(centroidList.get(1).getSepalLength() - dataList.get(dataListIndex).getSepalLength(), 2)
                + Math.pow(centroidList.get(1).getSepalWidth() - dataList.get(dataListIndex).getSepalWidth(), 2)
                + Math.pow(centroidList.get(1).getPetalLength() - dataList.get(dataListIndex).getPetalLength(), 2)
                + Math.pow(centroidList.get(1).getPetalWidth() - dataList.get(dataListIndex).getPetalWidth(), 2));
        double d3 = Math.sqrt(Math
                .pow(centroidList.get(2).getSepalLength() - dataList.get(dataListIndex).getSepalLength(), 2)
                + Math.pow(centroidList.get(2).getSepalWidth() - dataList.get(dataListIndex).getSepalWidth(), 2)
                + Math.pow(centroidList.get(2).getPetalLength() - dataList.get(dataListIndex).getPetalLength(), 2)
                + Math.pow(centroidList.get(2).getPetalWidth() - dataList.get(dataListIndex).getPetalWidth(), 2));

        // Determine shortest distance and display cluster data belongs to
        if (d1 < d2 && d1 < d3)
            System.out.println("This data looks like it belongs to Cluster1");
        else if (d2 < d3)
            System.out.println("This data looks like it belongs to Cluster2");
        else
            System.out.println("This data looks like it belongs to Cluster3");

    }

}