package com.grocerygame.GroceryGame.GroceryGame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class Spinach extends GroceryItem {
    public Spinach(double x, double y) {
        super("Spinach", 2, x, y);
        try  {
        	 String currentDirectory = System.getProperty("user.dir");

 	        // Specify the relative directory path
 	        String relativeDirectoryPath = "\\resources\\pngs\\spinach.png";

 	        // Create the absolute directory path
 	        String absoluteDirectoryPath = currentDirectory + relativeDirectoryPath;
 	        System.out.println(absoluteDirectoryPath);
 	        // Create a File object representing the directory
 	        System.out.println(absoluteDirectoryPath);
 		   
 		  // InputStream inputStream = getClass().getClassLoader().getResourceAsStream("apples.png");
 		   
 		   //BufferedImage img = ImageIO.read(getClass().getResource("/pngs/apples.png"));
 		  // URL url =this.getClass().getResource("/pngs/apples.png");
 		   this.image = new Image(new File(absoluteDirectoryPath).toURI().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}


