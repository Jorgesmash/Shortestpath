package com.shortestpath.app.mazeparser.datamodels;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List nodeList;

    public Path() {
        nodeList = new ArrayList();
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public List getNodeList() {
        return nodeList;
    }

    public int getCost() {
        if (nodeList.size() == 0) {
            return 0;
        }

        Node lastNode = (Node) nodeList.get(nodeList.size() - 1);
        return lastNode.getCost();
    }
}
