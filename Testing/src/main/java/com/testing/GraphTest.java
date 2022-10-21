package com.testing;

import java.util.ArrayList;


public class GraphTest {
    public static void main(String[] args) {
        ArrayList<Node> start = new ArrayList<>();
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                start.add(new Node(i, j));
            }
        }
        ArrayList<Node> expand = new ArrayList<>();
        for (int i = -64; i <= 64; i++) {
            for (int j = -64; j <= 64; j++) {
                for (Node ref : start) {
                    if (Math.sqrt(Math.pow(i - ref.getRow(), 2) + Math.pow(j - ref.getCol(), 2)) <= 9) { //8 inch radius with 1 inch wiggle
                        Node n = new Node(i, j);
                        n.setBlock(true);
                        expand.add(n);
                    }
                }
            }
        }
        System.out.println(expand);
    }

}
