package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.graph.Node;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.vision.Vision;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera2;

import java.util.ArrayList;


public class Pathfinding {
    public static AStar aStar;
    public static SampleTankDrive drive;
    static Vision sleeveDetection = new Vision();
    static OpenCvCamera camera;
    static Positions positions;
    final int MS_PER_INCH=100;
    final int MS_PER_45_DEGREES=200;

    public static void run(SampleTankDrive drive, HardwareMap hardwareMap, Positions positions) throws InterruptedException {
        Intake.init(hardwareMap);
        Pathfinding.drive = drive;

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
        drive.setPoseEstimate(positions.start);
        TrajectorySequence prev = pathfind(positions.start, positions.cones).build();
        drive.followTrajectorySequence(prev);
        boolean cone = false;
        for (int i = 0; i < 5; i++) {
            Pose2d target = cone ? positions.junction : positions.cones;
            TrajectorySequence next = pathfind(prev.end(), target).build();
            drive.followTrajectorySequence(next);
            cone = !cone;
            if (cone) {
                Intake.in();
            } else {
                Intake.up();
                Intake.out();
                Intake.down();
            }
            prev = next;
            Thread.sleep(500);
        }

        Pose2d parkPos = null;
        int leftOffset;
        if (positions == Positions.LEFT_BLUE || positions == Positions.RIGHT_BLUE) {
            leftOffset = -24;
        } else {
            leftOffset = 24;
        }
        park= Vision.ParkingPosition.CENTER; //todo temporary
        if (park == Vision.ParkingPosition.CENTER) {
            parkPos = positions.junction;
        } else if (park == Vision.ParkingPosition.RIGHT) {
            parkPos = new Pose2d(positions.junction.getX() - leftOffset, positions.junction.getY());
        } else if (park == Vision.ParkingPosition.LEFT) {
            parkPos = new Pose2d(positions.junction.getX() + leftOffset, positions.junction.getY());
        }
        assert parkPos != null;
        drive.followTrajectorySequence(pathfind(drive.getPoseEstimate(), parkPos).build());
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

    private static TrajectorySequenceBuilder pathfind(Pose2d initialPose, Pose2d finalPose) {
        TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(initialPose);
        Node initialNode = new Node((int) Math.round(initialPose.getX()), (int) Math.round(initialPose.getY()));
        Node finalNode = new Node((int) Math.round(finalPose.getX()), (int) Math.round(finalPose.getY()));
        AStar aStar = new AStar(64, 64, initialNode, finalNode);
        setBlocks(aStar);
        ArrayList<Node> path = aStar.findPath();
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
            Node prev = initialNode;
            double headingEstimate = drive.getPoseEstimate().getHeading();
            for (Node n1 : path) {
                //math.atan2 gets around tan(90) being undefined
                double rot = Math.atan2(n1.y - prev.y, n1.x - prev.x);
                builder.turn(rot - headingEstimate);
                headingEstimate += rot - headingEstimate;
                //builder.splineTo(new Vector2d(n.x, n.y),headingEstimate);
                builder.forward(Math.max(Math.abs(prev.x - n1.x), Math.abs(prev.y - n1.y)));
                prev = n1;
            }
        return builder;
    }
    private void forward(int inches) {
        for (DcMotor motor : drive.motors) {
            motor.setPower(0.5);
        }
        Thread.sleep(MS_PER_INCH*inches);
        for (DcMotor motor : drive.motors) {
            motor.setPower(0.5);
        }
    }
}
