package com.meepmeep;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    @Override
    public String toString() { //paste here https://csacademy.com/app/graph_editor/
        StringBuilder output = new StringBuilder();
        for (Node node : nodes) {
            for (Map.Entry<Node, Double> entry : node.getAdjacentNodes().entrySet()) {
                output.append(node + " " + entry.getKey() + " " + entry.getValue()+"\n");
            }
        }
        return output.toString();
    }
}
