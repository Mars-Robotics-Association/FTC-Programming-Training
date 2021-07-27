package org.firstinspires.ftc.teamcode.OpMode.TestingOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
@Disabled
public class testing extends LinearOpMode {
    Servo servo;

    public void runOpMode(){
        servo = hardwareMap.servo.get("S1");
        waitForStart();
        servo.setPosition(0.5);
    }
}
