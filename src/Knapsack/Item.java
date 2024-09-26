package Knapsack;

public abstract class Item implements Selectable
{
	String name;
    double weight;
    int quantity;
    int ratings;

    public Item(String name, double weight, int ratings, int quantity) 
    {
        this.name = name;
        this.weight = weight;
        this.ratings = ratings;
        this.quantity = quantity;
    }

    public String getName() 
    {
        return name;
    }

    public double getWeight() 
    {
        return weight;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public int getRatings() 
    {
        return ratings;
    }
    
}
