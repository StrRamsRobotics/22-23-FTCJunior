package com.javredstone.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.javredstone.pathfind.BFS;
import com.javredstone.pathfind.Constants;
import com.javredstone.pathfind.Conversions;
import com.javredstone.pathfind.Path;
import com.javredstone.pathfind.Point;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;

public class MeepMeepTesting {
    public static DriveShim drive;
    public static TrajectorySequenceBuilder tsb;
    public static void runPaths(ArrayList<Path> paths) {
        double prevAngle = 0.0;
        for (int i = 0; i < paths.size(); i++) {
            Path pa = paths.get(i);
            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
            BFS bfs = new BFS();
            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
            if (tsb == null) {
                tsb = drive.trajectorySequenceBuilder(new Pose2d(-Conversions.getRealPos(start.y), -Conversions.getRealPos(start.x), -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x)));
            }
            if (i > 0) {
                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
                if (Math.abs(angle) > Math.toRadians(180)) {
                    angle = Math.toRadians(360) - Math.abs(angle);
                }
                tsb = tsb.turn(prevAngle + angle);
                prevAngle = angle;
            }
            for (int j = 0; j < path.length; j++) {
                Point p = path[j];
                Point d = p;
                if (j > 0) {
                    if (j < path.length - 1) {
                        d = path[j + 1];
//                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
                        tsb = tsb.splineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)), -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x));
                        prevAngle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
                    } else {
                        tsb = tsb.splineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)), prevAngle);
                    }
                }
            }
        }
    }
    public static void runManualPaths(ArrayList<Path> paths) {
        double prevAngle = 0.0;
        for (int i = 0; i < paths.size(); i++) {
            Path pa = paths.get(i);
            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
            BFS bfs = new BFS();
            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
            if (tsb == null) {
                prevAngle = -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x);
                tsb = drive.trajectorySequenceBuilder(new Pose2d(-Conversions.getRealPos(start.y), -Conversions.getRealPos(start.x), prevAngle));
            } // place down the robot facing this way
            if (i > 0) {
                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
                if (Math.abs(angle) > Math.toRadians(180)) {
                    angle = Math.toRadians(360) - Math.abs(angle);
                }
                tsb = tsb.turn(prevAngle + angle);
                prevAngle = angle;
            }
            for (int j = 0; j < path.length; j++) {
                Point p = path[j];
                Point d = p;
                if (j > 0) {
                    if (j < path.length - 1) {
                        d = path[j + 1];
//                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
                        double angle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
                        double newAngle = angle - prevAngle;
                        if (Math.abs(angle) > Math.toRadians(180)) {
                            angle = Math.toRadians(360) - Math.abs(angle);
                        }
                        if (Math.abs(newAngle) > Math.toRadians(180)) {
                            newAngle = Math.toRadians(360) - Math.abs(newAngle);
                        }
                        tsb = tsb.lineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)));
                        tsb = tsb.turn(newAngle);
                        prevAngle = angle;
                    } else {
                        tsb = tsb.lineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)));
                    }
                }
            }
        }
    }
    public static void runLiterallyManualPaths(ArrayList<Path> paths) {
        double prevAngle = 0.0;
        for (int i = 0; i < paths.size(); i++) {
            Path pa = paths.get(i);
            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
            BFS bfs = new BFS();
            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
            if (tsb == null) {
                prevAngle = -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x);
                tsb = drive.trajectorySequenceBuilder(new Pose2d(-Conversions.getRealPos(start.y), -Conversions.getRealPos(start.x), prevAngle));
            } // place down the robot facing this way
            if (i > 0) {
                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
                if (Math.abs(angle) > Math.toRadians(180)) {
                    angle = Math.toRadians(360) - Math.abs(angle);
                }
                tsb = tsb.turn(prevAngle + angle);
                prevAngle = angle;
            }
            for (int j = 0; j < path.length; j++) {
                Point p = path[j];
                Point d = p;
                if (j > 0) {
                    if (j < path.length - 1) {
                        d = path[j + 1];
//                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
                        double angle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
                        double newAngle = angle - prevAngle;
                        if (Math.abs(angle) > Math.toRadians(180)) {
                            angle = Math.toRadians(360) - Math.abs(angle);
                        }
                        if (Math.abs(newAngle) > Math.toRadians(180)) {
                            newAngle = Math.toRadians(360) - Math.abs(newAngle);
                        }
                        tsb = tsb.forward(12);
                        tsb = tsb.turn(newAngle);
                        prevAngle = angle;
                    } else {
                        tsb = tsb.forward(12);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15).build();

        drive = myBot.getDrive();
        double[][] chosen = Constants.LEFT_BLUE;
        Conversions.subdivide(chosen);
        ArrayList<Path> paths = new ArrayList<>();
        paths.add(new Path(3, false));
        for (int i = 0; i < Constants.TIMES_CONES + (Constants.PRELOAD ? 1 : 0); i++) {
            paths.add(new Path(4, false));
            paths.add(new Path(4, true));
        }
        paths.add(new Path(2, false));
        runPaths(paths);
//        runManualPaths(paths);
//        runLiterallyManualPaths(paths);
        myBot.followTrajectorySequence(tsb.build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}