package com.testing;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class GraphTest {
    public static void main(String[] args) {
        Graph graph = new Graph();
        for (int i = -60; i <= 60; i += 24) {
            for (int j = -60; j <= 60; j += 24) {
                Node cur = new Node(i, j);
                Node[] nexts = new Node[]{
                        new Node(i + 24, j),
                        new Node(i - 24, j),
                        new Node(i, j + 24),
                        new Node(i, j - 24)
                };
                for (Node next : nexts) {
                    if (Math.abs(next.getY())<=60 && Math.abs(next.getX())<=60) {
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
                            old.addDestination(cur);
                            cur.addDestination(old);
                        }

                    }
                }
            }
        }
        /*
        HashMap<Pair, List<Node>> map = new HashMap<>();
        for (Node n : graph.getNodes()) {
            Dijkstra.calculateShortestPathFromSource(graph, n);
            for (Node n2 : graph.getNodes()) {
                if (n==n2) continue;
                Pair key = new Pair(n, n2);
                map.put(key, new ArrayList<>(n2.getShortestPath()));
            }
        }
*/
        Node start = (Node) graph.getNodes().toArray()[0], end = (Node) graph.getNodes().toArray()[1];
        System.out.println(getpath(graph, start, end));
    }

    private static List<Node> getpath(Graph graph, Node from, Node to) {
        Dijkstra.calculateShortestPathFromSource(graph, from);
        for (Node n : graph.getNodes()) {
            if (n == to) {
                return n.getShortestPath();
            }
        }
        return null;
    }
    private static class Pair {
        private final Node one, two;

        private Pair(Node one, Node two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(one, pair.one) && Objects.equals(two, pair.two);
        }

        @Override
        public int hashCode() {
            return Objects.hash(one, two);
        }
    }
}
