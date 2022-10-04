package org.firstinspires.ftc.teamcode.opmodes.auto;

import android.util.Pair;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.Dijkstra;
import org.firstinspires.ftc.teamcode.graph.Graph;
import org.firstinspires.ftc.teamcode.graph.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Autonomous(name = "testgrid")
public class DijkstraRoadrunnerTest extends LinearOpMode {
    public static SampleTankDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        DijkstraRoadrunnerTest.drive = new SampleTankDrive(hardwareMap);
        Graph graph = new Graph(); //field grid
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
        HashMap<Pair<Node, Node>, List<Node>> map = new HashMap<>();
        for (Node n : graph.getNodes()) {
                Dijkstra.calculateShortestPathFromSource(graph, n);
                for (Node n2 : graph.getNodes()) {
                    if (n==n2) continue;
                    Pair<Node, Node> key = new Pair<>(n, n2);
                    map.put(key, n2.getShortestPath());
                }
        }

        System.out.println(map.get(new Pair<>(new Node(60, 60), new Node(12, 12))));
    }
}
