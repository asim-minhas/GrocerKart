package com.grocerygame.GroceryGame.GroceryGame;

import javafx.scene.canvas.GraphicsContext;

abstract class GameObject {
    private double x, y;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void draw(GraphicsContext gc, double x, double y);
}


