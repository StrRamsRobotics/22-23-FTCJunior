package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

@Autonomous(name = "leftBlue")
public class StartLeftBlue extends LinearOpMode {
//place the robot so it's along the line closest to the substation (8 inch offset for robot radius)
    //left = from the perspective of the red substation (eepmeep's coord system)
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
        Intake.init(hardwareMap);
        waitForStart();
        Pathfinding.run(Positions.LEFT_BLUE);
    }
}
