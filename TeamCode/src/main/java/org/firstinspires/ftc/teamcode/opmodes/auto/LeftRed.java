package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Constants;

@Autonomous(name = "leftRed")
public class LeftRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
        Intake.init(hardwareMap);
        waitForStart();
        MainAuto.run(Constants.LEFT_RED);
    }
}