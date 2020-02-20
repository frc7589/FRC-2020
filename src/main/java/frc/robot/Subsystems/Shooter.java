package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.PID_Motor;

public class Shooter extends SubsystemBase {
    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;
    private WPI_VictorSPX transporter;
    private boolean toggleTransporting = false;

    private PID_Motor lPID;
    private PID_Motor rPID;

    private double transportSpd = .35;
    private double shootSpd = .7;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(3);
        rShooter = new WPI_TalonSRX(4);
        transporter = new WPI_VictorSPX(2);
        lPID = new PID_Motor(lShooter, 0.02, 5, 0, 1, 0, 30);
        rPID = new PID_Motor(rShooter, 0.02, 5, 0, 1, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getAButtonPressed()) {
            toggleTransporting = !toggleTransporting;
            if (toggleTransporting) {
                transporter.set(transportSpd);
            }
            else{
                transporter.set(0);
            }
        }

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
        lPID.PrintValue();
        rPID.PrintValue();
        System.out.println("next");
    }

    public void StartShooting() {
        lShooter.set(-shootSpd);
        rShooter.set(-shootSpd);
    }

    public void StopShooting() {
        lShooter.set(0);
        rShooter.set(0);
    }
}