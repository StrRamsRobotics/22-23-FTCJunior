package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera2;

import java.util.Arrays;
import java.util.List;

public class Chassis {
    public static List<DcMotor> leftMotors, rightMotors, motors;
    public static OpenCvCamera camera;

    public static void init(HardwareMap map) {
        if (true) { //true = 4wd
            DcMotor left1 = map.get(DcMotor.class, "left"), left2 = map.get(DcMotor.class, "left2"), right1 = map.get(DcMotor.class, "right"), right2 = map.get(DcMotor.class, "right2");
            leftMotors = Arrays.asList(left1, left2);
            rightMotors = Arrays.asList(right1, right2);
            motors = Arrays.asList(left1, left2, right1, right2);
            for (DcMotor motor : rightMotors) {
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            for (DcMotor motor : motors) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            int cameraMonitorViewId = map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", map.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(map.get(WebcamName.class, "camera"), cameraMonitorViewId);
        }
        else {
            DcMotor left1 = map.get(DcMotor.class, "left"), right1 = map.get(DcMotor.class, "right");
            leftMotors = Arrays.asList(left1);
            rightMotors = Arrays.asList(right1);
            motors = Arrays.asList(left1, right1);
            for (DcMotor motor : rightMotors) {
                motor.setDirection(DcMotorSimple.Direction.REVERSE);
            }
            for (DcMotor motor : motors) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            int cameraMonitorViewId = map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", map.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(map.get(WebcamName.class, "camera"), cameraMonitorViewId);
        }
    }
}
