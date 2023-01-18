package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private static CRServoImplEx intake;
    private static DcMotor lift;
    private static final int TICKS_PER_INCH = 20; //don't know
//19521 for expansion cable
    public static void init(HardwareMap map) {
        intake = map.get(CRServoImplEx.class, "servo");
        lift=map.get(DcMotor.class, "lift");
        intake.setPwmEnable();
        //leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void in() throws InterruptedException {
        intake.setPower(-0.5);
        Thread.sleep(500);
        intake.setPower(0);
    }

    public static void up(double inches) {
        lift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.5);
        while (lift.isBusy()) ;
        lift.setPower(0);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void down(double inches) {
        lift.setTargetPosition((int) (TICKS_PER_INCH * inches));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(-0.5);
        while (lift.isBusy()) ;
        lift.setPower(0);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void out() throws InterruptedException {

        intake.setPower(0.5);
        Thread.sleep(500);
        intake.setPower(0);
    }

}
