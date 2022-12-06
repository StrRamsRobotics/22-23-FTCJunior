package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public enum Positions {
    LEFT_BLUE(new Pose2d(32, 64, Math.toRadians(-90)), new Pose2d(60, 12, 0), new Pose2d(36, 12, 0)),
    RIGHT_BLUE(new Pose2d(-32, 64, Math.toRadians(-90)), new Pose2d(-60, 12, 0), new Pose2d(-36, 12, 0)),
    LEFT_RED(new Pose2d(-32, -64, Math.toRadians(90)), new Pose2d(-60, -12, 0), new Pose2d(-36, -12, 0)),
    RIGHT_RED(new Pose2d(-32, -64, Math.toRadians(-90)), new Pose2d(-60, -12, 0), new Pose2d(-36, -12, 0));

    public Pose2d start, cones, junction; //junctionspot = park2

    Positions(Pose2d pose2d, Pose2d pose2d1, Pose2d pose2d2) {

    }
}
