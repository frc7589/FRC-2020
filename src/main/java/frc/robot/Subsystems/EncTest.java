package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.PID_Motor;

public class EncTest extends SubsystemBase {
    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;
    private WPI_VictorSPX trans;
    private PID_Motor lPID;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(2);
        rShooter = new WPI_TalonSRX(3);
        trans = new WPI_VictorSPX(3);
        lPID = new PID_Motor(lShooter, 0.02, 5, 0, 1, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        lPID.PrintValue();
        if (xcon.getAButton()) {
            trans.set(.44);
        }
        else {
            trans.set(0);
        }

        if (xcon.getTriggerAxis(Hand.kRight)>.1) {
            lShooter.set(xcon.getTriggerAxis(Hand.kRight));
            rShooter.set(xcon.getTriggerAxis(Hand.kRight));
        }
        else{
            lShooter.set(0);
        }
    }
}