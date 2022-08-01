package org.firstinspires.ftc.teamcode.SummerWorkshop22;

import com.qualcomm.robotcore.hardware.DcMotor;

public class SimpleMethods {



    public static void moveWheels(DcMotor FR, DcMotor FL, DcMotor RR, DcMotor RL, int dist) {
        FR.setTargetPosition(FR.getCurrentPosition()+dist);
        FL.setTargetPosition(FL.getCurrentPosition()+dist);
        RR.setTargetPosition(RR.getCurrentPosition()+dist);
        RL.setTargetPosition(RL.getCurrentPosition()+dist);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setPower(1);
        FL.setPower(1);
        RL.setPower(1);
        RR.setPower(1);
        while(FR.isBusy()||FL.isBusy()|| RR.isBusy()||RL.isBusy()){}
        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);
    }

    public static void turnLeft(DcMotor FR, DcMotor FL, DcMotor RR, DcMotor RL, int dist){
        FR.setTargetPosition(FR.getCurrentPosition()+dist);
        FL.setTargetPosition(FL.getCurrentPosition()-dist);
        RR.setTargetPosition(RR.getCurrentPosition()+dist);
        RL.setTargetPosition(RL.getCurrentPosition()-dist);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setPower(1);
        FL.setPower(1);
        RL.setPower(1);
        RR.setPower(1);
        while(FR.isBusy()||FL.isBusy()|| RR.isBusy()||RL.isBusy()){}
        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);
    }

    public static void driveInches(DcMotor FR, DcMotor FL, DcMotor RR, DcMotor RL, int inches)
    {
        //you will write this
    }
}

