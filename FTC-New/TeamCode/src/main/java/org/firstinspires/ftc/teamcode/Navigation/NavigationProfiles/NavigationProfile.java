package org.firstinspires.ftc.teamcode.Navigation.NavigationProfiles;

import com.acmerobotics.roadrunner.control.PIDCoefficients;

public class NavigationProfile
{
    //Drive Constants
    public static final double TICKS_PER_REV_ = 8192;
    public static final double MAX_RPM = 160;
    public static final boolean RUN_USING_ENCODER = false;
    public static final PIDCoefficients MOTOR_VELO_PID = null;
    public static double WHEEL_RADIUS_CHASSIS = 2; // in
    public static double GEAR_RATIO_CHASSIS = 1; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 13; // in
    public static double kV =  0.01494; //1.0 / rpmToVelocity(MAX_RPM)
    public static double kA = 0.00004;
    public static double kStatic = 0.04877; //0.06510

    //Sample Mecanum Drive
    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(8, 0, 0);
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(8, 0, 0);

    public static String frontRightName = "FR";
    public static String frontLeftName = "FL";
    public static String backRightName = "RR";
    public static String backLeftName = "RL";

    public static boolean leftReversed = false;
    public static boolean rightReversed = true;

    //Standard Tracking Wheel Localizer
    public static double WHEEL_RADIUS_DEAD_WHEELS = 1; // in
    public static double GEAR_RATIO_DEAD_WHEELS = 1; // output (wheel) speed / input (encoder) speed

    public static double LATERAL_DISTANCE = 13.2; // in; distance between the left and right wheels
    public static double FORWARD_OFFSET = -6; // in; offset of the lateral wheel

    public static double X_MULTIPLIER = 1; // Multiplier in the X direction
    public static double Y_MULTIPLIER = 1; // Multiplier in the Y direction

    public static double ROT_LEFT = 180;
    public static double ROT_RIGHT = 0;
    public static double ROT_FRONT = 90;

    public static String LEFT_ENCODER_NAME = "FL";
    public static String RIGHT_ENCODER_NAME = "FR";
    public static String FRONT_ENCODER_NAME = "RR";

    //TF Object Detector
    public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Quad";
    public static final String LABEL_SECOND_ELEMENT = "Single";
    public static float cameraXOffset = -400;

    public NavigationProfile(){

    }

}
