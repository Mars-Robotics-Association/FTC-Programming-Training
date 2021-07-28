//:)

package org.firstinspires.ftc.teamcode.OpMode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "SimpleAutonomous" , group = "Competition")
@Disabled

public class SimpleAutonomousv2 extends LinearOpMode {
    //declare wheel motors
    private DcMotor FR;//front right wheel
    private DcMotor FL;//front left wheel
    private DcMotor RR;//back right wheel
    private DcMotor RL;//back left wheel

    @Override
    public void runOpMode() throws InterruptedException {
        //map the components to the robot configuration and set the power on wheels
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        RR = hardwareMap.dcMotor.get("RR");
        RL = hardwareMap.dcMotor.get("RL");

        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);

        waitForStart();
        DriveForward();
    }

    private void DriveForward() {
        long endRun = System.currentTimeMillis() + 8000;//define the time at which to stop as the current system time + 8 seconds

        while (endRun > System.currentTimeMillis()) {
            FR.setPower(.5);
            FL.setPower(-.5);
            RR.setPower(.5);
            RL.setPower(-.5);
            telemetry.addData("endRun Countdown", endRun - System.currentTimeMillis());
            telemetry.update();

        }

        //stop
        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);

    }
}