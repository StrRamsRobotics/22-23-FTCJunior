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

        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(-60, -60)); //path won't gen at 0, 0 bc 0,0 is a ground junction
                    Node initialNode = new Node(-60, -60);
                    Node finalNode = new Node(36, -12);
                    AStar aStar = new AStar(64, 64, initialNode, finalNode);
                    for (int i = -48; i <= 48; i += 24) {
                        for (int j = -48; j <= 48; j += 24) {
                            aStar.setBlock(i, j);
                        }
                    }
                  /*  ArrayList<Node> blocks = new ArrayList<>();
                    for (int i = -64; i <= 64; i++) {
                        for (int j = -64; j <= 64; j++) {
                            for (Node ref : start) {
                                if (Math.sqrt(Math.pow(i - ref.x, 2) + Math.pow(j - ref.y, 2)) <= 11) { //8 inch robot radius + 3 inch radius junction
                                    aStar.setBlock(i, j);
                                    blocks.add(new Node(i, j));
                                }
                            }
                        }
                    }*/

                    ArrayList<Node> path = aStar.findPath();
                    path.remove(initialNode);
                    for (Node n : path) {
                        builder.lineTo(new Vector2d(n.x, n.y));
                    }
                    return builder.build();
                });

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .addEntity(myBot)
                .start();
    }
}
