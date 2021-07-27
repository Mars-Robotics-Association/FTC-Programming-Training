
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;


@TeleOp(name = "Sensor: MR IR Seeker", group = "Sensor")
@Disabled
public class SensorMRIrSeeker extends LinearOpMode {

  @Override
  public void runOpMode() {

    IrSeekerSensor irSeeker;

    irSeeker = hardwareMap.get(IrSeekerSensor.class, "sensor_ir");

    waitForStart();

    while (opModeIsActive())  {

      if (irSeeker.signalDetected())
      {
        telemetry.addData("Angle",    irSeeker.getAngle());
        telemetry.addData("Strength", irSeeker.getStrength());
      }
      else
      {
        telemetry.addData("Seeker", "Signal Lost");
      }

      telemetry.update();
    }
  }
}
