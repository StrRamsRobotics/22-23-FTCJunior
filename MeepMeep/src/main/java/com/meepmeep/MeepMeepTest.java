package com.meepmeep;

import static com.meepmeep.BadPathing.getPath;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class Pathfind {
    public static TrajectorySequenceBuilder pathfind(DriveShim drive, Pose2d initialPose, Node finalNode) {
        TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(initialPose);
        Node initialNode = new Node((int) Math.round(initialPose.getX()), (int) Math.round(initialPose.getY()));
        ArrayList<Node> path = getPath(initialNode, finalNode);
        path.add(0, initialNode);
        assert path.size() > 1; //size==1 means initialnode=finalnode
        assert path.size() <= 3; //a* paths should only have 1 turn now so i don't think we have to account for path.size()>3
        Node n = path.get(0), next1 = path.get(1);
        double initialRot = Math.atan2(next1.y - n.y, next1.x - n.x);
        if (Math.abs(initialRot -initialPose.getHeading()) >= 0.01) {
            builder.turn(initialRot - initialPose.getHeading()); //correct starting heading
        }
        if (path.size() > 2) { //aka you have to turn
            System.out.println("Need to turn");
            Node next2 = path.get(2);
            double rot = Math.atan2(next2.y - next1.y, next2.x - next1.x);
            if (Math.abs(rot - initialRot) >= 0.01) {
                double step1X = next1.x, step2X = next1.x, step1Y = next1.y, step2Y = next1.y;
                if (Math.abs(next1.x - n.x) >= 0.01) { //offset in opposite direction
                    step1X += next1.x < n.x ? 12 : -12;
                } else {
                    step1Y += next1.y < n.y ? 12 : -12;
                }
                if (Math.abs(next2.x - next1.x) >= 0.01) { //offset in same direction
                    step2X += next2.x < next1.x ? -12 : 12;
                } else {
                    step2Y += next2.y < next1.y ? -12 : 12;
                }
                builder.forward(Math.abs(step1X - n.x) + Math.abs(step1Y - n.y));
                builder.splineTo(new Vector2d(step2X, step2Y), rot);
                builder.forward(Math.max(Math.abs(next2.x - step2X), Math.abs(next2.y - step2Y)));

            }
        } else {
            builder.forward(Math.max(Math.abs(next1.x - n.x), Math.abs(next1.y - n.y))); //if the path is just a straight line
        }
        path.remove(initialNode);
        return builder;
    }
}
