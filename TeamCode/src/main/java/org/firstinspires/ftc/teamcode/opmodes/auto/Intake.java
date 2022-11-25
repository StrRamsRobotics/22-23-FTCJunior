package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private static CRServoImplEx intake;
    private static DcMotor leftLift;
    private static DcMotor rightLift;
    private static final int TICKS_PER_ROT = 1440;

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
        intake.setPower(-0.5);
        Thread.sleep(500);
        intake.setPower(0);
    }

    public static void up() {
        leftLift.setTargetPosition(TICKS_PER_ROT * 2);
        rightLift.setTargetPosition(TICKS_PER_ROT * 2);
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftLift.setPower(0.5);
        rightLift.setPower(0.5);
        while (leftLift.isBusy()&& rightLift.isBusy());
        leftLift.setPower(0);
        rightLift.setPower(0);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void down() {
        leftLift.setTargetPosition(TICKS_PER_ROT * 2);
        rightLift.setTargetPosition(TICKS_PER_ROT * 2);
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftLift.setPower(-0.5);
        rightLift.setPower(-0.5);
        while (leftLift.isBusy()&& rightLift.isBusy());
        leftLift.setPower(0);
        rightLift.setPower(0);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void out() throws InterruptedException {

        intake.setPower(0.5);
        Thread.sleep(500);
        intake.setPower(0);
    }

}
