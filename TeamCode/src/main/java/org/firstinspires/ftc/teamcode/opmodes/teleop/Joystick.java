package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.opmodes.auto.Pathfinding;

@TeleOp(name = "Joystick")
public class Joystick extends LinearOpMode {

    boolean processing;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);

        waitForStart();


        while (opModeIsActive()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            0,
                            -gamepad1.right_stick_x
                    )
            );
            if (gamepad1.a && !processing) {
                processing = true;
                pressButton(Pathfinding.aStar);
                processing = false;
            }
        }
    }

    private void pressButton(AStar aStar) {
        //javier
    }
}