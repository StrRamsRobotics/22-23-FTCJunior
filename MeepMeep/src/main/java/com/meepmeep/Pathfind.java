package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class Pathfind {
    public static void setBlocks(AStar aStar) {
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                if (i % 48 == 0 && j % 48 == 0) aStar.setBlock(i, j, 1);
                else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0))
                    aStar.setBlock(i, j, 33.5);
                else if (Math.abs(i) == 24 && Math.abs(j) == 24) aStar.setBlock(i, j, 23.5);
                else aStar.setBlock(i, j, 13.5);
            }
        }
    }

    public static TrajectorySequenceBuilder pathfind(DriveShim drive, Pose2d initialPose, Node finalNode) {
        TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(initialPose);
        Node initialNode = new Node((int) Math.round(initialPose.getX()), (int) Math.round(initialPose.getY()));
        AStar aStar = new AStar(64, 64, initialNode, finalNode);
        setBlocks(aStar);
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
        if (!path.contains(initialNode)) {
            path.add(0, initialNode);
        }
        Node n = path.get(0), next1 = path.get(1);
        double initialRot = Math.atan2(next1.y - n.y, next1.x - n.x);
        if (Math.abs(initialRot - initialPose.getHeading()) >= 0.01) {
            builder.turn(initialRot - initialPose.getHeading());
            //correct starting heading
        }
        Node prev = initialNode;
        double headingEstimate = initialRot;
        boolean start =true;
        for (Node n1 : path) {
            //math.atan2 gets around tan(90) being undefined
            double rot = Math.atan2(n1.y - prev.y, n1.x - prev.x);
            if (start) {
                start=false;
                rot=initialRot;
            }
            double turnAmount=rot - headingEstimate;
            if (Math.abs(turnAmount)>Math.PI) {
                turnAmount=(-(2*Math.PI-turnAmount))%Math.toRadians(360);
            }
            builder.turn(turnAmount);
            headingEstimate += turnAmount;

            double dist = Math.max(Math.abs(prev.x - n1.x), Math.abs(prev.y - n1.y));
            if (dist > 0.1) {
                builder.forward(dist);
            }
            prev = n1;
        }
        return builder;
    }
}
