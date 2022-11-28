package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

@Autonomous(name = "servo")
public class ServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
         CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class,"servo");
         waitForStart();
        servo.setPwmEnable();
        servo.setPower(0.5);
        while (opModeIsActive()) {

        }
    }
}
