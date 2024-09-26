package Knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Greedy 
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
        
        System.out.println("Greedy Method" + '\n');
        System.out.println("Available items: ");
        System.out.printf("\n%1$-20s %2$-10s %3$-12s %4$-10s%n", "Item", "Weight", "Quantity(s)", "Rating");

        for (Selectable item : items) 
        {
            System.out.printf("%1$-20s %2$-10s %3$-12s %4$-10s%n", item.getName(), String.format("%.2f", item.getWeight()) + "kg", item.getQuantity(), item.getRatings());
        }

        System.out.println('\n' + "---------------------------------------------------" + '\n');
        
      //get the method execute start time
        long start = System.nanoTime();
        
        items.sort((item1, item2) -> Integer.compare(item2.getRatings(), item1.getRatings()));
        
        double totalWeight = 0.0;
        
        double bagCapacity = 15.0;
 
        LinkedList<SelectedGroceryItem> selectedItemsList = new LinkedList<>();
        
        for (Selectable item : items) 
        {
            int remainingQuantity = item.getQuantity();  // Initialize with the original quantity
            while (remainingQuantity > 0 && totalWeight + item.getWeight() <= bagCapacity) 
            {
                SelectedGroceryItem selected = findSelectedItem(selectedItemsList, item.getName());
                if (selected == null) 
                {
                    selected = new SelectedGroceryItem(item.getName(), item.getWeight(), item.getRatings(), 1);
                    selectedItemsList.add(selected);
                } 
                else 
                {
                    selected.quantity++;
                }
                
                totalWeight += item.getWeight();
                remainingQuantity--;  // Decrement the remaining quantity
            }
        }
        
        //get the method end time
        long end = System.nanoTime();
        
        //calculate the method execution time
        long execution = (end - start);
        
        System.out.println("Selected items: ");
        System.out.printf("\n%1$-20s %2$-10s %3$-12s %4$-10s%n", "Item", "Weight", "Quantity(s)", "Rating");
        for (SelectedGroceryItem selected : selectedItemsList) 
        {
            System.out.printf("%1$-20s %2$-10s %3$-12s %4$-10s%n", selected.name, String.format("%.2f", selected.weight) + "kg", selected.quantity, selected.ratings);
        }
        System.out.println('\n' + "Total weight: " + String.format("%.2f", totalWeight) + "kg");
        
        System.out.println("\nCalculation time of using the selected algorithm is: " 
        + execution + " nanoseconds");

	}
	
	public static SelectedGroceryItem findSelectedItem(LinkedList<SelectedGroceryItem> list, String name) {
		
        for (SelectedGroceryItem item : list) 
        {
            if (item.name.equals(name)) 
            {
                return item;
            }
        }
        return null;
    }

    public static class SelectedGroceryItem 
    {
        String name;
        double weight;
        int quantity;
        int ratings;

        public SelectedGroceryItem(String name, double weight, int ratings, int quantity) 
        {
            this.name = name;
            this.weight = weight;
            this.ratings = ratings;
            this.quantity = quantity;
        }
    }
}

