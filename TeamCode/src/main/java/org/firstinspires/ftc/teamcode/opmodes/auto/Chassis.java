package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chassis {
    public static List<DcMotor> leftMotors, rightMotors, motors;
    public static void init(HardwareMap map) {
        DcMotor left1 = map.get(DcMotor.class, "left"), left2 = map.get(DcMotor.class, "left2"), right1 = map.get(DcMotor.class, "right"), right2 = map.get(DcMotor.class, "right2");
        leftMotors = Arrays.asList(left1, left2);
        rightMotors = Arrays.asList(right1, right2);
        motors = Arrays.asList(left1, left2, right1, right2);
        for (DcMotor motor : leftMotors) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
}
