package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.PID_Motor;

public class Shooter extends SubsystemBase {
    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;

    private PID_Motor lPID;
    private PID_Motor rPID;

    private double[] shootSpd = {.3,.7};
    private int speedIdx = 0;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(2);
        rShooter = new WPI_TalonSRX(3);
        lPID = new PID_Motor(lShooter, 0.02, 5, 0, 1, 0, 30);
        rPID = new PID_Motor(rShooter, 0.02, 5, 0, 1, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getBButton()) {
            /*
            toggleShooting = !toggleShooting;
            if (toggleShooting) {
                StartShooting();
            }
            else {
                StopShooting();
            }
            */
            StartShooting();
        }
        else StopShooting();

        if(xcon.getYButton()){
            speedIdx = (speedIdx+1)%2;
        }
        lPID.PrintValue();
        rPID.PrintValue();
        System.out.println("next");
    }

    public void StartShooting() {
        lShooter.set(-shootSpd[speedIdx]);
        rShooter.set(-shootSpd[speedIdx]);
    }

    public void StopShooting() {
        lShooter.set(0);
        rShooter.set(0);
    }
}


