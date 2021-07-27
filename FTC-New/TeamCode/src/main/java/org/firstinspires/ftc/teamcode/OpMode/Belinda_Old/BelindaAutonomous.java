package org.firstinspires.ftc.teamcode.OpMode.Belinda_Old;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Core.Robots.BelindaControl;

//A simple autonomous script for the demobot

//REQUIRED TO RUN: Phones | REV Hub | Demobot Chassis | Shooter | Odometry Unit

@Config
@Autonomous(name = "Demobot Autonomous")
public class BelindaAutonomous extends LinearOpMode {
    BelindaControl control;

    //Pid coefficients
    public static double p;
    public static double i;
    public static double d;


    @Override
    public void runOpMode() throws InterruptedException {
        //Init Control
        control = new BelindaControl(this, true, false, true);
        control.Init();
        control.Start();

        waitForStart();

        //TODO: Set this to the start position of the robot
        control.GetOrion().SetPose(0,0,0);

        if (isStopRequested()) return;

        while (true){
            /*Control.SetDrivePID(p,i,d);
            Control.RawDrive(0,0,0);*/
            //control.FireShooter();
        }
    }
}
