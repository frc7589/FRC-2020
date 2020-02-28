package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intaker extends SubsystemBase {

    private WPI_VictorSPX intake;

    private boolean toggleWheel = false;
    private boolean toggle = false;

    private double wheelSpd = -0.3;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        intake = new WPI_VictorSPX(2);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getXButtonPressed()) {
            toggleWheel = !toggleWheel;
            if (toggleWheel) intake.set(wheelSpd);
            else intake.set(0);
        }

        if(xcon.getAButton()){
            toggle = !toggle;
            if(toggle)intake.set(wheelSpd);
            else intake.set(0);
        }

    }
}