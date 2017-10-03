package com.shortestpath.app.mazeparser.datamodels;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class PathJUnitTest {

    private Path path;

    @Before
    public void setUp() throws Exception {
        path = new Path();
    }

    @Test // Tests the method Path.getNodeList()
    public void testGetNodeList() throws Exception {

        // Validate that the returned List (list of nodes) is never null
        assertNotNull(path.getNodeList());
    }

    @Test // Tests the method Path.getTotalCost()
    public void testGetTotalCost() throws Exception {

        // Validate that getTotalCost returns size equals 0 when path is empty
        assertEquals(0, path.getTotalCost());

        // Validate that getTotalCost returns size bigger than zero when path contains at least one node
        Node node1 = new Node();
        path.addNode(node1);
        assertTrue(path.getTotalCost() >= 0);
    }
}
