package com.shortestpath.mazeparser.datamodels;

import com.shortestpath.app.mazeparser.datamodels.Node;
import com.shortestpath.app.mazeparser.datamodels.Path;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class PathTest {

    private Path path;

    @Before
    public void setUp() throws Exception {
        path = new Path();
    }

    @Test
    public void testGetNodeList() throws Exception {

        // Validate that the returned List (list of nodes) is never null
        assertNotNull(path.getNodeList());
    }

    @Test
    public void testGetDistance() throws Exception {

        // Validate that getCost returns size equals 0 when path is empty
        assertEquals(0, path.getCost());

        // Validate that getCost returns size bigger than zero when path contains at least one node
        Node node1 = new Node();
        path.addNode(node1);
        assertTrue(path.getCost() >= 0);
    }
}
