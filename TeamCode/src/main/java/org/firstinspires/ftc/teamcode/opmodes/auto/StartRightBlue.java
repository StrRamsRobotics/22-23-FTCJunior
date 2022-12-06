package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

@Autonomous(name="rightBlue")
public class StartRightBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
        Intake.init(hardwareMap);
        waitForStart();
        Pathfinding.run(Positions.RIGHT_BLUE);
    }
}
