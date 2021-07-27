package org.firstinspires.ftc.teamcode.Samples.OpMode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Core.Robots.BelindaControl;
import org.firstinspires.ftc.teamcode.Navigation.OrionNavigator;

@Config
@Autonomous(name = "ExampleAutonomous")
@Disabled
public class SampleAutonomous extends LinearOpMode
{
    private BelindaControl control;
    private OrionNavigator orion;
    private FtcDashboard dashboard;

    //Set these variables to the start pose of the robot
    public static double robotX = 0;
    public static double robotY = 0;
    public static double robotH = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        control = new BelindaControl(this,true,false,true);
        control.Init();
        orion = control.GetOrion();
        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);

        waitForStart();
        orion.SetPose(robotX, robotY, robotH); //robot start pos


        orion.MoveLinear(10,10,0); //moves robot along a line
        orion.MoveSpline(10,10,0, false); //moves robot along a spline path

        sleep(500);//wait for tensorflow to detect discs
        int numberOfDiscs = orion.GetNumberOfDiscs(10000);//figure out where to go

        telemetry.addLine("# of discs" + numberOfDiscs); //prints number of discs to telemetry
        telemetry.update();
    }
}
