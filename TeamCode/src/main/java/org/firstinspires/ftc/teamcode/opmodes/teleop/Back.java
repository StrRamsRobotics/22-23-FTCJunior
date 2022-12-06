package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "back")
public class Back extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");
        DcMotor left2 = hardwareMap.get(DcMotor.class, "left2");
        DcMotor right2 = hardwareMap.get(DcMotor.class, "right2");
        waitForStart();
        Direction.run(-0.5, -0.5, -0.5, -0.5, left, left2, right, right2);
        while (opModeIsActive()) {

        }
    }
}
