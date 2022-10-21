package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleTankDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name = "RoadrunnerTest")
public class RoadrunnerTest extends LinearOpMode {
    private SampleTankDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleTankDrive(hardwareMap);
        waitForStart();
        Trajectory test = drive.trajectoryBuilder(new Pose2d())
                .forward(5) //5 inches forward
                .addDisplacementMarker(2.5, () -> {
                    telemetry.addData("distance", "just hti 2.5 inches");
                    telemetry.update();
                })
                .build();
      /*  TrajectorySequence sequence = DijkstraRoadrunnerTest.drive.trajectorySequenceBuilder(test.end())
                .back(5)
                .strafeLeft(5) //not sure if tank drives can strafe
                .splineTo(new Vector2d(-40, 40), Math.toRadians(90)) //should work even without mecanum
                .build();*/
        drive.followTrajectory(test);
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("a", "done");
        FtcDashboard.getInstance().sendTelemetryPacket(packet);
       // DijkstraRoadrunnerTest.drive.followTrajectorySequence(sequence);
        while (opModeIsActive()) {

        }
    }
}
