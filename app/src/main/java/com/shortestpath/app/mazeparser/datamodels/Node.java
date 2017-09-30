package com.shortestpath.app.mazeparser.datamodels;

/**
 * Data model which holds the properties of a node into the maze.
 *
 * This properties are its position in the maze, determined by the row and column, and the cost,
 * which means the weight of the route to calculate the shortest pasth. */
public class Node {

    private int cost;

    private int row;
    private int col;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
