package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.BFS;
import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Constants;
import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Conversions;
import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Path;
import org.firstinspires.ftc.teamcode.opmodes.auto.pathfind.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Config
public class MainAuto {
    public static int MS_PER_INCH = 77;
    public static double curClawHeight = 3;
    public static double targetClawHeight = 7.9;
    private static boolean preload = Constants.PRELOAD;
    private static boolean cone = !Constants.PRELOAD;
    private static Telemetry telemetry;

    public static void runPaths(ArrayList<Path> paths) {
//        double prevAngle = 0.0;
//        for (int i = 0; i < paths.size(); i++) {
//            Path pa = paths.get(i);
//            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
//            BFS bfs = new BFS();
//            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
//            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
//            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
//            if (tsb == null) {
//                tsb = drive.trajectorySequenceBuilder(new Pose2d(-Conversions.getRealPos(start.y), -Conversions.getRealPos(start.x), -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x)));
//            }
//            if (i > 0) {
//                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
//                if (Math.abs(angle) > Math.toRadians(180)) {
//                    angle = Math.toRadians(360) - Math.abs(angle);
//                }
//                tsb = tsb.turn(prevAngle + angle);
//                prevAngle = angle;
//            }
//            for (int j = 0; j < path.length; j++) {
//                Point p = path[j];
//                Point d = p;
//                if (j > 0) {
//                    if (j < path.length - 1) {
//                        d = path[j + 1];
////                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
//                        tsb = tsb.splineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)), -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x));
//                        prevAngle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
//                    } else {
//                        tsb = tsb.splineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)), prevAngle);
//                    }
//                }
//            }
//        }
    }

    public static void runManualPaths(ArrayList<Path> paths) {
//        double prevAngle = 0.0;
//        for (int i = 0; i < paths.size(); i++) {
//            Path pa = paths.get(i);
//            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
//            BFS bfs = new BFS();
//            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
//            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
//            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
//            if (tsb == null) {
//                prevAngle = -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x);
//                tsb = drive.trajectorySequenceBuilder(new Pose2d(-Conversions.getRealPos(start.y), -Conversions.getRealPos(start.x), prevAngle));
//            } // place down the robot facing this way
//            if (i > 0) {
//                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
//                if (Math.abs(angle) > Math.toRadians(180)) {
//                    angle = Math.toRadians(360) - Math.abs(angle);
//                }
//                tsb = tsb.turn(prevAngle + angle);
//                prevAngle = angle;
//            }
//            for (int j = 0; j < path.length; j++) {
//                Point p = path[j];
//                Point d = p;
//                if (j > 0) {
//                    if (j < path.length - 1) {
//                        d = path[j + 1];
////                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
//                        double angle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
//                        double newAngle = angle - prevAngle;
//                        if (Math.abs(angle) > Math.toRadians(180)) {
//                            angle = Math.toRadians(360) - Math.abs(angle);
//                        }
//                        if (Math.abs(newAngle) > Math.toRadians(180)) {
//                            newAngle = Math.toRadians(360) - Math.abs(newAngle);
//                        }
//                        tsb = tsb.lineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)));
//                        tsb = tsb.turn(newAngle);
//                        prevAngle = angle;
//                    } else {
//                        tsb = tsb.lineTo(new Vector2d((double) -Conversions.getRealPos(p.y), (double) -Conversions.getRealPos(p.x)));
//                    }
//                }
//            }
//        }
    }
    public static void runLiterallyManualPaths(ArrayList<Path> paths) throws InterruptedException {
        double prevAngle = 0.0;
        for (int i = 0; i < paths.size(); i++) {
            telemetry.addData("pathfinding", i); telemetry.update();
            Path pa = paths.get(i);
            int[] points = Constants.SUBDIVIDED_MODE[pa.idx];
            BFS bfs = new BFS();
            Point start = pa.backwards ? new Point(points[3], points[2]) : new Point(points[1], points[0]);
            Point end = pa.backwards ? new Point(points[1], points[0]) : new Point(points[3], points[2]);
            Point[] path = bfs.findPath(Constants.SUBDIVIDED_FIELD, start, end);
            if (i == 0) {
                prevAngle = -Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x);
            } // place down the robot facing this way
            if (i > 0) {
                double angle = (-Math.toRadians(90) - Conversions.getAngle(start.y, path[1].y, start.x, path[1].x));
                if (Math.abs(angle) > Math.toRadians(180)) {
                    angle = Math.toRadians(360) - Math.abs(angle);
                }
                double turnAmount = Conversions.toDegrees(prevAngle + angle);
                if (Math.abs(turnAmount)>0.1) {
                    turn(turnAmount);
                }
                prevAngle = angle;
            }
            for (int j = 0; j < path.length; j++) {
                telemetry.addData("following path segment", i);
                Point p = path[j];
                Point d = p;
                if (j > 0) {
                    if (j < path.length - 1) {
                        d = path[j + 1];
//                    System.out.println(-Math.toRadians(90) - Conversions.getAngle(p.x, d.x, p.y, d.y));
                        double angle = -Math.toRadians(90) - Conversions.getAngle(p.y, d.y, p.x, d.x);
                        double newAngle = angle - prevAngle;
                        if (Math.abs(angle) > Math.toRadians(180)) {
                            angle = Math.toRadians(360) - Math.abs(angle);
                        }
                        if (Math.abs(newAngle) > Math.toRadians(180)) {
                            newAngle = Math.toRadians(360) - Math.abs(newAngle);
                        }
                        telemetry.addData("forward", 12); telemetry.update();
                        forward(12);
                        telemetry.addData("turn", Conversions.toDegrees(newAngle)); telemetry.update();
                        turn(Conversions.toDegrees(newAngle));
                        prevAngle = angle;
                    } else {
                           telemetry.addData("forward", 12); telemetry.update();
                        forward(12);
                        if (pa.cone) {
                            //runArmClaw();
                        }
                    }
                }
            }
        }
    }

    public static void run(double[][] chosen) throws InterruptedException {
        telemetry=FtcDashboard.getInstance().getTelemetry();
        try { //ftcdashboard doesn't report errors
            Conversions.subdivide(chosen);
            Vision sleeveDetection = new Vision();

        /*Chassis.camera.setPipeline(sleeveDetection);
        Chassis.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                Chassis.camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        while (sleeveDetection.route == -1) {
        }
        Chassis.camera.closeCameraDeviceAsync(() -> {
        });*/
            sleeveDetection.route = 2;
            ArrayList<Path> paths = new ArrayList<>();
            paths.add(new Path(3, false, false));
            for (int i = 0; i < Constants.TIMES_CONES + (Constants.PRELOAD ? 1 : 0); i++) {
                paths.add(new Path(4, false, true));
                paths.add(new Path(4, true, true));
            }
            paths.add(new Path(sleeveDetection.route, false, false));
//        runPaths(paths);
//        runManualPaths(paths);
            telemetry.addData("made path", "a");
            telemetry.update();
            //turn(180); //camera (currently turning is inaccuate and it leads to pole collision)
            runLiterallyManualPaths(paths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runArmClaw() throws InterruptedException {
        if (cone) {
            if (targetClawHeight - curClawHeight < 0) {
                Intake.down(Math.abs(targetClawHeight - curClawHeight));
            } else {
                Intake.up(Math.abs(targetClawHeight - curClawHeight));
            }
            Intake.in();
            if (preload) {
                targetClawHeight -= 1.182;
            }
            preload = false;
        } else {
            Intake.up(Constants.JUNCTION_HEIGHT - curClawHeight);
            turn(45);
            forward(11);
            Intake.down(Constants.JUNCTION_HEIGHT - curClawHeight);
            Intake.out();
            back(11);
            turn(-45);
        }
        cone = !cone;
        Thread.sleep(500);
    }

    private static void forward(double inches) throws InterruptedException {
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0.5);
        }
        Thread.sleep((long) (MS_PER_INCH * inches));
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
    }

    private static void back(double inches) throws InterruptedException {
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(-0.5);
        }
        Thread.sleep((long) (MS_PER_INCH * inches));
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
    }

    private static void turn(double degrees) throws InterruptedException {
        double curRot=Chassis.imu.getAngularOrientation().firstAngle, target = curRot+degrees;
        if (degrees < 0) {
            for (DcMotor motor : Chassis.leftMotors) {
                motor.setPower(-0.5);
            }
            for (DcMotor motor : Chassis.rightMotors) {
                motor.setPower(0.5);
            }
        } else {
            for (DcMotor motor : Chassis.rightMotors) {
                motor.setPower(-0.5);
            }
            for (DcMotor motor : Chassis.leftMotors) {
                motor.setPower(0.5);
            }
        }
        while (Math.abs(Chassis.imu.getAngularOrientation().firstAngle-target)>2) {}
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
    }
}