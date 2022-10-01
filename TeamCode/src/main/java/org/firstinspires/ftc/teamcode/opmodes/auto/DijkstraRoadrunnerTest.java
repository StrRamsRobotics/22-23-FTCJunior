package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.Graph;
import org.firstinspires.ftc.teamcode.graph.Node;

@Autonomous(name = "testgrid")
public class DijkstraRoadrunnerTest extends LinearOpMode {
    public static SampleTankDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        DijkstraRoadrunnerTest.drive = new SampleTankDrive(hardwareMap);
        Graph graph = new Graph(); //field grid
        Node[] start = new Node[]{
                new Node(12, 12),
                new Node(12, -12),
                new Node(-12, 12),
                new Node(-12, -12)
        };

        for (Node s : start) {
            graph.addNode(s);
            buildGraph(s, graph, new boolean[121][121]);
        }
    }

    private static void buildGraph(Node prev, Graph graph, boolean[][] vis) {
        vis[60 + prev.getX()][60 + prev.getY()] = true;
        Node[] nexts = new Node[]{
                new Node(prev.getX() + 24, prev.getY()),
                new Node(prev.getX() - 24, prev.getY()),
                new Node(prev.getX(), prev.getY() + 24),
                new Node(prev.getX(), prev.getY() - 24)
        };
        for (Node next : nexts) {
            if (Math.abs(next.getX()) > 60 || Math.abs(next.getY()) > 60) {
                continue;
            }
            prev.addDestination(next);
            next.addDestination(prev);
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
            if (!vis[60 + next.getX()][60 + next.getY()]) {
                buildGraph(next, graph, vis);
            }
        }
    }
}
