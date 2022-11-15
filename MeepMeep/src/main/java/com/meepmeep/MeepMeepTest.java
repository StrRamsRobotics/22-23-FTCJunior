package com.meepmeep;

import static com.meepmeep.Pathfind.pathfind;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequenceBuilder;

public class MeepMeepTest {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setStartPose(new Pose2d(36, 60))
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(300, 300, Math.toRadians(1440), Math.toRadians(720), 15).build();
        DriveShim drive = myBot.getDrive();
        Node cones = new Node(60, 12);
        Node junction = new Node(36, 12);
        TrajectorySequence first = pathfind(drive, new Pose2d(36, 60), cones).build();
        myBot.followTrajectorySequence(first);
        TrajectorySequence second = pathfind(drive, first.end(), junction).build();
        myBot.followTrajectorySequence(second);

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .addEntity(myBot)
                .start();
    }
}
