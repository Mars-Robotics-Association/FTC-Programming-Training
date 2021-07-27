package org.firstinspires.ftc.teamcode.Sensors.Arrays;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Sensors.ColorSensorArray;
import org.firstinspires.ftc.teamcode.Sensors.TouchSensorArray;

public class DemobotSensorArray
{
    //TODO ====REFERENCES====
    private OpMode opMode;
    private ColorSensorArray colorSensorArray;
    private TouchSensorArray touchSensorArray;

    //TODO: ====VARIABLES====
    private int lineAlpha = 20;
    private int discAlpha = 50;

    //TODO ====INITIALIZER====
    public DemobotSensorArray(OpMode setOpMode){
        opMode = setOpMode;
        //TODO: Fill these in with the config names of the sensors on the robot!
        colorSensorArray = new ColorSensorArray(opMode, new String[]{"cs_bottomFront", "cs_bottomRear", "cs_discBottom", "cs_discTop", "cs_intake"});
        touchSensorArray = new TouchSensorArray(opMode, new String[]{""});
    }

    //TODO ====PUBLIC METHODS====
    public boolean IsOverLine(){
        //Returns true if one of the bottom color sensors are over a line
        if(colorSensorArray.GetSensor(0).alpha() > lineAlpha || colorSensorArray.GetSensor(1).alpha() > lineAlpha) return true;
        else return false;
    }
    public int GetDiscStack(){
        //Returns 0, 1, or 2 depending on the stack height of discs in front of the disc color sensors
        if(colorSensorArray.GetSensor(3).alpha() > discAlpha) return 2;
        else if(colorSensorArray.GetSensor(2).alpha() > discAlpha) return 1;
        else return 0;
    }
    public boolean IsDiscInIntake(){
        //Returns true if a disc is in the intake of the robot
        if(colorSensorArray.GetSensor(4).alpha() > discAlpha) return true;
        else return false;
    }
}
