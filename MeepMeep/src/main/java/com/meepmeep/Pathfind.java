package com.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class Pathfind {
    public static void setBlocks(AStar aStar) {
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                if (i % 48 == 0 && j % 48 == 0) aStar.setBlock(i, j, 1);
                else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0)) aStar.setBlock(i, j, 33.5);
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
            //math.atan2 gets around tan(90) being undefined
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
