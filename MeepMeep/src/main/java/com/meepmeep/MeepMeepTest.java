package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class MeepMeepTest {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    Node initialNode = new Node(-48, -60);
                    Node finalNode = new Node(-48, -12);
                    drive.setPoseEstimate(new Pose2d(initialNode.x, initialNode.y, Math.toRadians(0)));
                    TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(initialNode.x, initialNode.y, Math.toRadians(0))); //path won't gen at 0, 0 bc 0,0 is a ground junction

                    AStar aStar = new AStar(64, 64, initialNode, finalNode);
                    for (int i = -48; i <= 48; i += 24) {
                        for (int j = -48; j <= 48; j += 24) {
                            if (i % 48 == 0 && j % 48 == 0) {
                                aStar.setBlock(i, j, 1);
                            } else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0)) {
                                aStar.setBlock(i, j, 33.5);
                            } else if (Math.abs(i) == 24 && Math.abs(j) == 24) {
                                aStar.setBlock(i, j, 23.5);
                            } else {
                                aStar.setBlock(i, j, 13.5);
                            }
                        }
                    }
                    //special blocks
                    aStar.setBlock(36, 36, 0);
                    aStar.setBlock(-36, 36, 0);
                    aStar.setBlock(36, -36, 0);
                    aStar.setBlock(-36, -36, 0);
                    ArrayList<Node> path = aStar.findPath();
                    if (!path.contains(finalNode)) {
                        path.add(finalNode);
                    }
                    assert path.size() > 1; //size==1 means initialnode=finalnode
                    Node start = path.get(0), second = path.get(1);
                    double headingEstimate = Math.atan2(second.y - start.y, second.x - start.x);
                    if (Math.abs(headingEstimate - drive.getPoseEstimate().getHeading()) >= 0.01) {
                        builder.turn(headingEstimate - drive.getPoseEstimate().getHeading()); //correct starting heading
                    }
                    if (path.size() > 2) { //aka you have to turn
                        for (int i = 0; i < path.size() - 2; i++) {
                            Node n = path.get(i), next1 = path.get(i + 1), next2 = path.get(i + 2);
                            //math.atan2 gets around tan(90) being undefined
                            double rot = Math.atan2(next2.y - next1.y, next2.x - next1.x);
                            if (Math.abs(rot - headingEstimate) >= 0.01) {

                                builder.splineTo(new Vector2d(next2.x, next2.y), rot);
                                headingEstimate = rot;
                            } else {
                                double firstDis = Math.max(Math.abs(next1.x - n.x), Math.abs(next1.x - n.y));
                                if (firstDis > 0.01) {
                                    builder.forward(firstDis);
                                }
                            }
                        }
                    } else {
                        builder.forward(Math.max(Math.abs(second.x - start.x), Math.abs(second.y - start.y))); //if the path is just a straight line
                    }
                    path.remove(initialNode);
                    /* example of rounded corners:
                    builder.splineTo(new Vector2d(-60,-24),Math.toRadians(90))
                            .splineTo(new Vector2d(-48,-12),0)
                            .splineTo(new Vector2d(36,-12),0);*/
                    return builder.build();
                });

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .addEntity(myBot)
                .start();
    }
}
