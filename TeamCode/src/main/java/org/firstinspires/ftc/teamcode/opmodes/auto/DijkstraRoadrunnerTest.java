package org.firstinspires.ftc.teamcode.opmodes.auto;

import android.util.Pair;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.kinematics.TankKinematics;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.Dijkstra;
import org.firstinspires.ftc.teamcode.graph.Graph;
import org.firstinspires.ftc.teamcode.graph.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Autonomous(name = "testgrid")

public class DijkstraRoadrunnerTest extends LinearOpMode {
    private Graph graph = new Graph();
    public static SampleTankDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleTankDrive(hardwareMap);
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

        TrajectoryBuilder builder = drive.trajectoryBuilder(new Pose2d(12, 12));
        Node start = findNode(12, 12), end = findNode(60, 60);
        List<Node> path = getpath(start, end);
        path.remove(0);
        path.add(end);
        for (Node n : path) {
            builder.splineTo(new Vector2d(n.getX(), n.getY()), drive.getPoseEstimate().getHeading());
        }
        drive.followTrajectory( builder.build());

    }

    private Node findNode(int x, int y) {
        for (Node n : graph.getNodes()) {
            if (n.x==x&&n.y==y) {
                return n;
            }
        }
        return null;
    }

    private List<Node> getpath(Node from, Node to) {
        Dijkstra.calculateShortestPathFromSource(graph, from);
        for (Node n : graph.getNodes()) {
            if (n == to) {
                return n.getShortestPath();
            }
        }
        return null;
    }
}
