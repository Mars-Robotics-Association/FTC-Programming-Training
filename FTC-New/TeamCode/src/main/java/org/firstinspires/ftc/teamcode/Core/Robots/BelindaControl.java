package org.firstinspires.ftc.teamcode.Core.Robots;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MechanicalControl.Belinda.BelindaShooterIntakeController;

/**
 * Control class for the Belinda Robot. Controls payload.
 * Required to run: Phones | REV Hub | Belinda Chassis
 * Suggested to run: Shooter | Intake | Odometry | Webcam
 */
//The class used to control the demobot. Autonomous functions, opmodes, and other scripts can call
//methods in here to control the demobot.

//REQUIRED TO RUN: Phones | REV Hub | Demobot Chassis | Shooter | Odometry Unit

public class BelindaControl extends MecanumBaseControl
{
    ////Dependencies////
    //Mechanical Components
    private BelindaShooterIntakeController shooterIntake;

    ////Variables////
    //Calibration
    private double shooterHeight = 0.5; //in meters

    /**@param setOpMode pass the opmode running this down to access hardware map
     * @param useChassis whether to use the chassis of the robot
     * @param usePayload whether to use the shooter/intake/lift of the robot
     * @param useNavigator whether to use Orion (webcams + odometry navigation)
     */
    public BelindaControl(OpMode setOpMode, boolean useChassis, boolean usePayload, boolean useNavigator) {
        super(setOpMode, useChassis, usePayload, useNavigator);
    }

    //SETUP METHODS//
    public void Init(){
        //TODO ===INIT PAYLOAD===
        if(USE_PAYLOAD) {
            DcMotor shooterMotor1 = currentOpMode.hardwareMap.dcMotor.get("SM1");
            DcMotor shooterMotor2 = currentOpMode.hardwareMap.dcMotor.get("SM2");
            DcMotor intakeMotor = currentOpMode.hardwareMap.dcMotor.get("IM");
            intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            Servo loaderServo = currentOpMode.hardwareMap.servo.get("loaderServo");
            DistanceSensor loaderSensor = currentOpMode.hardwareMap.get(DistanceSensor.class, "loaderSensor");

            shooterIntake = new BelindaShooterIntakeController();
            shooterIntake.Init(currentOpMode, new DcMotor[]{shooterMotor1, shooterMotor2}, new double[]{1,-1}, new Servo[]{loaderServo}, 1, intakeMotor, loaderSensor, 1);
        }

        //TODO ===INIT CORE ROBOT===
        super.InitCoreRobotModules();

        if(USE_NAVIGATOR) {
        }
    }

    public void Start(){
        super.StartCoreRobotModules();
    }

    //CALLABLE METHODS//
    public void ShooterRoutine(){shooterIntake.ShootRoutine();}
    public void StopShooter(){shooterIntake.ShooterOff();}
    public void Intake(){shooterIntake.Intake();}
    public void StopIntake(){shooterIntake.IntakeOff();}
}
