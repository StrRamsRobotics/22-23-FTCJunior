package com.testing;

public class GraphTest {
    public static void main(String[] args) {
        Graph graph = new Graph();
        for (int i = -36; i <= 36; i += 24) {
            for (int j = -36; j <= 36; j += 24) {
                Node cur = new Node(i, j);
                Node[] nexts = new Node[]{
                        new Node(i + 24, j),
                        new Node(i - 24, j),
                        new Node(i, j + 24),
                        new Node(i, j - 24)
                };
                for (Node next : nexts) {
                    if (Math.abs(next.getY())<=36 && Math.abs(next.getX())<=36) {
                        cur.addDestination(next);
                        next.addDestination(cur);
                        if (!graph.getNodes().contains(next)) {
                            graph.addNode(next);
                        } else {
                            Node old = next;
                            for (Node n : graph.getNodes()) {
                                if (n.equals(next)) {
                                    old = n;
                                    break;
                                }
                            }
                            old.getAdjacentNodes().putAll(next.getAdjacentNodes());

                        }

                    }
                }
            }
        }
        System.out.println(graph);
    }
    
}
