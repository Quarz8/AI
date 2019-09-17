// Course:          CS4242
// Name:            Warren Davis
// Student ID:      000545250
// Assignment #:    #2
// Due Date:        04/24/2019

public class DataPoint
{
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private String label;

    /** Constructor */
    DataPoint(double sLength, double sWidth, double pLength, double pWidth, String type)
    {
        sepalLength = sLength;
        sepalWidth = sWidth;
        petalLength = pLength;
        petalWidth = pWidth;
        label = type;
    }

    /** Getter methods for every variable */
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

}
