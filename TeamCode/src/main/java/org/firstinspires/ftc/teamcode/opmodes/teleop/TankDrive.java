package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.opmodes.auto.Chassis;
import org.firstinspires.ftc.teamcode.opmodes.auto.Intake;

@TeleOp(name = "joystick tank")
public class TankDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Chassis.init(hardwareMap);
       /* DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");
        DcMotor left2 = hardwareMap.get(DcMotor.class, "left2");
        DcMotor right2 = hardwareMap.get(DcMotor.class, "right2");*/
        CRServoImplEx claw = hardwareMap.get(CRServoImplEx.class, "servo");
        claw.setPwmEnable();
        DcMotor lift = hardwareMap.get(DcMotor.class, "lift");
      /*  right.setDirection(DcMotorSimple.Direction.REVERSE);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);*/
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        boolean leftTriggerOn = false;
        boolean rightTriggerOn = false;
        boolean leftBumperOn = false;
        boolean rightBumperOn = false;
        long leftTriggerTime = 0L;
        long rightTriggerTime = 0L;
        long leftBumperTime = 0L;
        long rightBumperTime = 0L;
        long TIME_CONSTANT = 100L;
        while (opModeIsActive()) {
            float righty = -gamepad1.right_stick_y, lefty = -gamepad1.left_stick_y;
            if (Math.abs(righty) < 0.2) righty = 0;
            if (Math.abs(lefty) < 0.2) lefty = 0;
           for (DcMotor m : Chassis.leftMotors) {
               m.setPower(lefty);
           }
           for (DcMotor m : Chassis.rightMotors) {
               m.setPower(righty);
           }
            telemetry.addData(String.valueOf(lefty), righty);
            telemetry.update();

            if (gamepad1.left_trigger>0) {
                leftTriggerOn = true;
                leftTriggerTime = System.currentTimeMillis();
            }
            if (leftTriggerOn && System.currentTimeMillis() - leftTriggerTime >= TIME_CONSTANT) {
                leftTriggerOn = false;
                claw.setPower(0);
            }
            else if (leftTriggerOn) {
                claw.setPower(-1);
            }
            if (gamepad1.left_bumper) {
                leftBumperOn = true;
                leftBumperTime = System.currentTimeMillis();
            }
            if (leftBumperOn && System.currentTimeMillis() - leftBumperTime >= TIME_CONSTANT) {
                leftBumperOn = false;
                claw.setPower(0);
            }
            else if (leftBumperOn) {
                claw.setPower(1);
            }
            if (gamepad1.right_trigger>0 && lift.getCurrentPosition()>-100000) {
                rightTriggerOn = true;
                rightTriggerTime = System.currentTimeMillis();
            }
            if ((rightTriggerOn && System.currentTimeMillis() - rightTriggerTime >= TIME_CONSTANT) || lift.getCurrentPosition()<=-100000) {
                rightTriggerOn = false;
                lift.setPower(0);
            }
            else if (rightTriggerOn) {
                lift.setPower(0.65);
            }
            if (gamepad1.right_bumper) {
                rightBumperOn = true;
                rightBumperTime = System.currentTimeMillis();
            }
            if (rightBumperOn && System.currentTimeMillis() - rightBumperTime >= TIME_CONSTANT) {
                rightBumperOn = false;
                lift.setPower(0);
            }
            else if (rightBumperOn) {
                lift.setPower(-0.65);
            }
            telemetry.addData("a",lift.getCurrentPosition());
        }
    }
}
