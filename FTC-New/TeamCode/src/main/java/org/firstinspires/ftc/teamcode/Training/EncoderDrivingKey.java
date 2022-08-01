/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Training;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="EncoderDrivingKey", group="Pushbot")
@Disabled
public class EncoderDrivingKey extends LinearOpMode {


    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    private DcMotor FR;//front right wheel
    private DcMotor FL;//front left wheel
    private DcMotor RR;//back right wheel
    private DcMotor RL;//back left wheel

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        RR = hardwareMap.dcMotor.get("RR");
        RL = hardwareMap.dcMotor.get("RL");
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                FR.getCurrentPosition(),
                FL.getCurrentPosition(),
                RR.getCurrentPosition(),
                RL.getCurrentPosition() );
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)


        encoderDrive(DRIVE_SPEED,  48,  48);  // S1: Forward 48 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24);  // S3: Reverse 24 Inches with 4 Sec timeout



        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed, double leftInches, double rightInches) {
        int FRTarget;
        int FLTarget;
        int RRTarget;
        int RLTarget;

        // Determine new target position, and pass to motor controller
        FRTarget = FR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        FLTarget = FL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        RRTarget = RR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        RLTarget = RL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);

        FR.setTargetPosition(FRTarget);
        FL.setTargetPosition(FLTarget);
        RR.setTargetPosition(RRTarget);
        RL.setTargetPosition(RLTarget);

            // Turn On RUN_TO_POSITION
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
        FR.setPower(Math.abs(speed));
        FL.setPower(Math.abs(speed));
        RR.setPower(Math.abs(speed));
        RL.setPower(Math.abs(speed));

            // keep looping while we are still active and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.

            while (opModeIsActive() &&
                    (FR.isBusy() && FL.isBusy() && RR.isBusy() && RL.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", FRTarget,  FLTarget, RRTarget, RLTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        FR.getCurrentPosition(),
                        FL.getCurrentPosition(),
                        RR.getCurrentPosition(),
                        RL.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
        FR.setPower(0);
        FL.setPower(0);
        RR.setPower(0);
        RL.setPower(0);


            //  sleep(250);   // optional pause after each move

    }
}
