package com.shortestpath.app.mazeparser.datamodels;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NodeJUnitTest {

    @Test // Tests the method Node.getCost()
    public void testGetCost() throws Exception {

        // Validate that getTotalCost returns cost = 0 when a cost has not been set
        Node node1 = new Node();
        assertEquals(0, node1.getCost());

        // Validate that getTotalCost returns the same set value
        int cost = 12;
        Node node2 = new Node();
        node2.setCost(cost);
        assertEquals(cost, node2.getCost());
    }

    @Test // Tests the method Node.getRow()
    public void testGetRow() throws Exception {

        // Validate that getRow returns 0 when no rows have been set
        Node node1 = new Node();
        assertEquals(0, node1.getRow());

        // Validate that getRow returns the same set value
        int row = 12;
        Node node2 = new Node();
        node2.setRow(row);
        assertEquals(row, node2.getRow());
    }

    @Test // Tests the method Node.getCol()
    public void testGetCol() throws Exception {

        // Validate that getTotalCost returns col = 0 when a col has not been set
        Node node1 = new Node();
        assertEquals(0, node1.getCol());

        // Validate that getTotalCost returns the same set value
        int col = 12;
        Node node2 = new Node();
        node2.setCol(col);
        assertEquals(col, node2.getCol());
    }
}
