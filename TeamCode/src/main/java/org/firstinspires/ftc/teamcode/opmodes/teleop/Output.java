package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

@TeleOp(name = "output")
public class Output extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class, "servo");
        servo.setPwmEnable();
        waitForStart();
        servo.setPower(-0.5);
        Thread.sleep(500);
        servo.setPower(0);
    }
}
