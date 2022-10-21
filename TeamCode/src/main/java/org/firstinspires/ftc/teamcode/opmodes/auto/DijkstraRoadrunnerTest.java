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
import org.firstinspires.ftc.teamcode.graph.Node;

import java.util.List;

@Autonomous(name = "testgrid")

public class DijkstraRoadrunnerTest extends LinearOpMode {
    private SampleTankDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleTankDrive(hardwareMap);
        waitForStart();


        TelemetryPacket packet = new TelemetryPacket();
        packet.put("a", "done");
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
        while (opModeIsActive()) {

        }
    }


}
