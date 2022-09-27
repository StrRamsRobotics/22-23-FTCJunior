package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Joystick")
public class Joystick extends LinearOpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;
    Gamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        frontRight = hardwareMap.get(DcMotor.class, "frontright");
        backLeft = hardwareMap.get(DcMotor.class, "frontleft");
        backRight = hardwareMap.get(DcMotor.class, "frontleft");
        gamepad = hardwareMap.get(Gamepad.class, "gamepad");

        waitForStart();

        while (opModeIsActive()) {
            float leftx = gamepad.left_stick_x, lefty = -gamepad.left_stick_y;
            frontLeft.setPower(lefty + leftx);
            frontRight.setPower(lefty - leftx);
            backLeft.setPower(lefty - leftx);
            backRight.setPower(lefty + leftx);
        }
    }


  /*  private void moveForward(int power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private void moveRight(int power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }

    private void moveLeft(int power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    private void moveDiagonallyForwardRight(int power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }

    private void spin(int power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }*/
}