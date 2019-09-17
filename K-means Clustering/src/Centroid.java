// Course:          CS4242
// Name:            Warren Davis
// Student ID:      000545250
// Assignment #:    #2
// Due Date:        04/24/2019

import java.util.ArrayList;

public class Centroid
{
    private static int count=0; // number of Centroid objects created
    
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private String label;
    private ArrayList<DataPoint> cluster =new ArrayList<DataPoint>(); // cluster of associated Centroid

    /** Constructor */
    Centroid(double sLength, double sWidth, double pLength, double pWidth)
    {
        count++;
        sepalLength = sLength;
        sepalWidth = sWidth;
        petalLength = pLength;
        petalWidth = pWidth;
        label = "Cluster"+count;
    }

    // Adds a DataPoint to this centroid's cluster
    public void addData(DataPoint datum) {
        cluster.add(datum);
    }
    
    /** Getter methods */
    public double getSepalLength()
    {
        return sepalLength;
    }

    public double getSepalWidth()
    {
        return sepalWidth;
    }

    public double getPetalLength()
    {
        return petalLength;
    }

    public double getPetalWidth()
    {
        return petalWidth;
    }

    public String getLabel()
    {
        return label;
    }

    public ArrayList<DataPoint> getCluster()
    {
        return cluster;
    }

    /** Setter methods */
    public void setSepalLength(double sepalLength)
    {
        this.sepalLength = sepalLength;
    }

    public void setSepalWidth(double sepalWidth)
    {
        this.sepalWidth = sepalWidth;
    }

    public void setPetalLength(double petalLength)
    {
        this.petalLength = petalLength;
    }

    public void setPetalWidth(double petalWidth)
    {
        this.petalWidth = petalWidth;
    }
}
