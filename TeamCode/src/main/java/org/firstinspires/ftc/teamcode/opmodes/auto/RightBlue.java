package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Constants;

@Autonomous(name = "rightBlue")
public class RightBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
        Intake.init(hardwareMap);
        waitForStart();
        MainAuto.run(Constants.RIGHT_BLUE);
    }
}