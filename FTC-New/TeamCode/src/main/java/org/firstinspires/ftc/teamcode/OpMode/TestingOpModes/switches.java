package org.firstinspires.ftc.teamcode.OpMode.TestingOpModes;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

//@Config
@Autonomous(name = "SWITCHTEST")
@Disabled
public class switches extends LinearOpMode {
    TouchSensor switch1;
    TouchSensor switch2;
    TouchSensor switch3;


    public void runOpMode(){
        switch1 = hardwareMap.touchSensor.get("touch1");
        switch2 = hardwareMap.touchSensor.get("touch2");
        switch3 = hardwareMap.touchSensor.get("touch3");

        double var1 = switch1.getValue();
        double var2 = switch2.getValue();
        double var3 = switch3.getValue();

        if(var1 == -1 || var2 == -1 || var3 == -1){
            //don't do anything
        }

        else if (var1 == 0 && var2 == 0 && var3 == 0 ) {
            //first
        }
        else if (var1 == 1 && var2 == 0 && var3 == 0 ) {
            //second
        }       


    }
}
