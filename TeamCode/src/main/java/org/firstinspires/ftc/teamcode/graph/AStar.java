package org.firstinspires.ftc.teamcode.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;


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
    private int spacing = 12;
    public HashSet<Node> blocks = new HashSet<>();

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

        int lowerX = x + spacing;

        if (Math.abs(lowerX) <= xBound) {
            if (Math.abs(y - spacing) <= yBound) {
                //checkNode(currentNode, y - spacing, lowerX, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            if (Math.abs(y + spacing) <= yBound) {
                //checkNode(currentNode, y + spacing, lowerX, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }

            checkNode(currentNode, y, lowerX, getHvCost());

        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int x = currentNode.x;
        int y = currentNode.y;
        if (Math.abs(y - spacing) <= yBound) {
            checkNode(currentNode, y - spacing, x, getHvCost());
        }
        if (Math.abs(y + spacing) <= yBound) {

            checkNode(currentNode, y + spacing, x, getHvCost());

        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int x = currentNode.x;
        int y = currentNode.y;
        int upperRow = x - spacing;
        if (Math.abs(y - spacing) <= yBound) {
            //checkNode(currentNode, y - spacing, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
        }
        if (Math.abs(y + spacing) <= yBound) {
            //checkNode(currentNode, y + spacing, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
        }

        checkNode(currentNode, y, upperRow, getHvCost());


    }

    private void checkNode(Node currentNode, int y, int x, int cost) {
        Node adjacentNode = new Node(x, y);
        adjacentNode.calculateHeuristic(finalNode);
        Node cur = adjacentNode, prev = currentNode;
        for (Node block : blocks) {
            double distance = (Math.abs((cur.x - prev.x) * (prev.y - block.y) - (prev.x - block.x) * (cur.y - prev.y))) / (Math.sqrt(Math.pow(cur.x - prev.x, 2) + Math.pow(cur.y - prev.y, 2)));
            //1 inch wiggle room is given here (distance<1)
            //currently set to 0 because for some reason it gives a shorter path if you set block radius to 11 instead of 8
            if (distance <= 11 && prev.x <= block.x && cur.x >= block.x && prev.y <= block.y && cur.y >= block.y) {
                return;
            }
        }
        if (!getClosedSet().contains(adjacentNode)) {
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

    public void setBlock(int x, int y, double h) {
        blocks.add(new Node(x, y, h));
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

