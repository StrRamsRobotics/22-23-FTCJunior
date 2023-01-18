package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;

@TeleOp(name = "Joystick")
public class Joystick extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        SampleTankDrive drive = new SampleTankDrive(hardwareMap);
        CRServoImplEx claw = hardwareMap.get(CRServoImplEx.class, "servo");
        claw.setPwmEnable();
        DcMotor lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();


        while (opModeIsActive()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            Math.abs(gamepad1.left_stick_y) < 0.2 ? 0 : -gamepad1.left_stick_y,
                            0,
                            Math.abs(gamepad1.right_stick_x) < 0.2 ? 0 : -gamepad1.right_stick_x
                    )
            );
            //claw start
            if (gamepad1.left_trigger>0) {
                claw.setPower(1);
            } else {
                claw.setPower(0);
            }
            if (gamepad1.left_bumper) {
                claw.setPower(-1);
            }else {
                claw.setPower(0);
            }
            if (gamepad1.right_trigger>0) {
                lift.setPower(1);
            }else {
                lift.setPower(0);
            }
            if (gamepad1.right_bumper) {
                lift.setPower(-1);
            }else {
                lift.setPower(0);
            }
        }
    }
}