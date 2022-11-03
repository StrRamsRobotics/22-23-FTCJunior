package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.graph.AStar;
import org.firstinspires.ftc.teamcode.graph.Node;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

import java.util.ArrayList;
import java.util.List;


public class DijkstraRoadrunnerTest {
    public static AStar aStar;
    public static SampleTankDrive drive;
    public static void run(SampleTankDrive drive,Telemetry telemetry){

        DijkstraRoadrunnerTest.drive = drive;
        telemetry.addData("run","");
        telemetry.update();
        /*TelemetryPacket packet = new TelemetryPacket();
        packet.put("a", "start");
        FtcDashboard.getInstance().sendTelemetryPacket(packet);*/
        TrajectorySequenceBuilder builder = drive.trajectorySequenceBuilder(drive.getPoseEstimate()); //path won't gen at 0, 0 bc 0,0 is a ground junction
        drive.setPoseEstimate(new Pose2d(-60,-60));
        Node initialNode = new Node(-60,-60); //startleftblue
        Node finalNode = new Node(36, -12);
        AStar aStar = new AStar(64, 64, initialNode, finalNode);
        for (int i = -48; i <= 48; i += 24) {
            for (int j = -48; j <= 48; j += 24) {
                telemetry.addData("" + i, "" + j);
                telemetry.update();
                if (i % 48 == 0 && j % 48 == 0) {
                    aStar.setBlock(i, j, 1);
                    //System.out.format("%d %d is a ground junction\n", i, j);
                } else if ((Math.abs(i) == 24 || Math.abs(j) == 24) && (i == 0 || j == 0)) {
                    // System.out.format("%d %d is a high junction\n", i, j);
                    aStar.setBlock(i, j, 33.5);
                } else if (Math.abs(i) == 24 && Math.abs(j) == 24) {
                    // System.out.format("%d %d is a medium junction\n", i, j);
                    aStar.setBlock(i, j, 23.5);
                } else {
                    // System.out.format("%d %d is a low junction\n", i, j);
                    aStar.setBlock(i, j, 13.5);
                }
            }
        }
        ArrayList<Node> path = aStar.findPath();
        path.remove(initialNode);
        Node prev=new Node(0,0);
        for (Node n : path) {
            rotate(n.x, n.y,builder);
            builder.forward(Math.abs(prev.x-n.x)+Math.abs(prev.y-n.y));
            prev=n;
        }
        telemetry.addData(String.valueOf(path.size()),"");
        telemetry.update();
        drive.followTrajectorySequence(builder.build());
        while (drive.isBusy());
        telemetry.addData("done","");
        telemetry.update();

    }
    private static void rotate(double junctionX, double junctionY,TrajectorySequenceBuilder builder) {
        double x = drive.getPoseEstimate().getX();
        double y = drive.getPoseEstimate().getY();
        double rot = drive.getPoseEstimate().getHeading();
        builder.turn(Math.atan((junctionX - x) / (junctionY - y)) - rot);
    }
}
