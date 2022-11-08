package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.opmodes.auto.Pathfinding;

@TeleOp(name = "Joystick")
public class Joystick extends LinearOpMode {

    DcMotor left, right; //2wd for now
    boolean processing;

    @Override
    public void runOpMode() throws InterruptedException {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();


        while (opModeIsActive()) {
            float rightx = gamepad1.right_stick_x, lefty = -gamepad1.left_stick_y;
            left.setPower(rightx + lefty);
            right.setPower(-rightx + lefty);
            if (gamepad1.a && !processing) {
                processing = true;
                pressButton(Pathfinding.aStar, Pathfinding.drive);
                processing = false;
            }
        }
    }

    private void pressButton(AStar aStar, SampleTankDrive drive) {
        //javier
    }
}