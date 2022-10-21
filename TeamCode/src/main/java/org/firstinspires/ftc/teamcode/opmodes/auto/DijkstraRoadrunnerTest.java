package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.Dijkstra;
import org.firstinspires.ftc.teamcode.graph.Graph;
import org.firstinspires.ftc.teamcode.graph.Node;

import java.util.List;

@Autonomous(name = "testgrid")

public class DijkstraRoadrunnerTest extends LinearOpMode {
    private Graph graph = new Graph();
    private SampleTankDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleTankDrive(hardwareMap);
        waitForStart();
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
                    if (Math.abs(next.getY()) <= 60 && Math.abs(next.getX()) <= 60) {
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
        Telemetry t = FtcDashboard.getInstance().getTelemetry();
        t.addData("a", "built");
        t.update();
        TrajectoryBuilder builder = drive.trajectoryBuilder(new Pose2d(12, 12, 0));
        Node start = findNode(12, 12), end = findNode(60, 60);
        t.addData(start.x + " " + start.y, end.x+ " " + end.y);
        t.update();
        List<Node> path = getpath(start, end);

        path.remove(0);
        path.add(end);
        for (Node n : path) {
            builder.splineTo(new Vector2d(n.getX(), n.getY()), drive.getPoseEstimate().getHeading());
        }


        drive.followTrajectory(builder.build());
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("a", "done");
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        while (opModeIsActive()) {

        }
    }

    private Node findNode(int x, int y) {
        for (Node n : graph.getNodes()) {
            if (n.x == x && n.y == y) {
                return n;
            }
        }
        return null;
    }

    private List<Node> getpath(Node from, Node to) {
        Dijkstra.calculateShortestPathFromSource(graph, from);
        for (Node n : graph.getNodes()) {
            if (n.equals(to)) {
                List<Node> path = n.getShortestPath();
                TelemetryPacket p = new TelemetryPacket();
                for (Node n2 : path) {
                    p.put("path", n2.toString());
                }
                FtcDashboard.getInstance().sendTelemetryPacket(p);
                return path;
            }
        }
        return null;
    }
}
