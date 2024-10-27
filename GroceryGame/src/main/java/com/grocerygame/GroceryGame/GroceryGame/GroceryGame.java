package com.grocerygame.GroceryGame.GroceryGame;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GroceryGame extends Application {
    private GraphicsContext gc;
    private List<GroceryItem> groceryItems;
    private int correctTotalPrice;
    private List<Integer> priceOptions;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private MediaPlayer correctSoundPlayer;
    private MediaPlayer wrongSoundPlayer;
    private GridPane optionsGrid;
    private GridPane	itemsGrid;
    


    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        Canvas canvas = new Canvas(1000, 800);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GrocerKart");
        primaryStage.show();
        
        gc.setFill(Color.LIGHTBLUE); // Adjust the background color as needed
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image logoImage = new Image("file:src/main/resources/pngs/logo.png"); // Replace "logo.png" with the path to your logo image
        gc.drawImage(logoImage, 400, 550,250,100);
        gc.setFill(Color.BLACK);
       // gc.setFont(Font.font("Arial", 36)); // Adjust font size and style as needed
       // gc.fillText("GrocerKart", 400, 700);
        
        // Generate random grocery items
        generateGroceryItems();

        // Start the game
        calculatePrice();

        GridPane catalogueGridPane = new GridPane();
        catalogueGridPane.setHgap(10); // Set horizontal gap between items
        catalogueGridPane.setVgap(10); // Set vertical gap between items
        catalogueGridPane.setPadding(new Insets(20)); // Set padding around the grid

        // Draw the catalogue items
        drawCatalogue(catalogueGridPane);
        
        root.getChildren().addAll(canvas,catalogueGridPane);
        // Draw the grocery items on the canvas
        itemsGrid = new GridPane();
        itemsGrid.setHgap(10); // Set horizontal gap between items
        itemsGrid.setVgap(10); // Set vertical gap between items
        itemsGrid.setPadding(new Insets(20)); // Set padding around the grid
        
        drawGroceryItems();
        StackPane.setMargin(itemsGrid,new Insets(150,150,150,150));
        root.getChildren().add(itemsGrid);
        
        // Draw selectable options
        
        optionsGrid = new GridPane();

        optionsGrid.setAlignment(Pos.CENTER);
        optionsGrid.setHgap(10);
        optionsGrid.setVgap(10);

        // Draw the options
        drawOptions(optionsGrid);
        
        root.getChildren().add(optionsGrid);
        
        
        playBackgroundMusic();
        loadSounds();
        
        // Handle mouse click events
        scene.setOnMouseClicked(this::handleMouseClick);
    }
   
    public void resetGroceryItems() {
        itemsGrid.getChildren().clear(); 
        optionsGrid.getChildren().clear();
   	 	generateGroceryItems();
   	 	drawGroceryItems();
        calculatePrice(); 
    	 drawOptions(optionsGrid);
    	 playBackgroundMusic();
    	 loadSounds();
    	
    }

    private void generateGroceryItems() {
        groceryItems = new ArrayList<>();
        // Add random number of random grocery items
        Random random = new Random();
        int numItems = random.nextInt(5) + 1; // Generate random number of items (1-5)
        for (int i = 0; i < numItems; i++) {
            // Create a random grocery item
        	
            groceryItems.add(GroceryItemFactory.createRandomGroceryItem());
        }
        //GroceryItemFactory.allItems = groceryItems;
    }
    private void drawCatalogue(GridPane gridPane) {
        // Populate the catalogue grid
        List<GroceryItem> allItems = GroceryItemFactory.getAllItems();
        int col = 0;
        int row = 0;
        for (GroceryItem item : allItems) {
            ImageView imageView = new ImageView(item.getImage());
            imageView.setFitWidth(30); // Set image width
            imageView.setFitHeight(30); // Set image height
            gridPane.add(imageView, col, row); // Add image to the grid

            Label nameLabel = new Label(item.getName());
            gridPane.add(nameLabel, col, row + 1); // Add item name below the image

            Label priceLabel = new Label("Price: $" + item.getPrice());
            gridPane.add(priceLabel, col, row + 2); // Add item price below the name

            col++; // Move to the next column
            if (col == 9) { // If reached the end of a row, move to the next row
                col = 0;
                row += 3;
            }
            
        }
    }
    private void loadSounds() {
        // Load the sound files for correct and wrong selections
        String correctSoundFile = "resources/audio/celebrate.mp3"; // Change this to the path of your correct sound file
        Media correctSound = new Media(new File(correctSoundFile).toURI().toString());

        String wrongSoundFile = "resources/audio/gasp.mp3"; // Change this to the path of your wrong sound file
        Media wrongSound = new Media(new File(wrongSoundFile).toURI().toString());

        // Create media players for correct and wrong sounds
        correctSoundPlayer = new MediaPlayer(correctSound);
        wrongSoundPlayer = new MediaPlayer(wrongSound);
    }

    private void calculatePrice() {
        // Generate total price for the groceries
        correctTotalPrice = calculateTotalPrice();
        // Generate 4 options for total price (including correct and 3 random incorrect prices)
        priceOptions = generatePriceOptions(correctTotalPrice);
        // Print the options to the console (for demonstration)
        System.out.println("Options: " + priceOptions);
    }
    private void playBackgroundMusic() {
        // Load the audio file
        String musicFile = "resources/audio/background-music.mp3"; // Change this to the path of your audio file
        Media sound = new Media(new File(musicFile).toURI().toString());

        // Create a media player
        mediaPlayer = new MediaPlayer(sound);

        // Set the media player to loop indefinitely
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the background music
        mediaPlayer.play();
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (GroceryItem item : groceryItems) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    private List<Integer> generatePriceOptions(int correctPrice) {
        List<Integer> options = new ArrayList<>();
        options.add(correctPrice); // Add correct price
        // Generate 3 random incorrect prices
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int price = correctPrice;
            // Ensure generated price is different from the correct price
            while (price == correctPrice) {
                price = random.nextInt(20) + 1; // Generate random price (1-20)
            }
            options.add(price);
        }
        // Shuffle the options
        java.util.Collections.shuffle(options);
        return options;
    }

    private void drawGroceryItems() {
        double x = 0;
       
        int col = 0;
        for (GroceryItem item : groceryItems) {
        //    item.draw(gc, x, 200); // Pass the x and y coordinates to the draw method
        //    if(!(groceryItems.indexOf(item) == (groceryItems.size()-1))) {
       //     	gc.setFill(Color.BLACK);
            
       //     gc.setFont(javafx.scene.text.Font.font("Arial", 20));
        //    gc.fillText("+", x + 100, 250); // Adjust position as needed
        	ImageView imgView = new ImageView(item.getImage());
        	imgView.setFitWidth(100); // Set image width
        	imgView.setFitHeight(100); // Set image height

        	itemsGrid.add(imgView,col++,0);
        	
        	
            if(x < (groceryItems.size()-1)) {
            	Text addSymbol = new Text("+");
            	addSymbol.setFont(Font.font("Arial",28));
            	itemsGrid.add(addSymbol, col++, 0);
        	
            }
        else {
        	Text equalSymbol = new Text("\t=\t\t?");
        	equalSymbol.setFont(Font.font("Arial",28));
        
        	itemsGrid.add(equalSymbol, col++, 0);
        }
            x ++; // Adjust the gap between items
            
            
        }
    }

        // Draw addition symbol between grocery items
       // gc.setFill(Color.BLACK);
      //  gc.setFont(javafx.scene.text.Font.font("Arial", 20));
      //  gc.fillText("=", x+50, 250);
      //  gc.fillText("?", x+100, 250);// Adjust position as needed
    
    
    private void drawOptions(GridPane optionsGrid) {
        // Define the options
        //String[] options = { "Option 1", "Option 2", "Option 3", "Option 4" };

        // Populate the options grid
        for (int i = 0; i < priceOptions.size(); i++) {
            int totalPrice = priceOptions.get(i);
            Button optionButton = new Button("$" + totalPrice);
            optionButton.setMinWidth(200);
            optionButton.setMinHeight(50);
            optionButton.setAlignment(Pos.CENTER);
            optionButton.setStyle("-fx-background-color: #000000,linear-gradient(#7ebcea, #2f4b8f),linear-gradient(#426ab7, #263e75),linear-gradient(#395cab, #223768);-fx-background-insets: 0,1,2,3;-fx-background-radius: 3,2,2,2;-fx-padding: 12 30 12 30;-fx-text-fill: white;-fx-font-size: 12px;"); // Reset to default color


            // Add event handler to change the color of the option when clicked
            int finalI = i; // Required for lambda expression
            optionButton.setOnAction(event -> {
                // Reset the color of all options
                for (int j = 0; j < optionsGrid.getChildren().size(); j++) {
                    Button btn = (Button) optionsGrid.getChildren().get(j);
                    btn.setStyle("-fx-background-color: #000000,linear-gradient(#7ebcea, #2f4b8f),linear-gradient(#426ab7, #263e75),linear-gradient(#395cab, #223768);-fx-background-insets: 0,1,2,3;-fx-background-radius: 3,2,2,2;-fx-padding: 12 30 12 30;-fx-text-fill: white;-fx-font-size: 12px;"); // Reset to default color
                }

                // Change the color of the clicked option
                optionButton.setStyle("-fx-background-color: #FF0000"); // Set to red
                String priceStr = optionButton.getText();
                priceStr= priceStr.substring(1);
                Integer price = Integer.parseInt(priceStr);
                // Check if the clicked option is correct (for demonstration, assume option 2 is correct)
                if (price == correctTotalPrice) { // Index 1 corresponds to option 2
                    optionButton.setStyle("-fx-background-color: #00FF00"); // Set to green
                    mediaPlayer.stop();
                    correctSoundPlayer.play();
                    
                }
                else {
                	mediaPlayer.stop();
                	wrongSoundPlayer.play();     
                	}           
                Duration sec = Duration.ofSeconds(1);
            
                PauseTransition pause = new PauseTransition();
                pause.setOnFinished(e -> {
                    // Add event handlers to options buttons after the delay

                    
                    // Call the initial start function
                	resetGroceryItems();
                });
                pause.play();
            });

            optionsGrid.add(optionButton, i % 2, i / 2);
        }
    }

    private void handleMouseClick(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();
        for (int i = 0; i < priceOptions.size(); i++) {
            double optionX = 50 + i * 100; // Calculate the X position of the option
            double optionY = 400; // Adjust the Y position of the options
            if (mouseX >= optionX && mouseX <= optionX + 50 && mouseY >= optionY && mouseY <= optionY + 20) {
                int selectedOption = priceOptions.get(i);
                System.out.println("Selected option: " + selectedOption);
                // Implement logic for handling the selected option (e.g., check if it's correct)
                break; // Exit the loop once an option is selected
            }
        }
    }
    
    

    @Override
    public void stop() {
        // Clean-up code here
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
