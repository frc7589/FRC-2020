package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.MotionMagicMotor;
import frc.robot.PID_Motor;

public class Shooter extends SubsystemBase {

    // Singleton instance
    private Shooter() {}
      private static Shooter instance = null;
      public static Shooter GetInstance() {
        if (instance==null) instance = new Shooter();
        return instance;
    }

    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;

    private PID_Motor lPID;
    private PID_Motor rPID;
    private double[] shootSpd = {.087, .85};
    private int[] shootSpdPID = {12, 32 };
    private int speedIdx = 0;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(2);
        rShooter = new WPI_TalonSRX(3);
                                      //f  P  I  D
        lPID = new PID_Motor(lShooter, 20.5, 10, 0.2, 10, 0, 30);
        rPID = new PID_Motor(rShooter, 20.5, 15, 0.1, 0, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getAButton()) {
            speedIdx = 0;
            StartShooting();
        }
        else if (xcon.getBButton()) { 
            speedIdx = 1;
            StartShooting();
        }
        else StopShooting();
        rPID.PrintValue();
    }

    public void StartShooting() {
        //lShooter.set(-shootSpd[speedIdx]);
        //rShooter.set(-shootSpd[speedIdx]);

        lPID.VelControl(-shootSpdPID[speedIdx]);
        rPID.VelControl(-shootSpdPID[speedIdx]);
    }

    public void StopShooting() {
        lShooter.set(0);
        rShooter.set(0);
    }
}


