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
        /*CRServoImplEx claw = hardwareMap.get(CRServoImplEx.class, "servo");
        claw.setPwmEnable();*/
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        left2.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();


        while (opModeIsActive()) {
            float righty = -gamepad1.right_stick_y, lefty = -gamepad1.left_stick_y;
            if (Math.abs(righty) < 0.1) righty = 0;
            if (Math.abs(lefty) < 0.1) lefty = 0;
            left.setPower(lefty);
            left2.setPower(lefty);
            right.setPower(righty);
            right2.setPower(righty);
            telemetry.addData(String.valueOf(lefty), righty);
            telemetry.update();
            //claw start

            /*float leftTrigger = gamepad1.left_trigger, rightTrigger = gamepad1.right_trigger;
            if (Math.abs(leftTrigger)<0.1) leftTrigger=0;
            if (Math.abs(rightTrigger)<0.1) rightTrigger=0;
            if (leftTrigger>rightTrigger) {
                claw.setPower(leftTrigger);
            } else {
                claw.setPower(-rightTrigger);
            }*/
        }
    }
}
