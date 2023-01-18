package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.graph.Node;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;


public class MainAuto {
    public static AStar aStar;
    static Vision sleeveDetection = new Vision();
    static Positions positions;
    static final int MS_PER_INCH = 132;
    static final int MS_PER_45_DEGREES = 400;

    public static void run(Positions positions) throws InterruptedException {

        MainAuto.positions = positions;
        sleeveDetection = new Vision();
        Chassis.camera.setPipeline(sleeveDetection);

        Chassis.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                Chassis.camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        String park;
        while (true) {
            String route = sleeveDetection.route;
            if (!route.equals("OH SHIT!")) {
                park = route;
                break;
            }
        }

        Chassis.camera.closeCameraDeviceAsync(() -> {
        });
        System.out.println("route:" + park);
        //if (park.equals("OH SHIT!")) park="LEFT";
        //drive.setPoseEstimate(positions.start);
/*
        double targetClawHeight = 7.9;
        double junctionHeight = 37;
        double curClawHeight = 3;
        boolean preload = true;
        boolean cone = false;
        pathfind(positions.start, positions.junction);
        Intake.up(junctionHeight - curClawHeight);
        forward(11);
        Intake.down(junctionHeight - curClawHeight);
        Intake.out();
        back(11);
        for (int i = 0; i < 4; i++) { //even number = at junction

            pathfind(cone ? positions.cones : positions.junction, cone ? positions.junction : positions.cones);

            if (cone) {
                if (targetClawHeight - curClawHeight < 0) {
                    Intake.down(Math.abs(targetClawHeight - curClawHeight));
                } else {
                    Intake.up(Math.abs(targetClawHeight - curClawHeight));
                }
                Intake.in();
                if (!preload) {
                    targetClawHeight -= 1.182;
                }
                preload = false;
            } else {
                Intake.up(junctionHeight - curClawHeight);
                forward(11);
                Intake.down(junctionHeight - curClawHeight);
                Intake.out();
                back(11);
            }
            cone = !cone;
            Thread.sleep(500);
        }*/
        Pose2d parkPos = null;
        int leftOffset;
        if (positions == Positions.LEFT_BLUE || positions == Positions.RIGHT_BLUE) {
            leftOffset = 24;
        } else {
            leftOffset = -24;
        }
        if (park.equals("CENTER")) { //park.equals("CENTER")
            back(24);
            parkPos = positions.junction;
        } else if (park.equals("RIGHT")) { //park.equals("RIGHT")
            back(24);
            turn(Math.PI / 2);
            forward(24);
            parkPos = new Pose2d(positions.junction.getX() - leftOffset, positions.junction.getY());
        } else if (park.equals("LEFT")) { //park.equals("LEFT")
            back(24);
            turn(-Math.PI / 2);
            forward(24);
            parkPos = new Pose2d(positions.junction.getX() + leftOffset, positions.junction.getY());
        }
        // assert parkPos != null;
        // pathfind(positions.junction, parkPos); //actually positions.junction if doing main auto
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
        double endRot = finalPose.getHeading();
        double turnAmount = endRot - headingEstimate;
        if (Math.abs(turnAmount) < 0.1) {
            return;
        }
        if (Math.abs(turnAmount) > Math.PI) {
            turnAmount = (Math.PI - turnAmount) % (Math.PI * 2);
        }
        turn(turnAmount);
    }

    private static void forward(double inches) throws InterruptedException {
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0.5);
        }
        Thread.sleep((long) (MS_PER_INCH * inches));
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
        Thread.sleep(500);
    }

    private static void back(double inches) throws InterruptedException {
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(-0.5);
        }
        Thread.sleep((long) (MS_PER_INCH * inches));
        for (DcMotor motor : Chassis.motors) {
            motor.setPower(0);
        }
        Thread.sleep(500);
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
        Thread.sleep(500);
    }
}
