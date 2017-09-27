package com.shortestpath.app.mazeparser;

import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

import java.util.Collections;

/*
* This class is a handler that parser a given maze in order to get the smallest path by going
* from the first column to the last.
*
* In order in order to get the smallest path, an auxiliary derivative maze is created by
* choosing the smallest (less costly) addition between each node in the given maze and its three
* left-adjacent nodes at the upper-left, left, and lower-left positions.
*
* If there is no nodes at these positions, means there is the start of the path (column 0) and the
* current value in the given maze is stored in the derivative maze.
*
* Once the parsing is done, the smallest value (the smallest distance) in the last column is selected
* to get the result distance of the shortest path.
*
* To know which are all the nodes which conform the shortest path, the following is done:
*
* On the derivative maze:
* 1. The node in the last column with the smallest distance is taken and its value stored in a array.
* 2. Find the left-adjacent node with the smallest value and store it in the array.
* 3. Select this node and repeat steps 1 and 2 until the first column is reached.
* 4. Reverse the order of the array.
*
* */
public class MazeParser {

    // Listener which informs that the shortest path in the maze has been found
    private OnShortestPathFoundListener onShortestPathFoundListener;

    private boolean success = true;

    /**
     * Beginning of the calculation.
     *
     * First, the derivative maze is obtained by calling the method parseInputMaze with the original maze as parameter.
     * Second, the smallest path is obtained by calling the method getShortestPath with the derivative maze as parameter.
     * */
    public void findShortestPath(String[][] initial_maze) {

        Path path = null;
        int[][] derivative_maze;

        if (initial_maze.length == 0) {
            success = false;

        } else {
            int[][] valid_maze = convertMaze(initial_maze);
            derivative_maze = parseInputMaze(valid_maze);

            if (derivative_maze != null) {
                path = getShortestPath(derivative_maze);
            } else {
                success = false;
            }
        }

        if (onShortestPathFoundListener != null) {
            onShortestPathFoundListener.onShortestPathFound(success, path);
        }
    }

    /**
     * Validates and converts the given string array in a int array.
     *
     * Returns an array of integers when the conditions of each element in the string array to be a digit is valid.
     * If the given array is not valid, returns null.
     *
     * Conditions:
     * 1. First character is a digit or arithmetic sign '+' or '-'.
     * 2. If the first character is a sign, the length of the string must be bigger than 1.
     * 3. From second char ahead, all must be digits.
     * */
    private int[][] convertMaze(String[][] initial_maze) {

        int[][] converted_maze = new int[initial_maze.length][initial_maze[0].length];

        for (int i = 0; i < initial_maze.length; i++) {
            for (int j = 0; j < initial_maze[0].length; j++) {

                // Validate that the element accomplish with:
                // 1. First character is a digit or arithmetic sign '+' or '-'
                // 2. If the first character is a sign, the length of the string must be bigger than 1
                // 3. From second char ahead, all must be digits
                String elementString = initial_maze[i][j];
                for (int x = 0; x < elementString.length(); x++) {
                    char c = elementString.charAt(x);

                    boolean valid;

                    if (x == 0) {
                        valid = Character.isDigit(c) || (c == '-' || c == '+' && elementString.length() > 1);
                    } else {
                        valid = Character.isDigit(c);
                    }

                    if (!valid) {
                        return null;
                    }
                }

                // So the element is valid. Now let's convert it from string to int and place it in the int array
                int number = Integer.parseInt(elementString);
                converted_maze[i][j] = number;
            }
        }

        return converted_maze;
    }

    /**
     * Calculates a derivative maze with the smallest distance between each node and one of its left-adjacent nodes.
     *
     * Chooses the smallest of the additions between the number in the node we are reviewing,
     * and the left-adjacent three nodes which we can come from.
     *
     * The smallest addition is stored in a new derivative maze.
     * */
    private int[][] parseInputMaze(int[][] initial_maze) {

        if (initial_maze == null) {
            return null;
        }

        int maxRow = initial_maze.length;
        int maxColumn = initial_maze[0].length;

        int[][] derivative_maze = new int[maxRow][maxColumn];

        for (int col = 0; col < maxColumn; col++) {
            for (int row = 0; row < maxRow; row++) {

                if (col == 0) {
                    derivative_maze[row][col] = initial_maze[row][col];
                } else {

                    int upperNode;
                    if ((row - 1) < 0) {
                        upperNode = derivative_maze[maxRow - 1][col - 1];
                    } else {
                        upperNode = derivative_maze[row - 1][col - 1];
                    }

                    int centerNode = derivative_maze[row][col - 1];

                    int lowerNode;
                    if ((row + 1) >= maxRow) {
                        lowerNode = derivative_maze[0][col - 1];
                    } else {
                        lowerNode = derivative_maze[row + 1][col - 1];
                    }

                    derivative_maze[row][col] = initial_maze[row][col] + (Math.min(upperNode, Math.min(centerNode, lowerNode)));
                }
            }
        }

        return derivative_maze;
    }

    /**
     * Calculates the shortest path in the derivative maze as parameter.
     *
     * To get the shortest path:
     *
     * On the derivative maze:
     * 1. The node in the last column with the smallest distance is taken and its value stored in a array.
     * 2. Find the left-adjacent node with the smallest value and store it in the array.
     * 3. Select this node and repeat steps 1 and 2 until the first column is reached.
     * 4. Reverse the order of the array.
     *
     * */
    private Path getShortestPath(int[][] derivative_maze) {

        int maxRow = derivative_maze.length;
        int maxColumn = derivative_maze[0].length;

        Path path = new Path();
        Node node = new Node();

        // Find our first node, which is the node of the last column with the lowest cost
        node.setCost(derivative_maze[0][maxColumn - 1]);
        node.setRow(0);
        node.setCol(maxColumn - 1);
        for (int row = 1; row < maxRow; row++) {
            if (derivative_maze[row][maxColumn - 1] < node.getCost()) {
                node.setCost(derivative_maze[row][maxColumn - 1]);
                node.setRow(row);
                node.setCol(maxColumn - 1);
            }
        }

        // If the value of the node is bigger than 50, then the result of the path is unsuccessful and the node is not stored
        if (node.getCost() > 50) {
            success = false;
        } else {
            path.addNode(node);
        }

        // Find the node with the minimum cost of between the three nodes adjacent to the current node
        for (int col = maxColumn - 1; col > 0; col--) {

            Node upperNode = new Node();
            if ((node.getRow() - 1) < 0) {
                upperNode.setCost(derivative_maze[maxRow - 1][col - 1]);
                upperNode.setRow(maxRow - 1);
                upperNode.setCol(col - 1);

            } else {
                upperNode.setCost(derivative_maze[node.getRow() - 1 ][col - 1]);
                upperNode.setRow(node.getRow() - 1);
                upperNode.setCol(col - 1);
            }

            Node centerNode = new Node();
            centerNode.setCost(derivative_maze[node.getRow()][col - 1]);
            centerNode.setRow(node.getRow());
            centerNode.setCol(col - 1);

            Node lowerNode = new Node();
            if ((node.getRow() + 1) >= maxRow) {
                lowerNode.setCost(derivative_maze[0][col - 1]);
                lowerNode.setRow(0);
                lowerNode.setCol(col - 1);

            } else {
                lowerNode.setCost(derivative_maze[node.getRow() + 1][col - 1]);
                lowerNode.setRow(node.getRow() + 1);
                lowerNode.setCol(col - 1);
            }

            // Compare the nodes to get the minor of the three
            Node aux_node = upperNode;
            if (centerNode.getCost() < aux_node.getCost()) {
                aux_node = centerNode;
            }
            if (lowerNode.getCost() < aux_node.getCost()) {
                aux_node = lowerNode;
            }

            if (aux_node.getCost() < 50) {
                path.addNode(aux_node);
            }

            node = aux_node;
        }

        // Reverse the order of the list to get it from the initial point to the final
        Collections.reverse(path.getNodeList());

        return path;
    }

    /**
     * Informs that the calculation as finished.
     *
     * The result of the calculation might be successful or unsuccessful.
     * If the distance of the smallest path is bigger than 50.
     *
     * It returns both the result of the calculation and the shortest path as parameters.
     * */
    public interface OnShortestPathFoundListener {
        void onShortestPathFound(boolean success, Path path);
    }

    public void setOnShortestPathFoundListener(OnShortestPathFoundListener onShortestPathFoundListener) {
        this.onShortestPathFoundListener = onShortestPathFoundListener;
    }
}
