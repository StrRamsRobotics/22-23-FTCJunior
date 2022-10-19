package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.SampleTankDrive;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.List;

public class MeepMeepTest {
    private static Graph graph = new Graph();
    public static void main(String[] args) {
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
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                            TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(12, 12, 0));
                            Node start = findNode(12, 12), end = findNode(60, 60);
                            List<Node> path = getpath(start, end);
                            path.remove(0);
                            path.add(end);
                            for (Node n : path) {
                                builder.splineTo(new Vector2d(n.getX(), n.getY()), drive.getPoseEstimate().getHeading());
                            }
                            return builder.build();
                        });

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .addEntity(myBot)
                .start();
    }
    private static Node findNode(int x, int y) {
        for (Node n : graph.getNodes()) {
            if (n.x==x&&n.y==y) {
                return n;
            }
        }
        return null;
    }

    private static List<Node> getpath(Node from, Node to) {
        Dijkstra.calculateShortestPathFromSource(graph, from);
        for (Node n : graph.getNodes()) {
            if (n == to) {
                return n.getShortestPath();
            }
        }
        return null;
    }
}
