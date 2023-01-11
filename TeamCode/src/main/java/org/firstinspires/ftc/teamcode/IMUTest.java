package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="imutest")
public class IMUTest extends LinearOpMode {

    private BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
        waitForStart();

        while (opModeIsActive()) {
            FtcDashboard.getInstance().getTelemetry().addData("a", imu.getAngularOrientation().firstAngle);
            FtcDashboard.getInstance().getTelemetry().update();
        }

    }
}
