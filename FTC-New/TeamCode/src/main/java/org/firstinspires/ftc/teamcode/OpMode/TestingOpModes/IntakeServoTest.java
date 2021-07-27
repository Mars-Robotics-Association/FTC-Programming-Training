package org.firstinspires.ftc.teamcode.OpMode.TestingOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@Autonomous
@Disabled
public class IntakeServoTest extends LinearOpMode {
    private Servo servo;
    @Override
    public void runOpMode() {
        servo = hardwareMap.servo.get("SA");
        waitForStart();
        servo.setPosition(0);
        while (opModeIsActive()){
            telemetry.addData("Servo pos: ", servo.getPosition());
            telemetry.update();
        }
    }
}
