package org.firstinspires.ftc.teamcode.OpMode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.Core.Input.ControllerInput;

//import org.firstinspires.ftc.teamcode.Core.Input.ControllerInputListener;
//import org.firstinspires.ftc.teamcode.Core.Robots.MecanumBaseControl;
import static java.lang.Thread.sleep;

//SimpleAutonomous (or whatever name is in the line below) shows up on the phone so you can select it to run
@Autonomous(name = "SimpleAutonomous", group = "Competition")
@Disabled
@Config
//This name must match the file name
public class SimpleAutonomous extends LinearOpMode //implements ControllerInputListener
{
    ////Dependencies////
    //  private MecanumBaseControl control;

    //declare wheel motors
    private DcMotor FR;//front right wheel
    private DcMotor FL;//front left wheel
    private DcMotor RR;//rear right wheel
    private DcMotor RL;//rear left wheel

    // declare shooters, intake and feed
    private DcMotor shooterLeft;
    private DcMotor shooterRight;
    private DcMotor intakeDcMotor;
    private DcMotor feedDcMotor;

    //delcare wobble lift and arms
    private Servo wobbleLiftServo1;
    private Servo wobbleLiftServo2;
    private Servo wobbleLeftServo;
    private Servo wobbleRightServo;

    ////Variables////
    //Tweaking Variables

    private boolean hasRunForward = false;

    @Override
    public void runOpMode() throws InterruptedException {
        //map the components to the robot configuration and set power on wheels to 0
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        RR = hardwareMap.dcMotor.get("RR");
        RL = hardwareMap.dcMotor.get("RL");

        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);

        shooterLeft = hardwareMap.dcMotor.get("shooterLeft");
        shooterRight = hardwareMap.dcMotor.get("shooterRight");

        wobbleLiftServo1 = hardwareMap.servo.get("wobbleLiftServo1");
        wobbleLiftServo2 = hardwareMap.servo.get("wobbleLiftServo2");
        wobbleLeftServo = hardwareMap.servo.get("wobbleLeftServo");
        wobbleRightServo = hardwareMap.servo.get("wobbleRightServo");

        intakeDcMotor = hardwareMap.dcMotor.get("intakeDcMotor");
        feedDcMotor = hardwareMap.dcMotor.get("feedDcMotor");

        waitForStart();
        DriveForward();

    }

    //START CODING THE DriveFoward() method after the "{" in the line below
    private void DriveForward() {

    }


}
}