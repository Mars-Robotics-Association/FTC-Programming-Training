package org.firstinspires.ftc.teamcode.Training;

//IMPORT THE CODE LIBRARIES NEEDED
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.Core.Input.ControllerInput;
import org.firstinspires.ftc.teamcode.Core.Input.ControllerInputListener;

//import org.firstinspires.ftc.teamcode.Core.Robots.CuriosityUltimateGoalControl;

import org.firstinspires.ftc.teamcode.Core.Robots.MecanumBaseControl;
import org.firstinspires.ftc.teamcode.MechanicalControl.Kenobi.WobbleGoalController;

//SimpleTeleOpKenobi (or whatever name is here) shows up on the phone so you can select it
@TeleOp(name = "SimpleTeleOp", group = "Competition")

@Config
//This name must match the file name
public class SimpleTeleOp extends OpMode implements ControllerInputListener {
    ////Dependencies////
    // private WobbleGoalController wobble;
    private MecanumBaseControl control;
    private ControllerInput controllerInput1;
    private ControllerInput controllerInput2;

    //declare wheel motors
    private DcMotor FR;//front right wheel
    private DcMotor FL;//front left wheel
    private DcMotor RR;//rear right wheel
    private DcMotor RL;//rear left wheel

    //declare shooter motors
    private DcMotor shooterLeft;
    private DcMotor shooterRight;

    //declare intake motor
    private DcMotor intakeDcMotor;

    //declare feeder motor
    private DcMotor feedDcMotor;

    // declare servos for wobble lift
    private Servo wobbleLiftServo1;
    private Servo wobbleLiftServo2;
    private Servo wobbleLeftServo;
    private Servo wobbleRightServo;

    ////Variables////
    //Tweaking Vars
    public static double driveSpeed = .75;//used to change how fast robot drives
    public static double turnSpeed = .75;//used to change how fast robot turns
    public static double turnWhileDrivingSpeed = 0.5;

    public double wobbleLiftServoPosition;//used to determine the positon of the wobble lift

    private double speedMultiplier = 1;

    private boolean busy = false;//used to test if it's ok to drive
    private double turnOffset = 0;

    private int payloadController = 2;

    private double ArmMultiplier = 1;
    private boolean ArmPos = false;
    private boolean ArmNeg = false;

    //delcare variables and initialiaze them (not all of these variables were used in the final version of the class
    private boolean ShooterRampUpDone = false;//used to determine if set up is done in prep for shooting
    private boolean PullBackFeederTimerIsSet = false;//used to determine if timer has been set to pull back feeder in prep for shooting
    private boolean ShooterRampUpTimerSet = false;//used to determine if shooters are ramped up in prep for shooting
    private boolean ShooterTimerSet = false;//used to determine if a timer has been set for shooting
    private boolean NeedPullBackFeeder = true;//used to determine if the feeder needs to be reversed briefly in prep for shooting
    private long PullBackFeederTimer = 0;
    private long ShooterRampUpTimer = 0;
    private long ShooterTimer = 0;
    private long shootCycles = 0;
    private long rampCycles = 0;
    private long shootDirection = -1;  //set for direction of shooter motors:  -1 or 1 (use on each shooter call)

    @Override
    public void init() {
        //map the wheels to the robot configuration
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        RR = hardwareMap.dcMotor.get("RR");
        RL = hardwareMap.dcMotor.get("RL");

        //map the shooter motors to the robot configuration
        shooterLeft = hardwareMap.dcMotor.get("shooterLeft");
        shooterRight = hardwareMap.dcMotor.get("shooterRight");

        //map the wobble lift and the wobble arms to the robot configuration
        // wobbleLiftServo = hardwareMap.crservo.get("wobbleLiftServo");
        wobbleLiftServo1 = hardwareMap.servo.get("wobbleLiftServo1");
        wobbleLiftServo2 = hardwareMap.servo.get("wobbleLiftServo2");
        wobbleLeftServo = hardwareMap.servo.get("wobbleLeftServo");
        wobbleRightServo = hardwareMap.servo.get("wobbleRightServo");

        //map the intake, feed, and shooter to the robot configuration
        intakeDcMotor = hardwareMap.dcMotor.get("intakeDcMotor");
        feedDcMotor = hardwareMap.dcMotor.get("feedDcMotor");

        //You must set these booleans to use the mecanum wheels: set useChassis to true, usePayload to false, useNavigator to false
        control = new MecanumBaseControl(this, true, false, false);
        control.InitCoreRobotModules();

        controllerInput2 = new ControllerInput(gamepad2, 2);
        controllerInput1 = new ControllerInput(gamepad1, 1);
        controllerInput1.addListener(this);
        controllerInput2.addListener(this);
        telemetry.setAutoClear(false);
        telemetry.addData("Speed Multiplier", speedMultiplier);
        telemetry.update();
    }

    @Override
    public void start() {
        control.StartCoreRobotModules();
    }

    @Override
    public void loop() {

        controllerInput1.Loop();
        controllerInput2.Loop();

        if (!busy) {
            MangeDriveMovement();
        }


//GAMEPAD 1

        if (gamepad1.x) {
            shooterLeft.setPower(1 * shootDirection);//-1 reverses direction
            shooterRight.setPower(-1 * shootDirection);
        }

        if (gamepad1.b) {
            shootOnce();
        }

        if(!gamepad1.b ) {
            initalizeShootingCycle();
        }


        //CODE TO CONTROL INTAKE
        //if the "A" button on gamepad 1 is pressed, the power on the intakeServo is set to 1 (max power)
        if (gamepad1.a) {
            intakeDcMotor.setPower(1);
        }

        if (gamepad1.left_trigger > .5) { //left trigger start and leave intake at max speed until stopped
            intakeDcMotor.setPower(1);
        }
        if (gamepad1.left_bumper) { //left bumper start and leave intake at max speed until stopped
            intakeDcMotor.setPower(-1);
        }
        //any dpad stops all shooter motors
        if (gamepad1.dpad_left | gamepad1.dpad_right | gamepad1.dpad_up | gamepad1.dpad_down) {
            intakeDcMotor.setPower(0);
            feedDcMotor.setPower(0);
            shooterLeft.setPower(0);
            shooterRight.setPower(0);
        }

        if (gamepad1.right_trigger > .5) { //left trigger start and leave intake at max speed until stopped
            feedDcMotor.setPower(-1);
        }
        if (gamepad1.right_bumper) { //left bumper start and leave intake at max speed until stopped
            feedDcMotor.setPower(1);
        }
        if (gamepad1.dpad_left | gamepad1.dpad_right | gamepad1.dpad_up | gamepad1.dpad_down) {
            //any dpad stops the feed
            feedDcMotor.setPower(0);
        }
//GAMEPAD 2
        //close the arms completely
        if (gamepad2.y) {
            wobbleLeftServo.setPosition(0);
            wobbleRightServo.setPosition(1);
        }

        //open the arms completely
        if (gamepad2.b) {
            wobbleLeftServo.setPosition(1);
            wobbleRightServo.setPosition(0);//reverses the direction of the arm
        }

        //open the lift arms to grab a wobble
        if (gamepad2.a) {
            wobbleLeftServo.setPosition(.8);
            wobbleRightServo.setPosition(.2);
        }


        if (gamepad2.left_bumper) {
            wobbleLiftServo1.setPosition(wobbleLiftServo1.getPosition());
            wobbleLiftServo2.setPosition(wobbleLiftServo2.getPosition());
        }

        //raise the lift
        if (gamepad2.dpad_up) {
            wobbleLiftServo1.setPosition(.7);
            wobbleLiftServo2.setPosition(.7);
        }
        //lower the lift
        if (gamepad2.dpad_down) {
            wobbleLiftServo1.setPosition(.1);
            wobbleLiftServo2.setPosition(.1);
        }
        //position lift at low goal height to pick up the wobble
        if (gamepad2.dpad_left) {
            wobbleLiftServo1.setPosition(.1);
            wobbleLiftServo2.setPosition(.1);
        }

    }

//These @Override methods are needed for a clean compile.  They work with one of Owen's classes and the code won't compile without them

    @Override
    public void APressed(double controllerNumber) {

    }


    @Override
    public void BPressed(double controllerNumber) {

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


    //CODE USED TO DRIVE THE ROBOT (drive with left joy stick and rotate with right joy stick)
    private void MangeDriveMovement(){
        //MOVE if left joystick magnitude > 0.1
        if (controllerInput1.CalculateLJSMag() > 0.1) {
            control.RawDrive(controllerInput1.CalculateLJSAngle(), controllerInput1.CalculateLJSMag() * driveSpeed, controllerInput1.GetRJSX() * turnWhileDrivingSpeed);//drives at (angle, speed, turnOffset)
            //control.GetOrion().MoveRaw(gamepad1.left_stick_y * driveSpeed, gamepad1.left_stick_x * driveSpeed, controllerInput1.GetRJSX()*turnWhileDrivingSpeed);
            telemetry.addData("Moving at ", controllerInput1.CalculateLJSAngle());
        }

        //TURN if right joystick magnitude > 0.1 and not moving
        else if (Math.abs(controllerInput1.GetRJSX()) > 0.1) {
            control.RawTurn(controllerInput1.GetRJSX() * turnSpeed * -1);//turns at speed according to rjs1
            //control.GetOrion().TurnRaw(controllerInput1.GetRJSX() * turnSpeed);
            telemetry.addData("Turning", true);
        }
        else {
            control.GetChassis().SetMotorSpeeds(0, 0, 0, 0);
        }
    }

    private void shootOnce() {
        //HINT: when the shooter is activated, a) reverse the feeder motors for .5 seconds to pull the ring back so it clears the wheels, b) activate/ramp-up the shooter motors for 3 seconds (or 3000 milliseconds)  c) reengage the feeder motors for 2 seconds to fire/shoot the ring and d) stop the feeder and shooter motors

        //INSERT CODE FOR THE shootOnce() METHOD HERE


    }
    //initialize the variables for the next shooting cycle
    public void initalizeShootingCycle(){
        shooterLeft.setPower(0);  // stop shooter
        shooterRight.setPower(0);
        ShooterRampUpDone = false;//used to determine if set up is done in prep for shooting
        PullBackFeederTimerIsSet = false;//used to determine if timer has been set to pull back feeder in prep for shooting
        ShooterRampUpTimerSet = false;//used to determine if shooters are ramped up in prep for shooting
        ShooterTimerSet = false;//used to determine if a timer has been set for shooting
        NeedPullBackFeeder = true;//used to determine if the feeder needs to be reversed briefly in prep for shooting
        PullBackFeederTimer = 0;
        ShooterRampUpTimer = 0;
        ShooterTimer = 0;
        shootCycles = 0;
        rampCycles = 0;
    }
}