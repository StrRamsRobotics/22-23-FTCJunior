package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "spintest")
public class SpinTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");
        DcMotor left2 = hardwareMap.get(DcMotor.class, "left2");
        DcMotor right2 = hardwareMap.get(DcMotor.class, "right2");
       /* CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class,"servo");
        servo.setPwmEnable();*/
        waitForStart();
       right.setDirection(DcMotorSimple.Direction.REVERSE);
        right2.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setPower(0.5);
        right.setPower(0.5);
        left2.setPower(0.5);
        right2.setPower(0.5);
        while (opModeIsActive()) {

        }
    }
}
