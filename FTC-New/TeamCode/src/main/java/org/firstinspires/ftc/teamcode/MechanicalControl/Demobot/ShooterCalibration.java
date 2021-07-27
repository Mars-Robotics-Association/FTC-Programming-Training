package org.firstinspires.ftc.teamcode.MechanicalControl.Demobot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoController;

//A script for calibrating the shooter. Basic process is:
//  - Enter horizontal distance of shooter from wall
//  - Run script and note the three distances the ball lands at in meters
//  - Enter three lengths in d1, d2, d3
//  - Run script again and note the "vAverage" variable in telemetry. This will be entered in when you create a shooter object
//Basically this works by calculating the velocity at which the shooter shoots at. You may want to run it a few times to determine consistency and get an average
//
//REQUIRED TO RUN: Phones | REV Hub | Shooter

@Autonomous(name = "ShooterCalibration")
public class ShooterCalibration extends LinearOpMode
{
    ////DEPENDENCIES////
    DemobotShooter demobotShooter;
    DcMotor spinner;
    DcMotor loader;
    ServoController aimer;

    ////VARIABLES////
    double heightOffset = 0; //set this to the negative of the distance between shooter and ground
    //Distance vars- change these after running and recording once
    double d1 = 0;
    double d2 = 0;
    double d3 = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        //init motors/servos
        spinner = hardwareMap.dcMotor.get("spinner");
        loader = hardwareMap.dcMotor.get("loader");
        aimer = hardwareMap.crservo.get("aimer").getController();

        //init shooter
        demobotShooter = new DemobotShooter();
        demobotShooter.Init(spinner, loader, aimer, 20);

        waitForStart();

        //shoot three times
        shoot(15);
        shoot(30);
        shoot(45);

        //Calculate velocity of the shots and take an average
        double v1 = solveForSpeed(d1, heightOffset, 15);
        double v2 = solveForSpeed(d2, heightOffset, 30);
        double v3 = solveForSpeed(d3, heightOffset, 45);
        double vAverage = (v1+v2+v3)/3;

        //Returns telemetry
        telemetry.addData("v1: ", v1);
        telemetry.addData("v2: ", v2);
        telemetry.addData("v3: ", v3);
        telemetry.addData("vAverage: ", vAverage);
        telemetry.update();

        sleep(10000);//wait ten seconds
        stop();
    }

    public void shoot(double angle){ //TODO: use a button on robot instead of timers
        //shoots at an angle
        demobotShooter.RawAim(angle);
        demobotShooter.SpinUp();
        sleep(2000);//wait 2 seconds
        demobotShooter.Fire();
        sleep(10000);//wait ten seconds
    }

    public double solveForSpeed(double x, double y, double angle){
        //solves a trajectory equation for velocity
        double top = (6*(x*x)*(1/Math.pow(Math.cos(angle), 2)));
        double bottom = 2*(x*Math.tan(angle)-y);
        double vel = Math.sqrt(top/bottom);
        return vel;
    }
}
