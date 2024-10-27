package com.grocerygame.GroceryGame.GroceryGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javafx.scene.image.Image;

public class Apple extends GroceryItem {

    public Apple(double x, double y) {
        
    	super("Apple", 2,  x, y);
    	   try{
    		   
    		   
    		   
    		  
    		// Get the current working directory
    	        String currentDirectory = System.getProperty("user.dir");

    	        // Specify the relative directory path
    	        String relativeDirectoryPath = "\\resources\\pngs\\apples.png";

    	        // Create the absolute directory path
    	        String absoluteDirectoryPath = currentDirectory + relativeDirectoryPath;
    	        System.out.println(absoluteDirectoryPath);
    	        // Create a File object representing the directory
    	        System.out.println(absoluteDirectoryPath);
    		   
    		  // InputStream inputStream = getClass().getClassLoader().getResourceAsStream("apples.png");
    		   
    		   //BufferedImage img = ImageIO.read(getClass().getResource("/pngs/apples.png"));
    		  // URL url =this.getClass().getResource("/pngs/apples.png");
    		   this.image = new Image(new File(absoluteDirectoryPath).toURI().toString());
    	   }catch(Exception e) {
    		   
    	   }
           
      
    }
    
    
}

