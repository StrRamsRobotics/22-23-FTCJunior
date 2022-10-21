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
                    TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(-64, -64)); //path won't gen at 0, 0 bc 0,0 is a ground junction
                    Node initialNode = new Node(-64, -64);
                    Node finalNode = new Node(64, 64);
                    AStar aStar = new AStar(64, 64, initialNode, finalNode);
                    ArrayList<Node> start = new ArrayList<>();
                    for (int i = -48; i <= 48; i += 24) {
                        for (int j = -48; j <= 48; j += 24) {
                            start.add(new Node(i, j));
                        }
                    }
                    ArrayList<Node> blocks = new ArrayList<>();
                    for (int i = -64; i <= 64; i++) {
                        for (int j = -64; j <= 64; j++) {
                            for (Node ref : start) {
                                if (Math.sqrt(Math.pow(i - ref.x, 2) + Math.pow(j - ref.y, 2)) <= 11) { //8 inch robot radius
                                    aStar.setBlock(i, j);
                                    blocks.add(new Node(i, j));
                                }
                            }
                        }
                    }

                    ArrayList<Node> path = aStar.findPath(), trimmed = new ArrayList<>();
                    Node prev = path.get(0);
                    trimmed.add(prev);
                    label:
                    for (int i = 1; i < path.size(); i++) {

                        Node cur = path.get(i);
                        for (Node block : blocks) {
                            double distance = (Math.abs((cur.x - prev.x) * (prev.y - block.y) - (prev.x - block.x) * (cur.y - prev.y))) / (Math.sqrt(Math.pow(cur.x - prev.x, 2) + Math.pow(cur.y - prev.y, 2)));
                            //1 inch wiggle room is given here (distance<1)
                            //currently set to 0 because for some reason it gives a shorter path if you set block radius to 11 instead of 8
                            if (distance==0 && prev.x <= block.x && cur.x >= block.x && prev.y <= block.y && cur.y >= block.y) {
                                trimmed.add(cur);
                                prev = cur;
                                continue label;
                            }
                        }
                    }
                    if (trimmed.size() == 0 || !trimmed.get(trimmed.size() - 1).equals(finalNode)) {
                        trimmed.add(finalNode);
                    }
                    trimmed.remove(initialNode);
                    for (Node n : trimmed) {
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
