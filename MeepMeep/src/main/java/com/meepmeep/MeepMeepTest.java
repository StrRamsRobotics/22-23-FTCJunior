package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class MeepMeepTest {
    private static boolean isDivisible(int num, int divisor) {
        return num % divisor == 0;
    }
    public static ArrayList<Node> getPath(Node initialNode, Node finalNode) {
        AStar aStar = new AStar(64, 64, initialNode, finalNode);
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                if (isDivisible(i, 48) && isDivisible(j, 48)) {
                    aStar.setBlock(i, j, 1);
                    System.out.format("%d %d is a ground junction\n", i, j);
                } else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0)) {
                    System.out.format("%d %d is a high junction\n", i, j);
                    aStar.setBlock(i, j, 33.5);
                } else if (Math.abs(i) == 24 && Math.abs(j) == 24) {
                    System.out.format("%d %d is a medium junction\n", i, j);
                    aStar.setBlock(i, j, 23.5);
                } else {
                    System.out.format("%d %d is a low junction\n", i, j);
                    aStar.setBlock(i, j, 13.5);
                }
            }
        }
        ArrayList<Node> path = aStar.findPath();
        path.remove(initialNode);
        return path;
    }

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> {
                    TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(new Pose2d(-60, -60)); //path won't gen at 0, 0 bc 0,0 is a ground junction
                    Node initialNode = new Node(-60, -60);
                    Node finalNode = new Node(48, 36);
                    ArrayList<Node> path = getPath(initialNode, finalNode);
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
