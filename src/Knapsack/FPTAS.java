package Knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class FPTAS 
{
	public static void main(String[] args) 
	{
        LinkedList<Selectable> items = new LinkedList<>();

        try 
        {
            Scanner scanner = new Scanner(new File("MainItems.txt"));
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String name = data[0];
                double weight = Double.parseDouble(data[1]);
                int ratings = Integer.parseInt(data[2]);
                int quantity = Integer.parseInt(data[3]);

                items.add(new GroceryItem(name, weight, ratings, quantity));
            }
            scanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.print(e);
            return;
        }

        System.out.println("Fully Polynomial-Time Approximation Scheme (FPTAS)" + '\n');
        System.out.println("Available items: ");
        System.out.printf("\n%1$-20s %2$-10s %3$-12s %4$-10s%n", "Item", "Weight", "Quantity(s)", "Rating");

        for (Selectable item : items) 
        {
            System.out.printf("%1$-20s %2$-10s %3$-12s %4$-10s%n", item.getName(), String.format("%.2f", item.getWeight()) + "kg", item.getQuantity(), item.getRatings());
        }

        System.out.println('\n' + "---------------------------------------------------" + '\n');

        //get the method execute start time
        long start = System.nanoTime();
        
        double maxWeightCapacity = 15.0;
        
        LinkedList<Selectable> selectedItems = new LinkedList<>();
        double maxWeight = fptas(items, maxWeightCapacity, selectedItems);
        
        //get the method end time
        long end = System.nanoTime();
        
        //calculate the method execution time
        long execution = (end - start);

        System.out.println("Selected items: ");
        System.out.printf("\n%1$-20s %2$-10s %3$-12s %4$-10s%n", "Item", "Weight", "Quantity(s)", "Rating");
        for (Selectable selected : selectedItems) {
            System.out.printf("%1$-20s %2$-10s %3$-12s %4$-10s%n", selected.getName(), String.format("%.2f", selected.getWeight()) + "kg", selected.getQuantity(), selected.getRatings());
        }
        System.out.println('\n' + "Total weight: " + String.format("%.2f", maxWeight) + "kg");
        
        System.out.println("\nCalculation time of using the selected algorithm is: " + execution + " nanoseconds");
    }

    public static double fptas(LinkedList<Selectable> items, double capacity, LinkedList<Selectable> selectedItems) {
        int n = items.size();

        items.sort((item1, item2) -> Integer.compare(item2.getRatings(), item1.getRatings()));

        double[][] dp = new double[n + 1][(int) (capacity * 10) + 1];

        for (int i = 1; i <= n; i++) {
        	
            Selectable item = items.get(i - 1);

            for (int w = 0; w <= (int) (capacity * 10); w++) {
            	
                dp[i][w] = dp[i - 1][w];
                
                for (int q = 1; q <= item.getQuantity(); q++) {
                	
                    if (q * (item.getWeight() * 10) <= w) {
                        dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - (int) (q * (item.getWeight() * 10))] + q * item.getRatings());
                    }
                }
            }
        }

        int w = (int) (capacity * 10);

        for (int i = n; i > 0 && w > 0; i--) 
        {
            Selectable item = items.get(i - 1);
            for (int q = item.getQuantity(); q >= 0 && q * (item.getWeight() * 10) <= w; q--) 
            {
                if (dp[i][w] == dp[i - 1][w - (int) (q * (item.getWeight() * 10))] + q * item.getRatings()) 
                {
                    if (q > 0) 
                    {
                        selectedItems.add(new GroceryItem(item.getName(), item.getWeight(), item.getRatings(), q));
                        w -= q * (item.getWeight() * 10);
                    }
                    break;
                }
            }
        }

        double totalWeight = calculateTotalWeight(selectedItems);
        return totalWeight;
    }

    public static double calculateTotalWeight(LinkedList<Selectable> items) 
    {
        double totalWeight = 0.0;
        for (Selectable item : items)
        {
            totalWeight += item.getWeight() * item.getQuantity();
        }
        return totalWeight;
    }
}
