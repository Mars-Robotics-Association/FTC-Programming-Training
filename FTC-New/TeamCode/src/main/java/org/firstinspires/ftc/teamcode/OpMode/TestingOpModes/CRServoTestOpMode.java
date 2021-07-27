package org.firstinspires.ftc.teamcode.OpMode.TestingOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ServoController;

@Autonomous
@Disabled
public class CRServoTestOpMode extends LinearOpMode {
    private ServoController servoController;

    @Override
    public void runOpMode() {
        servoController = hardwareMap.crservo.get("SA").getController();
        waitForStart();
        servoController.setServoPosition(0,1);
        while (opModeIsActive()){
            telemetry.addData("Servo pos: ", servoController.getServoPosition(0));
        }
    }
}
