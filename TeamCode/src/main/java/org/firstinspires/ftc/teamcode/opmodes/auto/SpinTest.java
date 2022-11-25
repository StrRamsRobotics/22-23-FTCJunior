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
       /* DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");*/
        CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class,"servo");
        servo.setPwmEnable();
        waitForStart();
       // left.setDirection(DcMotorSimple.Direction.REVERSE);
       /* left.setPower(0.5);
        right.setPower(0.5);*/
//        while (opModeIsActive()) {

            servo.setPower(1);
            sleep(1000);
            servo.setPower(0);
//        }
    }
}
