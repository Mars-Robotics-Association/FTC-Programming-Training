package org.firstinspires.ftc.teamcode.MechanicalControl.Belinda;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BelindaShooterIntakeController
{
    ////DEPENDENCIES////
    OpMode opMode;
    //Motors
    private DcMotor[] shooterMotors;
    private double[] motorSpeedMultipliers;
    private DcMotor intakeMotor;
    //Servos
    private Servo[] servos;
    private double pathSpeed;
    //Sensors
    private DistanceSensor discLoadedSensor; //for detecting if disc is ready to be loaded into shooter
    private double discLoadedDistance = 0;

    ////PRIVATE VARIABLES////
    private double startTime = 0;
    private boolean shooterOn = false;

    public void Init(OpMode setOpMode, DcMotor[] setShooterMotors, double[] setSpeedMultipliers, Servo[] setPathServos, double setPathSpeed, DcMotor setIntakeMotor, DistanceSensor setDiscLoadedSensor, double setDiscLoadedDistance){
        opMode = setOpMode;

        shooterMotors = setShooterMotors;
        motorSpeedMultipliers = setSpeedMultipliers;

        servos = setPathServos;
        pathSpeed = setPathSpeed;

        intakeMotor = setIntakeMotor;

        discLoadedSensor = setDiscLoadedSensor;
        discLoadedDistance = setDiscLoadedDistance;
    }

    public void SetShooterPower(double power){
        int i = 0;
        for (DcMotor motor: shooterMotors) {
            motor.setPower(power * motorSpeedMultipliers[i]);
            i++;
        }
    }

    public void SetPathPower(double power){
        for (Servo s : servos) {
            s.setPosition(power*pathSpeed);
        }
    }

    public boolean IsDiscLoaded(){
        double distance = discLoadedSensor.getDistance(DistanceUnit.INCH);
        if(distance<discLoadedDistance) return  true;
        else return false;
    }

    public void Intake(){
        intakeMotor.setPower(-1);
        if(IsDiscLoaded()) SetPathPower(0.5);
        else SetPathPower(1);
    }

    public void ShootRoutine(){
        SetShooterPower(1); //spin up shooter

        if(!shooterOn)startTime = opMode.getRuntime();
        shooterOn = true;

        if(startTime+1 > opMode.getRuntime()) return; //wait one second

        SetPathPower(1); //move disc into shooter to shoot

        if(startTime+1.5 > opMode.getRuntime()) return; //wait half a second

        SetPathPower(0); //stop everything
        SetShooterPower(0);
    }

    public void ShooterOn(){SetShooterPower(1);}
    public void ShooterOff(){
        SetShooterPower(0);
        SetPathPower(0.5);
        shooterOn = false;
    }
    public void IntakeOff(){
        SetPathPower(0.5);
        intakeMotor.setPower(0);
    }
}
