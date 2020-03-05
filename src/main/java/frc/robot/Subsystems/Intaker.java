package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intaker extends SubsystemBase {

    private WPI_VictorSPX intake;

    private double highSpd = .45;
    private double lowSpd = .2;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        intake = new WPI_VictorSPX(5);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getXButton()) {
            intake.set(highSpd);
        }else if(xcon.getYButton()){
            intake.set(lowSpd);
        }
        else intake.set(0);

    }
}