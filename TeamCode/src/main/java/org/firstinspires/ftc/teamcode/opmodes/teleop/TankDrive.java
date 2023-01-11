package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "joystick tank")
public class TankDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");
        DcMotor left2 = hardwareMap.get(DcMotor.class, "left2");
        DcMotor right2 = hardwareMap.get(DcMotor.class, "right2");
        //CRServoImplEx claw = hardwareMap.get(CRServoImplEx.class, "servo");
        //claw.setPwmEnable();
        //DcMotor lift = hardwareMap.get(DcMotor.class, "lift");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();


        while (opModeIsActive()) {
            float righty = -gamepad1.right_stick_y, lefty = -gamepad1.left_stick_y;
            if (Math.abs(righty) < 0.2) righty = 0;
            if (Math.abs(lefty) < 0.2) lefty = 0;
            left.setPower(lefty);
            left2.setPower(lefty);
            right.setPower(righty);
            right2.setPower(righty);
            telemetry.addData(String.valueOf(lefty), righty);
            telemetry.update();
            //claw start
//            if (gamepad1.dpad_left) {
//                claw.setPower(1);
//            }
//            if (gamepad1.dpad_right) {
//                claw.setPower(-1);
//            }
//            if (gamepad1.dpad_up) {
//                lift.setPower(1);
//            }
//            if (gamepad1.dpad_down) {
//                lift.setPower(-1);
//            }
        }
    }
}