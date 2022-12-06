package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.graph.Node;
import org.firstinspires.ftc.teamcode.vision.Vision;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;


public class Pathfinding {
    public static AStar aStar;
    static Vision sleeveDetection = new Vision();
    static OpenCvCamera camera;
    static Positions positions;
    static final int MS_PER_INCH = 100;
    static final int MS_PER_45_DEGREES = 200;

    public static void run(Positions positions) throws InterruptedException {

        Pathfinding.positions = positions;
        /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK, cameraMonitorViewId);
        sleeveDetection = new Vision();
        camera.setPipeline(sleeveDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        while (sleeveDetection.getPosition() == null) {
        }*/
        Vision.ParkingPosition park = sleeveDetection.getPosition();
        //drive.setPoseEstimate(positions.start);
        pathfind(positions.start, positions.cones);
        boolean cone = false;
        for (int i = 0; i < 4; i++) { //even number = at junction
            pathfind(cone ? positions.cones : positions.junction, cone ? positions.junction : positions.cones);
            cone = !cone;
            if (cone) {
                Intake.in();
            } else {
                Intake.up();
                Intake.out();
                Intake.down();
            }
            Thread.sleep(500);
        }

        Pose2d parkPos = null;
        int leftOffset;
        if (positions == Positions.LEFT_BLUE || positions == Positions.RIGHT_BLUE) {
            leftOffset = -24;
        } else {
            leftOffset = 24;
        }
        park = Vision.ParkingPosition.CENTER; //todo temporary
        if (park == Vision.ParkingPosition.CENTER) {
            parkPos = positions.junction;
        } else if (park == Vision.ParkingPosition.RIGHT) {
            parkPos = new Pose2d(positions.junction.getX() - leftOffset, positions.junction.getY());
        } else if (park == Vision.ParkingPosition.LEFT) {
            parkPos = new Pose2d(positions.junction.getX() + leftOffset, positions.junction.getY());
        }
        assert parkPos != null;
        pathfind(positions.junction, parkPos);
    }

    private static void setBlocks(AStar aStar) {
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                if (i % 48 == 0 && j % 48 == 0) aStar.setBlock(i, j, 1);
                else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0))
                    aStar.setBlock(i, j, 33.5);
                else if (Math.abs(i) == 24 && Math.abs(j) == 24) aStar.setBlock(i, j, 23.5);
                else aStar.setBlock(i, j, 13.5);
            }
        }
    }

    private static ArrayList<Node> getSimplePath(Node start, Node end) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(new Node(start.x, end.y));
        path.add(new Node(end.x, end.y));
        return path;
    }

    private static void pathfind(Pose2d initialPose, Pose2d finalPose) throws InterruptedException {
        Node initialNode = new Node((int) Math.round(initialPose.getX()), (int) Math.round(initialPose.getY()));
        Node finalNode = new Node((int) Math.round(finalPose.getX()), (int) Math.round(finalPose.getY()));
        ArrayList<Node> path = getSimplePath(initialNode, finalNode);
        path.remove(initialNode);
        ArrayList<Node> toRemove = new ArrayList<>();
        if (path.size() > 1) {
            Node prev = initialNode, ref = initialNode;
            for (Node cur : path) {
                if (!(ref.x == cur.x || ref.y == cur.y)) {
                    ref = cur;
                    toRemove.remove(prev);
                }
                toRemove.add(cur);
                prev = cur;
            }
        }
        path.removeAll(toRemove);
        if (!path.contains(finalNode)) {
            path.add(finalNode);
        }
        if (!path.contains(initialNode)) {
            path.add(0, initialNode);
        }
        Node n = path.get(0), next1 = path.get(1);
        double initialRot = Math.atan2(next1.y - n.y, next1.x - n.x);
        if (Math.abs(initialRot - initialPose.getHeading()) >= 0.01) {
            turn(initialRot - initialPose.getHeading()); //correct starting heading
        }
        Node prev = initialNode;
        double headingEstimate = initialRot;
        for (int i = 1; i < path.size(); i++) {
            Node n1 = path.get(i);
            //math.atan2 gets around tan(90) being undefined
            double rot = Math.atan2(n1.y - prev.y, n1.x - prev.x);
            double turnAmount = rot - headingEstimate;
            if (Math.abs(turnAmount) > Math.PI) {
                turnAmount = (Math.PI - turnAmount) % (Math.PI * 2);
            }
            turn(turnAmount);
            headingEstimate += turnAmount;

            double dist = Math.max(Math.abs(prev.x - n1.x), Math.abs(prev.y - n1.y));
            if (dist > 0.1) {
                forward(dist);
            }
            prev = n1;
        }
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

    private static void turn(double degrees) throws InterruptedException {
        degrees = Math.toDegrees(degrees);
        if (degrees < 0) {
            for (DcMotor motor : Chassis.leftMotors) {
                motor.setPower(0.5);
            }
            for (DcMotor motor : Chassis.rightMotors) {
                motor.setPower(-0.5);
            }
        } else {
            for (DcMotor motor : Chassis.rightMotors) {
                motor.setPower(0.5);
            }
            for (DcMotor motor : Chassis.leftMotors) {
                motor.setPower(-0.5);
            }
        }
        Thread.sleep((long) (MS_PER_45_DEGREES * Math.abs(degrees) / 45));
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
    }
}
