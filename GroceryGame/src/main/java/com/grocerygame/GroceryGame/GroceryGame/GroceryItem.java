package com.grocerygame.GroceryGame.GroceryGame;

import java.io.FileInputStream;
import java.io.InputStream;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class GroceryItem extends GameObject {
    private String name;
    private int price;
    public Image image;
    private static final double IMAGE_WIDTH = 100; // Adjust the width as needed
    private static final double IMAGE_HEIGHT = 100; // Adjust the height as needed

    public GroceryItem(String name, int price, double x, double y) {
        super(x, y);
        
        this.name = name;
        this.price = price;
    	

    }

    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        // Draw the image of the grocery item with specified width and height
        gc.drawImage(image, x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

	protected Image getImage() {
		return image;
	}
	protected void setImage(InputStream inputStream) {
		  this.image = new Image(inputStream);
	}
}


