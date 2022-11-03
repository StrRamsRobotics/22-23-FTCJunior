package com.meepmeep;

import java.util.Objects;

/**
 * Node Class
 *
 * @author Marcelo Surriabre
 * @version 2.0, 2018-02-23
 */
public class Node {

    private int g;
    private int f;
    private int h;
    public int x;
    public int y;
    public double height;
    private Node parent;

    public Node(int x, int y,double height) {
        super();
        this.x = x;
        this.y = y;
        this.height=height;
    }
    public Node(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Node offset(int x, int y) {
        return new Node(x + x, this.y + y);
    }

    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.x - x) + Math.abs(finalNode.y - y);
    }

    public void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

}
