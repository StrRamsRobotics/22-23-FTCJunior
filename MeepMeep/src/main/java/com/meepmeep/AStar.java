package com.meepmeep;

import java.util.*;

/**
 * A Star Algorithm
 *
 * @author Marcelo Surriabre
 * @version 2.1, 2017-02-23
 */
public class AStar {
    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private int hvCost;
    private int diagonalCost;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;
    private int xBound, yBound;
    private HashSet<Node> blocks = new HashSet<>();

    //an xbound of 64 = -64 is minimum, 64 is maximum
    public AStar(int xBound, int yBound, Node initialNode, Node finalNode, int hvCost, int diagonalCost) {
        this.hvCost = hvCost;
        this.xBound = xBound;
        this.yBound = yBound;
        this.diagonalCost = diagonalCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        //setNodes();
        this.closedSet = new HashSet<>();
    }

    public AStar(int xBound, int yBound, Node initialNode, Node finalNode) {
        this(xBound, yBound, initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    private void setNodes() {
        for (int i = -xBound; i <= xBound; i++) {
            for (int j = -yBound; j <=yBound; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
            }
        }
    }

    public ArrayList<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<Node> getPath(Node currentNode) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int x = currentNode.x;
        int y = currentNode.y;
        int lowerX = x + 1;
        if (Math.abs(lowerX) <= xBound) {
            if (Math.abs(y - 1) <= yBound) {
                checkNode(currentNode, y - 1, lowerX, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            if (Math.abs(y + 1) <= yBound) {
                checkNode(currentNode, y + 1, lowerX, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, y, lowerX, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int x = currentNode.x;
        int y = currentNode.y;
        if (Math.abs(y - 1) <= yBound) {
            checkNode(currentNode, y - 1, x, getHvCost());
        }
        if (Math.abs(y + 1) <= yBound) {
            checkNode(currentNode, y + 1, x, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int x = currentNode.x;
        int y = currentNode.y;
        int upperRow = x - 1;
        if (upperRow >= 0) {
            if (Math.abs(y - 1) <= yBound) {
                checkNode(currentNode, y - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            if (Math.abs(y + 1) <= yBound) {
                checkNode(currentNode, y + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, y, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int y, int x, int cost) {
        Node adjacentNode = new Node(x, y);
        adjacentNode.calculateHeuristic(finalNode);
        boolean block = blocks.contains(adjacentNode);
        if (!block && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    public void setBlock(int x, int y) {
        blocks.add(new Node(x, y));
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }


    public PriorityQueue<Node> getOpenList() {
        return openList;
    }


    public Set<Node> getClosedSet() {
        return closedSet;
    }


    public int getHvCost() {
        return hvCost;
    }


    private int getDiagonalCost() {
        return diagonalCost;
    }

}

