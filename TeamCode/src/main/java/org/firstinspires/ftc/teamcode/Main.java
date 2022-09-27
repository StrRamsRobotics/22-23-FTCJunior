package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "potapwod")
public class Main extends LinearOpMode {

    private static final int TICKS_PER_ROT = 1440;

    public void spinTicks(DcMotor motor, int ticks, double power) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        while (opModeIsActive() && motor.isBusy()) {
            telemetry.addData("Motor Data", motor.getCurrentPosition());
            telemetry.update();
        }
        motor.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor motor = hardwareMap.get(DcMotor.class, "m");
        // Spin the DcMotor for one rotation
        this.spinTicks(motor, TICKS_PER_ROT, 0.25);
    }
}
