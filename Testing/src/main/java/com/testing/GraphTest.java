package com.testing;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class GraphTest {
    // just so all the +60's don't get too confusing
    private static Node getNode(Node[][] graph, int x, int y) {
        return graph[60+x][60+y];
    }
    public static void main(String[] args) {
        Node[][] graph = new Node[121][121]; //node[x][y]
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
                        if (graph[60+next.getX()][60+next.getY()]==null) {
                            graph[60+next.getX()][60+next.getY()] = next;
                        }
                        graph[60+next.getX()][60+next.getY()].getAdjacentNodes().putAll(next.getAdjacentNodes());
                    }
                }
            }
        }
        System.out.println(getNode(graph, 60, 60).getAdjacentNodes()); //prints {(36,60)=24.0, (60,36)=24.0}
      /*  Dijkstra.calculateShortestPathFromSource(graph, (Node) graph.getNodes().toArray()[0]);

        HashMap<Pair, List<Node>> map = new HashMap<>();
        for (Node n : graph.getNodes()) {
            Dijkstra.calculateShortestPathFromSource(graph, n);
            for (Node n2 : graph.getNodes()) {
                if (n==n2) continue;
                Pair key = new Pair(n, n2);
                map.put(key, new LinkedList<>(n2.getShortestPath()));
            }
        }

        System.out.println(map.get(new Pair(new Node(12, 12), new Node(12, 36))));*/
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
