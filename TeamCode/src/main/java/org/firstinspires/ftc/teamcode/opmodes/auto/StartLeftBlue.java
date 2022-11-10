package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

@Autonomous(name = "leftBlue")
public class StartLeftBlue extends LinearOpMode {
//place the robot so it's along the line closest to the substation (8 inch offset for robot radius)
    //left = from the perspective of the red substation (eepmeep's coord system)
    @Override
    public void runOpMode() throws InterruptedException {
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);
        waitForStart();
        drive.setPoseEstimate(new Pose2d(-32, 64,Math.toRadians(-90)));
        Pathfinding.run(drive);
    }
}
