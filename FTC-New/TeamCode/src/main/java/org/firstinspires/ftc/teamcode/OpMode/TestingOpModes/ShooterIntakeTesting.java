package org.firstinspires.ftc.teamcode.OpMode.TestingOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ShooterIntakeTesting")
@Disabled
public class ShooterIntakeTesting extends OpMode
{
    private Servo servo;

    @Override
    public void init() {
        servo = hardwareMap.servo.get("IM");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            servo.setPosition(0.5);
        }
        else servo.setPosition(0);
    }
}
