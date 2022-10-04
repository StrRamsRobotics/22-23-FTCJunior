package com.testing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
                            old.getAdjacentNodes().putAll(next.getAdjacentNodes());

                        }

                    }
                }
            }
        }

        HashMap<Pair, List<Node>> map = new HashMap<>();
        for (Node n : graph.getNodes()) {
            Dijkstra.calculateShortestPathFromSource(graph, n);
            for (Node n2 : graph.getNodes()) {
                if (n==n2) continue;
                Pair key = new Pair(n, n2);
                map.put(key, new LinkedList<>(n2.getShortestPath()));
            }
        }

        System.out.println(map.get(new Pair(new Node(12, 12), new Node(12, 36))));
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
