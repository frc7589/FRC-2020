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

<<<<<<< HEAD
        if (xcon.getXButtonPressed()) {
            toggleWheel = !toggleWheel;
            if (toggleWheel) intake.set(wheelSpd);
            else intake.set(0);
        }

        if(xcon.getAButton()){
            toggle = !toggle;
            if(toggle)intake.set(wheelSpd);
            else intake.set(0);
=======
        if (xcon.getXButton()) {
            intake.set(highSpd);
        }else if(xcon.getYButton()){
            intake.set(lowSpd);
>>>>>>> f3859d3f99289d166eb3837bfdd8b527e02a65a8
        }
        else intake.set(0);

    }
}