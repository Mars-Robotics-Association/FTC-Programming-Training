package org.firstinspires.ftc.teamcode.Core.Robots;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MechanicalControl.Curiosity.CuriosityPayloadController;

/**
 * Control class for the Belinda Robot. Controls payload.
 * Required to run: Phones | REV Hub | Belinda Chassis
 * Suggested to run: Shooter | Intake | Odometry | Webcam
 */
//The class used to control the demobot. Autonomous functions, opmodes, and other scripts can call
//methods in here to control the demobot.

//REQUIRED TO RUN: Phones | REV Hub | Demobot Chassis | Shooter | Odometry Unit

@Config
public class CuriosityUltimateGoalControl extends MecanumBaseControl
{
    ////Dependencies////
    //Mechanical Components
    private CuriosityPayloadController shooterIntake;

    ////Variables////
    //Calibration
    public static double shootXOffset = 70;
    public static double shootYOffset = 0;
    public static double shootAngleOffset = 180;

    /**@param setOpMode pass the opmode running this down to access hardware map
     * @param useChassis whether to use the chassis of the robot
     * @param usePayload whether to use the shooter/intake/lift of the robot
     * @param useNavigator whether to use Orion (webcams + odometry navigation)
     */
    public CuriosityUltimateGoalControl(OpMode setOpMode, boolean useChassis, boolean usePayload, boolean useNavigator) {
        super(setOpMode, useChassis, usePayload, useNavigator);
    }

    //SETUP METHODS//
    public void Init(){
        //TODO ===INIT PAYLOAD===
        if(USE_PAYLOAD) {
            DcMotor shooterMotor1 = currentOpMode.hardwareMap.dcMotor.get("SM1");
            DcMotor shooterMotor2 = currentOpMode.hardwareMap.dcMotor.get("SM2");
            Servo intakeServo1 = currentOpMode.hardwareMap.servo.get("intakeServo1");
            Servo intakeServo2 = currentOpMode.hardwareMap.servo.get("intakeServo2");
            Servo loaderServo = currentOpMode.hardwareMap.servo.get("loaderServo");
            Servo starpathServo = currentOpMode.hardwareMap.servo.get("starpathServo");
            Servo bootServo = currentOpMode.hardwareMap.servo.get("bootServo");

            DistanceSensor intakeSensor = currentOpMode.hardwareMap.get(Rev2mDistanceSensor.class, "intake sensor");

            shooterIntake = new CuriosityPayloadController();
            shooterIntake.Init(currentOpMode, new DcMotor[]{shooterMotor1, shooterMotor2}, intakeServo1, intakeServo2, bootServo, starpathServo, loaderServo, intakeSensor);
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
    public void ShootOne(){shooterIntake.ShootOne();}

    public void ShootRoutine(){
        double startTime = currentOpMode.getRuntime();
        ShooterOn();
        while (currentOpMode.getRuntime() < startTime+2){
            currentOpMode.telemetry.addLine("spinning up");
            currentOpMode.telemetry.update();
        }
        ShootThree();
    }

    public void AlignAndShoot(){
        ShooterOn();
        orion.AlignToVumark(0, shootXOffset, shootYOffset, shootAngleOffset);
        ShootThree();
    }

    public void ShootThree(){shooterIntake.ShootThree();}
    public void StopShootThree(){shooterIntake.StopShooting();}

    //public void ShootAsync(){shooterIntake.ShootAsync();}
    //public void StopShootAsync(){shooterIntake.StopShootAsync();}

    public void ShooterOn(){shooterIntake.ShooterOn();}
    public void ShooterOff(){shooterIntake.ShooterOff();}

    public void ModifyForPowerShot(){shooterIntake.ModifyForPowerShot();}
    public void StopModifyForPowerShot(){shooterIntake.StopModifyForPowerShot();}

    public void Intake(){shooterIntake.Intake();}
    public void StopIntake(){shooterIntake.StopIntake();}
    //public void LoadStarpath(){shooterIntake.LoadFromIntake();}
    //public void StopLoadStarpath(){shooterIntake.StopLoadFromIntake();}
    public void RotateStarpathToNextPos(){shooterIntake.RotateStarpathToNextPos();}
    public void RotateStarpathToPreviousPos(){shooterIntake.RotateStarpathToPreviousPos();}
    public void StarpathToShooter(){shooterIntake.StarPathToShooter();}
    public void StarpathToIntake(){shooterIntake.StarpathToIntake();}
    public boolean IsShooterRunning(){return shooterIntake.shooterRunning;}
}
