package com.testing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Node {

    private Double distance = Double.MAX_VALUE;

    public int x = 0;
    public int y = 0;

    private Map<Node, Double> adjacentNodes = new HashMap<>();

    private LinkedList<Node> shortestPath = new LinkedList<>();

    public Node(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void addDestination(Node destination) {
        Double distance = Math.sqrt(Math.pow(destination.getX() - this.x, 2) + Math.pow(destination.getY() - this.y, 2));
        //System.out.println(distance);
        adjacentNodes.put(destination, distance);
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(x, node.x) && Objects.equals(y, node.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
