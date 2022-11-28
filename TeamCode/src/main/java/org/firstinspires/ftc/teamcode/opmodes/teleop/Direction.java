package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Direction {
    public static void run(double l1, double l2, double r1, double r2, DcMotor left, DcMotor left2, DcMotor right, DcMotor right2) throws InterruptedException {
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        left2.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setPower(l1);
        right.setPower(r1);
        left2.setPower(l2);
        right2.setPower(r2);
        /*Thread.sleep(1000);
        left.setPower(0);
        right.setPower(0);
        left2.setPower(0);
        right2.setPower(0);*/
    }
}
