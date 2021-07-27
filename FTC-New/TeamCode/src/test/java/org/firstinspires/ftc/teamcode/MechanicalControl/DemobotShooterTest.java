package org.firstinspires.ftc.teamcode.MechanicalControl;

import org.junit.jupiter.api.Test;

class DemobotShooterTest {

    @Test
    void setTrajectory() {
        double targetY = 5;
        double shooterY = 0.5;
        double targetDist = 15;
        double speed = 20;

        double yDif = targetY - shooterY;
        double bot = 9.8 * targetDist;
        double top1 = (9.8*Math.pow(targetDist,2) + 2*Math.pow(speed,2)*yDif);
        double top2 = Math.sqrt(Math.pow(speed, 4)-(9.8*top1));

        double topHigh = Math.pow(speed, 2)+top2;//shoot high
        double topLow = Math.pow(speed, 2)-top2;//shoot low

        double full = Math.atan(topHigh/bot);

        System.out.println("yDif: " + yDif);
        System.out.println("bot: " + bot);
        System.out.println("top1: " + top1);
        System.out.println("top2: " + top2);
        System.out.println("topHigh: " + topHigh);
        System.out.println("full: " + Math.toDegrees(full));
    }

    @Test
    void aim() {
    }

    @Test
    void spinUp() {
    }

    @Test
    void fire() {
    }
}