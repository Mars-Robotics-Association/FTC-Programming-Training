//:)

package org.firstinspires.ftc.teamcode.SummerWorkshop22;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//The name that you use in the line below will show up on the phone for you to select to run on the robot
@Autonomous(name = "LukeWantsToTestAThing" , group = "Competition")

public class SimpleAutonomous extends LinearOpMode {
    //declare wheel motors.  The names you give your motors must match the names you give them in the configuration file that is stored on the phone.
    private DcMotor FR;//front right wheel
    private DcMotor FL;//front left wheel
    private DcMotor RR;//back right wheel
    private DcMotor RL;//back left wheel

    @Override
    public void runOpMode() throws InterruptedException {
        //map the components to the robot configuration
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        RR = hardwareMap.dcMotor.get("RR");
        RL = hardwareMap.dcMotor.get("RL");

        //set the power on the motors/wheels to 0 so the robot doesn't start wandering right away.
        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);

        waitForStart();//makes your robot wait until you press the arrow on the phone to start moving forward
        SimpleMethods.moveWheels(FR,FL,RR,RL,100);
        SimpleMethods.turnLeft(FR,FL,RR,RL,100);
        //DriveForward();//calls the DriveForward method that you will code below
        //this might break something
    }

    private void DriveForward() {
        //PUT YOUR CODE TO MOVE THE ROBOT FORWARD HERE (BETWEEN THE BRACKETS BEFORE AND AFTER THIS LINE)
        //This is a new comment
    }

    //stop the robot after it moves forward the correct number of milliseconds
    //PUT YOUR CODE TO STOP THE ROBOT HERE(BEFORE THE BRACKET BELOW)

}
