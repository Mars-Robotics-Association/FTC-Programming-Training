package org.firstinspires.ftc.teamcode.MechanicalControl;

import org.junit.jupiter.api.Test;

class DemobotShooterCalibrationTest {

    @Test
    public void main(){
        solveForSpeed(10, 2, 20);
    }

    @Test
    void solveForSpeed(double x, double y, double velocity) {
        //calculates angle to aim at for given data which should all be in meters
        double yDif = y;//uses formula from here: https://www.forrestthewoods.com/blog/solving_ballistic_trajectories/ from the "Firing Angle to Hit Stationary Target" section
        double bot = 9.8 * x;//Gx
        double top1 = (9.8*Math.pow(x,2) + 2*Math.pow(velocity,2)*yDif);//(Gx^2 + 2*S^2*y)
        double top2 = Math.sqrt(Math.pow(velocity, 4)-(9.8*top1));//SQRT(S^4-top1)

        double topHigh = Math.pow(velocity, 2)+top2;//shoot high
        double topLow = Math.pow(velocity, 2)-top2;//shoot low

        double full = Math.atan(topHigh/bot);
        System.out.println("Degrees is " + Math.toDegrees(full));

        //solves a trajectory equation for velocity
        double top = (6*(x*x)*(1/Math.pow(Math.cos(full), 2)));
        double bottom = 2*(x*Math.tan(full)-y);
        double vel = Math.sqrt(top/bottom);
        System.out.println("Velocity is " + vel);


    }
}