package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class MeepMeepTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    drive.setPoseEstimate(new Pose2d(-60, -60, Math.toRadians(90)));
                    TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(-60, -60, Math.toRadians(90))); //path won't gen at 0, 0 bc 0,0 is a ground junction
                    Node initialNode = new Node(-60, -60);
                    Node finalNode = new Node(60, 60);
                    AStar aStar = new AStar(64, 64, initialNode, finalNode);
                    for (int i = -48; i <= 48; i += 24) {
                        for (int j = -48; j <= 48; j += 24) {
                            if (i % 48 == 0 && j % 48 == 0) {
                                aStar.setBlock(i, j, 1);
                                //System.out.format("%d %d is a ground junction\n", i, j);
                            } else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0)) {
                                // System.out.format("%d %d is a high junction\n", i, j);
                                aStar.setBlock(i, j, 33.5);
                            } else if (Math.abs(i) == 24 && Math.abs(j) == 24) {
                                // System.out.format("%d %d is a medium junction\n", i, j);
                                aStar.setBlock(i, j, 23.5);
                            } else {
                                // System.out.format("%d %d is a low junction\n", i, j);
                                aStar.setBlock(i, j, 13.5);
                            }
                        }
                    }


                    ArrayList<Node> path = aStar.findPath();
                    path.remove(initialNode);
                    ArrayList<Node> toRemove = new ArrayList<>();
                    if (path.size() > 1) {
                        Node prev = initialNode, ref = initialNode;
                        for (Node cur : path) {
                            if (!(ref.x == cur.x || ref.y == cur.y)) {
                                ref = cur;
                                toRemove.remove(prev);
                            }
                            toRemove.add(cur);
                            prev = cur;
                        }
                    }
                    path.removeAll(toRemove);
                    if (!path.contains(finalNode)) {
                        path.add(finalNode);
                    }
                    Node prev = initialNode;
                    double headingEstimate = drive.getPoseEstimate().getHeading();
                    for (Node n : path) {
                        //math.atan2 gets around tan(90) being undefined
                        double rot = Math.atan2(n.y - prev.y, n.x - prev.x);
                        builder.turn(rot - headingEstimate);
                        headingEstimate += rot - headingEstimate;
                        //builder.splineTo(new Vector2d(n.x, n.y),headingEstimate);
                         builder.forward(Math.max(Math.abs(prev.x - n.x), Math.abs(prev.y - n.y)));
                        prev = n;
                    }
                    return builder.build();
                });

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .addEntity(myBot)
                .start();
    }

    private static void rotate(DriveShim drive, double junctionX, double junctionY, TrajectorySequenceBuilder builder) {

    }
}
