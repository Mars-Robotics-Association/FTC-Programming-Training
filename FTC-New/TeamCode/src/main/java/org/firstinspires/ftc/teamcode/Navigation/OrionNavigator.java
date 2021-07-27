package org.firstinspires.ftc.teamcode.Navigation;

////SENSING////
//Vuforia.java
//Tensorflow Package
//DistanceSensorArray.java
//ColorSensorArray.java

////DRIVING////
//Roadrunner Package
//Orion Package

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Core.Robots.MecanumBaseControl;
import org.firstinspires.ftc.teamcode.Navigation.Roadrunner.RoadrunnerControl;
import org.firstinspires.ftc.teamcode.Sensors.Arrays.DemobotSensorArray;
import org.firstinspires.ftc.teamcode.Navigation.Tensorflow.TensorFlowObjectDetector;
import org.firstinspires.ftc.teamcode.Navigation.Vuforia.VuMarkNavigation;

public class OrionNavigator
{
    //TODO ====REFERENCES====
    private RoadrunnerControl rr;
    private VuMarkNavigation vuforiaFront;
    private VuMarkNavigation vuforiaBack;
    private TensorFlowObjectDetector tf;
    private DemobotSensorArray sa;
    private RobotTransformSystem cs;
    private MecanumBaseControl control;
    private OpMode opMode;

    //TODO ====VARIABLES====
    private double tfDistCoefficient = 1;
    private double tfXCoefficient = 1;
    public void SetTFCoefficients(double distCoefficient, double xCoefficient){
        tfDistCoefficient = distCoefficient;
        tfXCoefficient = xCoefficient;
    }


    public OrionNavigator(OpMode setOpMode, MecanumBaseControl setControl){
        opMode = setOpMode;
        control = setControl;
    }

    public void Init(){
        if(control.isUSE_CHASSIS()) {
            rr = new RoadrunnerControl(opMode);
            rr.Init();
        }
        vuforiaFront = new VuMarkNavigation(opMode, "Webcam 1");
        //vuforiaBack = new VuMarkNavigation(opMode, "Webcam 2");
        tf = new TensorFlowObjectDetector(opMode, vuforiaFront.GetVuforia(), new double[]{0,0,0});
        cs = new RobotTransformSystem(0,0,0);
        //sa = new DemobotSensorArray(opMode);
    }

    //TODO ====SIMPLE METHODS====
    public void Turn(double angle){rr.Turn(angle);}
    public void MoveSpline(double x, double y, double tangent, boolean reverse){rr.MoveSpline(x,y,tangent, reverse);}
    public void MoveSplineConstHeading(double x, double y, double tangent, boolean reverse){rr.MoveSplineConstantHeading(x,y,tangent, reverse);}
    public void MoveLinear(double x, double y, double heading){rr.MoveLine(x,y,heading);}
    public void SetPose(double x, double y, double heading){
        if(control.isUSE_CHASSIS())rr.SetPose(x,y,heading);
        cs.SetRobotGlobalPose(x,y,heading);
    }
    public Pose2d GetPose(){
        if(control.isUSE_CHASSIS()) return rr.GetCurrentPose();
        else return null;
    }
    public void UpdatePose(){
        if(control.isUSE_CHASSIS()){
            Pose2d robotPose = rr.GetCurrentPose();
            cs.SetRobotGlobalPose(robotPose.getX(), robotPose.getY(), robotPose.getHeading());
        }
    }

    public void AlignToVumark(int vumarkIndex, double xOffset, double yOffset, double headingOffset){
        double[] vumarkData = vuforiaFront.GetData(vumarkIndex);
        if(vumarkData == null) return;
        if(!control.isUSE_CHASSIS()) return;
        rr.SetPose(vumarkData[2], vumarkData[0], Math.toRadians(vumarkData[4]));

        MoveLinear(xOffset, yOffset, 0);

        vumarkData = vuforiaFront.GetData(vumarkIndex);
        if(vumarkData == null) return;
        rr.SetPose(vumarkData[2], vumarkData[0], Math.toRadians(vumarkData[4]));

        TurnTo(headingOffset);
    }


    /**
     * Moves the robot towards the closest visible disc. Does not adjust using heading, but rather
     * by using side-to-side strafing.
     *
     * @param  speed  the speed at which to move the robot.
     * @param  correctionCoefficient  a multiplier for that speed to be calibrated for each robot. Values should be low.
     */
    public void MoveTowardsDiscRaw(double speed, double correctionCoefficient){
        UpdatePose();
        double error = tf.GetClosestDisc()[0] * correctionCoefficient;
        if(error == 0) return;
        rr.MoveRaw(new Pose2d(speed, error, rr.GetCurrentPose().getHeading())); //try to keep disc in center of screen
    }

    public double TurnTowardsDiscSpeed(double correctionCoefficient){
        UpdatePose();
        double error = tf.GetClosestDisc()[0];
        if(error == 0) return 0;
        return error * correctionCoefficient; //try to keep disc in center of screen
    }

    public void TurnTowardsVuMark(double speed, int vumarkCode, double correctionCoefficient, boolean useFrontVuforia){
        double[] data;
        data = vuforiaFront.GetData(vumarkCode);

        double rotationalError = data[4]; //get heading
        /*if(rotationalError > 0) rotationalError -= 180;
        else if(rotationalError < 0) rotationalError += 180;*/
        if(!(rotationalError > 0 ||rotationalError < 0 || rotationalError == 0)) rotationalError = 0;

        opMode.telemetry.addData("VuMark heading error ", rotationalError);

        if(!(rotationalError > 0 || rotationalError < 0 || rotationalError ==0)) return; //if its null

        rr.TurnRaw(speed*rotationalError*correctionCoefficient); //turn based on the rotational error towards vumark
    }

    public void MoveRaw(double x, double y, double turn){rr.MoveRaw(new Pose2d(x,y,turn));}
    public void TurnRaw(double speed){rr.TurnRaw(speed);}
    public void TurnTo(double angle){rr.TurnTo(angle);}

    public int GetNumberOfDiscs(double upperLimit){return tf.ReturnNumberOfDiscsInSight(upperLimit);}

    //TODO: ====TELEMETRY METHODS FOR DEBUG====
    public void PrintVuforiaTelemetry(int vumarkCode){
        double[] data = vuforiaFront.GetData(vumarkCode);
        //opMode.telemetry.addData("vumark is ",data[3] + " inches away, "+data[4]+" degrees right, and "+data[0]+" inches high.");
        opMode.telemetry.addLine("X: " + data[2] + ", Y: " + data[0] + ", Angle: " + data[4]);
    }
    public void PrintTensorflowTelemetry(){
        opMode.telemetry.addLine("===TF data===");
        tf.PrintTFTelemetry();
    }
}
