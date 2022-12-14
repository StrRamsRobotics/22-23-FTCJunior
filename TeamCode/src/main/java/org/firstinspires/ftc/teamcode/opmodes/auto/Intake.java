package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private static CRServoImplEx intake;
    private static DcMotor leftLift;
    private static DcMotor rightLift;
    private static final int TICKS_PER_INCH = 20; //don't know

    public static void init(HardwareMap map) {
        intake = map.get(CRServoImplEx.class, "intake");
        leftLift = map.get(DcMotor.class, "leftLift");
        rightLift = map.get(DcMotor.class, "rightLift");
        intake.setPwmEnable();
        //leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void in() throws InterruptedException {
        intake.setPower(-1);
        Thread.sleep(500);
        intake.setPower(0);
    }

    public static void up(double inches) {
        leftLift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        rightLift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftLift.setPower(1);
        rightLift.setPower(1);
        while (leftLift.isBusy() && rightLift.isBusy()) ;
        leftLift.setPower(0);
        rightLift.setPower(0);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void down(double inches) {
        leftLift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        rightLift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftLift.setPower(-1);
        rightLift.setPower(-1);
        while (leftLift.isBusy() && rightLift.isBusy()) ;
        leftLift.setPower(0);
        rightLift.setPower(0);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void out() throws InterruptedException {

        intake.setPower(1);
        Thread.sleep(500);
        intake.setPower(0);
    }

}