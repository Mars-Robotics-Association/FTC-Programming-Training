package org.firstinspires.ftc.teamcode.MechanicalControl.Demobot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ServoController;

//REQUIRED TO COMPILE: REV Hub | Phones | Shooter

public class DemobotShooter
{
    ////DEPENDENCIES////
    //Motors/servos
    private DcMotor spinner;//motor controlling spinners that fire ball
    private DcMotor loader;//motor that loads ball into assembly
    private ServoController aimer;//servo for aiming the shooter

    ////VARIABLES////
    //Calibration
    private double kS = 20; //the velocity in m/s max motor speed applies
    private double kA = 1; //ration from servo ticks to degrees
    private double shooterHeight = 0.5; //in meters

    //Utility
    private double tDist;//target x for trajectory
    private double tY;//target y for trajectory
    private double tHeading;//target angle for trajectory

    ////METHODS////
    //Public//
    public void Init(DcMotor setSpinner, DcMotor setLoader, ServoController setAimer, double shooterVelocity){
        spinner = setSpinner;
        loader = setLoader;
        aimer = setAimer;
        spinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//for reliable velocity
        kS = shooterVelocity;//calculated by shooter calibration opmode
    }

    public void SetTrajectory(double targetDist, double targetHeight, double targetAngle){
        //sets the target trajectory variables to an x and y and angle relative to the robot
        tDist = targetDist;
        tY = targetHeight;
        tHeading = targetAngle;
    }
    public void Aim(){
        //Rotates shooter and aims it at target.
        //Rotate to target using tAngle
        //Aim with servo
        //aimer.setPosition(FindAngleToFireAt(kS, shooterHeight, tY, tDist) * kA); //TODO: verify if new works and remove
        aimer.setServoPosition(0,FindAngleToFireAt(kS, shooterHeight, tY, tDist) * kA);
    }
    public void SpinUp(){
        //Spins up shooter to speed depending on target. You should aim it before this.
        spinner.setPower(1);
    }
    public void Fire(){
        //Loads shooter. You should spin it up before this.
        spinner.setPower(1);
        loader.setPower(1);
    }
    public void CombinedFire(double targetDist, double targetHeight, double targetAngle, double spinupTime){
        //Aims, shoots, waits, and fires in sequence.
        SetTrajectory(targetDist, targetHeight, targetAngle);
        Aim();
        SpinUp();
        //TODO: add spinup time usage
        Fire();
    }
    public void RawAim(double fireAngle){
        //Moves guide to target rotation
        //aimer.setPosition(fireAngle * kA); //TODO: verify if new works and remove
        aimer.setServoPosition(0,fireAngle * kA);
    }
    public double GetTargetHeading(){
        return tHeading;
    }

    //Private//

    //Trajectory calculations//
    public double FindAngleToFireAt(double speed, double shooterY, double targetY, double targetDist){
        //calculates angle to aim at for given data which should all be in meters
        double yDif = targetY - shooterY;//uses formula from here: https://www.forrestthewoods.com/blog/solving_ballistic_trajectories/ from the "Firing Angle to Hit Stationary Target" section
        double bot = 9.8 * targetDist;//Gx
        double top1 = (9.8*Math.pow(targetDist,2) + 2*Math.pow(speed,2)*yDif);//(Gx^2 + 2*S^2*y)
        double top2 = Math.sqrt(Math.pow(speed, 4)-(9.8*top1));//SQRT(S^4-top1)

        double topHigh = Math.pow(speed, 2)+top2;//shoot high
        double topLow = Math.pow(speed, 2)-top2;//shoot low

        double full = Math.atan(topHigh/bot);
        return Math.toDegrees(full);
    }
}
