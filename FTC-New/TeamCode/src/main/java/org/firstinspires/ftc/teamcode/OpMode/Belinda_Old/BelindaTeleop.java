package org.firstinspires.ftc.teamcode.OpMode.Belinda_Old;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Core.Input.ControllerInput;
import org.firstinspires.ftc.teamcode.Core.Input.ControllerInputListener;
import org.firstinspires.ftc.teamcode.Core.Robots.BelindaControl;

//The class for controlling the robot in teleop. Includes basic drive movement, shooter operations,
//and advanced autonomous functions.

//REQUIRED TO RUN: Phones | REV Hub | Demobot Chassis | Shooter | Odometry Unit
//REQUIRED TO FUNCTION: Controllers

//@Config
@TeleOp(name = "*Belinda TeleOp*")
@Disabled
public class BelindaTeleop extends OpMode implements ControllerInputListener
{
    ////Dependencies////
    private BelindaControl control;
    private ControllerInput controllerInput1;
    private ControllerInput controllerInput2;
    FtcDashboard dashboard;

    ////Variables////
    //Tweaking Vars
    public static double turnWhileDrivingSpeed = 0.5;//used to change how fast robot turns when driving
    public static double driveSpeed = 0.8;//used to change how fast robot drives
    public static double turnSpeed = 0.5;//used to change how fast robot turns
    public static double headingP = 0.002;
    public static double headingI = 0;
    public static double headingD = 0.001;
    //Utility Vars
    private boolean busy = false;

    @Override
    public void init() {
        //Sets up demobot control class
        control = new BelindaControl(this, true, false, true);
        control.Init();

        //Sets up controller inputs
        controllerInput1 = new ControllerInput(gamepad1, 1);
        controllerInput1.addListener(this);
        controllerInput2 = new ControllerInput(gamepad2, 2);
        controllerInput2.addListener(this);

        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);
    }

    @Override
    public void start(){
        control.Start();
    }

    @Override
    public void loop() {
        controllerInput1.Loop();
        controllerInput2.Loop();

        //Only run if robot isn't busy
        if(!busy) {
            MangeDriveMovement();
        }

        control.SetDrivePID(headingP, headingI, headingD);
        telemetry.addData("angular vel ", control.GetImu().GetAngularVelocity());
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay = packet.fieldOverlay();
        packet.put("angular vel", control.GetImu().GetAngularVelocity());
        packet.put("heading pid offset", control.GetPID().getOutput(controllerInput1.GetRJSX() * turnWhileDrivingSpeed, control.GetImu().GetAngularVelocity()));
        packet.put("Robot velocity x ", control.GetImu().GetAcceleratioin().xAccel);
        packet.put("Robot velocity y ", control.GetImu().GetAcceleratioin().yAccel);
        packet.put("Robot actual angle ", controllerInput1.CalculateLJSAngle());
        dashboard.sendTelemetryPacket(packet);
    }

    //Mange driving of the robot via the joysticks
    private void MangeDriveMovement(){
        //MOVE if left joystick magnitude > 0.1
        if (controllerInput1.CalculateLJSMag() > 0.1) {
            //control.RawDrive(controllerInput1.CalculateLJSAngle(), controllerInput1.CalculateLJSMag() * driveSpeed, controllerInput1.GetRJSX() * turnWhileDrivingSpeed);//drives at (angle, speed, turnOffset)
            control.GetOrion().MoveRaw(gamepad1.left_stick_y * driveSpeed, gamepad1.left_stick_x * driveSpeed, controllerInput1.GetRJSX()*turnWhileDrivingSpeed);
            telemetry.addData("Moving at ", controllerInput1.CalculateLJSAngle());
        }
        //TURN if right joystick magnitude > 0.1 and not moving
        else if (Math.abs(controllerInput1.GetRJSX()) > 0.1) {
            //control.RawTurn(controllerInput1.GetRJSX() * turnSpeed);//turns at speed according to rjs1
            control.GetOrion().TurnRaw(controllerInput1.GetRJSX() * turnSpeed);
            telemetry.addData("Turning", true);
        }
        else {
            control.GetChassis().SetMotorSpeeds(0,0,0,0);
        }
    }

    @Override
    public void APressed(double controllerNumber) {
        if(controllerNumber == 1){
            //AIM SHOOTER if A pressed
            //control.AimShooter();
        }
    }

    @Override
    public void BPressed(double controllerNumber) {
        if(controllerNumber == 1){
            //AIM SHOOTER if A pressed
            //control.FireShooter();
        }
    }

    @Override
    public void XPressed(double controllerNumber) {
    }

    @Override
    public void YPressed(double controllerNumber) {

    }

    @Override
    public void AHeld(double controllerNumber) {

    }

    @Override
    public void BHeld(double controllerNumber) {

    }

    @Override
    public void XHeld(double controllerNumber) {
        if(controllerNumber == 1){
            busy = true;
            control.MoveTowardsClosestDisc();
            telemetry.addLine("Moving to closest disc!");
            telemetry.update();
        }
    }

    @Override
    public void YHeld(double controllerNumber) {

    }

    @Override
    public void AReleased(double controllerNumber) {

    }

    @Override
    public void BReleased(double controllerNumber) {

    }

    @Override
    public void XReleased(double controllerNumber) {
        if(controllerNumber == 1) busy = false;
    }

    @Override
    public void YReleased(double controllerNumber) {

    }

    @Override
    public void LBPressed(double controllerNumber) {

    }

    @Override
    public void RBPressed(double controllerNumber) {

    }

    @Override
    public void LTPressed(double controllerNumber) {
        if(controllerNumber == 1){
            busy = true;
            control.Brake();
        }
    }

    @Override
    public void RTPressed(double controllerNumber) {

    }

    @Override
    public void LBHeld(double controllerNumber) {

    }

    @Override
    public void RBHeld(double controllerNumber) {

    }

    @Override
    public void LTHeld(double controllerNumber) {

    }

    @Override
    public void RTHeld(double controllerNumber) {

    }

    @Override
    public void LBReleased(double controllerNumber) {

    }

    @Override
    public void RBReleased(double controllerNumber) {

    }

    @Override
    public void LTReleased(double controllerNumber) {

    }

    @Override
    public void RTReleased(double controllerNumber) {

    }

    @Override
    public void DUpPressed(double controllerNumber) {

    }

    @Override
    public void DDownPressed(double controllerNumber) {

    }

    @Override
    public void DLeftPressed(double controllerNumber) {

    }

    @Override
    public void DRightPressed(double controllerNumber) {

    }

    @Override
    public void DUpHeld(double controllerNumber) {

    }

    @Override
    public void DDownHeld(double controllerNumber) {

    }

    @Override
    public void DLeftHeld(double controllerNumber) {

    }

    @Override
    public void DRightHeld(double controllerNumber) {

    }

    @Override
    public void DUpReleased(double controllerNumber) {

    }

    @Override
    public void DDownReleased(double controllerNumber) {

    }

    @Override
    public void DLeftReleased(double controllerNumber) {

    }

    @Override
    public void DRightReleased(double controllerNumber) {

    }

    @Override
    public void LJSPressed(double controllerNumber) {

    }

    @Override
    public void RJSPressed(double controllerNumber) {

    }

    @Override
    public void LJSHeld(double controllerNumber) {

    }

    @Override
    public void RJSHeld(double controllerNumber) {

    }

    @Override
    public void LJSReleased(double controllerNumber) {

    }

    @Override
    public void RJSReleased(double controllerNumber) {

    }
}
