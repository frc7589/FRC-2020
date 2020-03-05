package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.PID_Motor;

public class EncTest extends SubsystemBase {

    // Singleton instance
    private EncTest() {
        InitSubsystem();}
      private static EncTest instance = null;
      public static EncTest GetInstance() {
        if (instance==null) instance = new EncTest();
        return instance;
      }
    
    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;
    private WPI_VictorSPX trans;
    private PID_Motor lPID;
    private PID_Motor rPID;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(2);
        rShooter = new WPI_TalonSRX(3);
        trans = new WPI_VictorSPX(3);
        lPID = new PID_Motor(lShooter, 0.02, 5, 0, 1, 0, 30);
        rPID = new PID_Motor(rShooter, 0.02, 5, 0, 1, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        rPID.PrintValue();
        if (xcon.getAButton()) {
            trans.set(.44);
        }
        else {
            trans.set(0);
        }

        if (xcon.getTriggerAxis(Hand.kRight)>.1 || xcon.getTriggerAxis(Hand.kLeft)>.1) {
            lShooter.set(xcon.getTriggerAxis(Hand.kLeft));
            rShooter.set(xcon.getTriggerAxis(Hand.kRight));
        }
        else{
            lShooter.set(0);
            rShooter.set(0);
        }
    }
}