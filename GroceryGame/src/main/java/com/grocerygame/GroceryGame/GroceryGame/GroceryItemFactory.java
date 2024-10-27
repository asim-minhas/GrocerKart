package com.grocerygame.GroceryGame.GroceryGame;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroceryItemFactory {
	public static List<GroceryItem> allItems = new ArrayList<>();
	static {
        // Create instances of all available grocery items and add them to the list
        allItems.add(new Apple(0,0)); // Example image path
        allItems.add(new Banana(0,0)); // Example image path
        allItems.add(new Orange(0,0)); // Example image path
        allItems.add(new Spinach(0,0)); // Example image path
        allItems.add(new Potatoes(0,0)); // Example image path
        allItems.add(new Tomatoes(0,0)); // Example image path
        allItems.add(new Eggs(0,0)); // Example image path
        allItems.add(new Onion(0,0)); // Example image path
        // Add more items as needed
    }
	public static GroceryItem createRandomGroceryItem() {
        Random random = new Random();
        int itemType = random.nextInt(10); // Generate random number (0-9)
        switch (itemType) {
            case 0:
                return new Apple(0,0); // Assuming initial position (0, 0)
            case 1:
                return new Banana(0, 0);
            case 2:
                return new Orange(0, 0);
            case 3:
                return new Eggs(0, 0);
            case 4:
                return new Spinach(0, 0);
            case 5:
                return new Tomatoes(0, 0);
            case 6:
                return new Potatoes(0, 0);
            // Add more grocery items as needed
            default:
                return new Onion(0, 0);
        }
        
    }
    public static List<GroceryItem> getAllItems() {
        return allItems;
    }
    
}

