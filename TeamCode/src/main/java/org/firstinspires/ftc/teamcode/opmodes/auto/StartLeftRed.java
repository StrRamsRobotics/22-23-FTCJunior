package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "leftRed")
public class StartLeftRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
        Intake.init(hardwareMap);
        waitForStart();
        MainAuto.run(Positions.LEFT_RED);
    }
}
