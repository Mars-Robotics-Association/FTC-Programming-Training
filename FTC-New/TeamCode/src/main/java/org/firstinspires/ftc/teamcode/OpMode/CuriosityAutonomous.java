package org.firstinspires.ftc.teamcode.OpMode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Core.Robots.CuriosityUltimateGoalControl;
import org.firstinspires.ftc.teamcode.Navigation.OrionNavigator;

@Config
@Autonomous(name = "*CURIOSITY AUTO*", group = "Curiosity")
public class CuriosityAutonomous extends LinearOpMode {
    private CuriosityUltimateGoalControl control;
    private OrionNavigator orion;
    private FtcDashboard dashboard;

    public static double tfDistCoef = 6666;
    public static double tfXCoef = 0.001;

    public static double robotX = 0;
    public static double robotY = 4;
    public static double robotH = 180;

    public static double powerShotStartAngle = 2;
    public static double powerShotStartX = 60;
    public static double powerShotStartY = 36;
    public static double powerShotIncrament = -6;

    public static double tfUpperLimit = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        control = new CuriosityUltimateGoalControl(this,true,true,true);
        control.Init();
        orion = control.GetOrion();
        dashboard = FtcDashboard.getInstance();
        dashboard.setTelemetryTransmissionInterval(25);

        waitForStart();
        orion.SetPose(robotX, robotY, Math.toRadians(robotH));//robot starts on blue left line

        control.StarpathToShooter();
        control.RotateStarpathToPreviousPos();

        //Move to where it can see discs
        orion.MoveLinear(16, 8, 0);

        sleep(200);//wait for tensorflow to detect discs
        int numberOfDiscs = orion.GetNumberOfDiscs(tfUpperLimit);//figure out where to go\

        //Go near square A
        orion.MoveSpline(40, 0, 0, true);

        if(numberOfDiscs == 0){ //A
            //deposit wobble goal
            orion.MoveLinear(68, -6, 0);
            orion.MoveLinear(60, -6, 0);
        }
        else if(numberOfDiscs > 0 && numberOfDiscs < 3){ //B
            //spline to B, deposit
            orion.MoveSpline(88, 12, 0, true);
            orion.MoveLinear(80, 12, 0);
        }
        else { //C
            //keep going forwards, deposit
            orion.MoveLinear(110, -8, 0);
            orion.MoveLinear(102, -8, 0);
        }

        //Line up for high goal
        control.ShooterOn();
        orion.MoveLinear(60, 18, 0);
        orion.TurnTo(180);
        HighGoalRoutine();

        orion.MoveLinear(40, -4, 0);
        orion.MoveLinear(4, 4, 0);
        orion.MoveLinear(1, 26, 0);

        orion.MoveSpline(40, 32, 0, true);

        if(numberOfDiscs == 0){ //A
            //deposit wobble goal
            orion.MoveSpline(60, -6, -45, true);
            orion.MoveLinear(55, -6, 0);
        }
        else if(numberOfDiscs > 0 && numberOfDiscs < 3){ //B
            //spline to B, deposit
            orion.MoveSpline(80, 12, 0, true);
            orion.MoveLinear(75, 12, 0);
        }
        else { //C
            //keep going forwards, deposit
            orion.MoveSpline(102, -8, 0, true);
            orion.MoveLinear(97, -8, 0);
        }

        orion.MoveLinear(65, 0, 0);
    }

    private void HighGoalRoutine(){
        control.ShooterOn();
        control.RotateStarpathToNextPos();
        sleep(1500);
        control.RotateStarpathToNextPos();
        sleep(1500);
        control.RotateStarpathToNextPos();
        sleep(500);
        control.ShooterOff();
    }

    private void PowerShotRoutine(){
        //Start at (x,y)
        control.ModifyForPowerShot();
        control.ShooterOn();

        //turn to first shot
        orion.Turn(powerShotStartAngle);
        control.ShootOne();
        control.ShooterOn();

        control.ModifyForPowerShot();
        //turn to second shot
        orion.Turn(powerShotIncrament);
        //orion.MoveLinear(powerShotStartX, powerShotStartY+powerShotIncrament, 0);
        control.ShootOne();
        control.ShooterOn();

        control.ModifyForPowerShot();
        //turn to third shot
        orion.Turn(powerShotIncrament);
        //orion.MoveLinear(powerShotStartX, powerShotStartY+powerShotIncrament+powerShotIncrament, 0);
        control.ShootOne();

        //Reset shooter
        control.ShooterOff();
        control.StopModifyForPowerShot();
    }
    /*private void Shoot(){
        control.ShootAsync();
        while(control.IsShooterRunning()){
            control.ShootAsync();
            telemetry.addLine("Shooting");
            telemetry.update();
        }
        control.StopShootAsync();
        control.ShooterOn();
    }*/
}

/*if(numberOfDiscs == 0){ //go to A
        telemetry.addLine("close target");
        orion.MoveSpline(30, 12, 0);//drop off wobble goal 1
        orion.MoveLinear(-62, -36, 0);//go to second wobble goal
        orion.MoveLinear(0, -24, 0);
        orion.MoveSpline(62, 60, 0);//places it
        }

        else if(numberOfDiscs > 0 && numberOfDiscs < 3){ //go to B
        telemetry.addLine("middle target");
        orion.MoveSpline(54, -2, 0);//drop off wobble goal 1
            *//*orion.MoveLinear(-10, -30, 0);
            orion.MoveLinear(-72, 0, 0);*//*
        orion.MoveLinear(-44,-50, 0);//heads back
        orion.MoveLinear(-40,0, 0);
        orion.MoveLinear(0,20,0);//lines up for second wobble goal
        orion.MoveLinear(30,-3,0);//places it
        orion.MoveSpline(56,10,0);
        orion.MoveLinear(-15,0,0);//goes back to line

        }

        else{ //go to C
        telemetry.addLine("far target");
        orion.MoveSpline(78, 12, 0);//drop off wobble goal 1
        orion.MoveLinear(-68,-52, 0);//heads back
        orion.MoveLinear(-42,0, 0);
        orion.MoveLinear(0,16,0);//lines up for second wobble goal
        orion.MoveLinear(30,0,0);//places it
        orion.MoveSpline(80,34,0);
        }*/
