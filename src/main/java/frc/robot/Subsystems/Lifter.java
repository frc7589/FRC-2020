package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Lifter extends SubsystemBase {

    private WPI_VictorSPX lineWinder;
    private WPI_VictorSPX arm;

    private boolean toggleLifting = false;

    private double armValtage = .8;
    private double windSpd = -.5;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lineWinder = new WPI_VictorSPX(4);
        arm = new WPI_VictorSPX(3);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getTriggerAxis(Hand.kRight)>.5) {
            lineWinder.set(windSpd*xcon.getTriggerAxis(Hand.kRight));
        }
        else if (xcon.getTriggerAxis(Hand.kLeft)>.5) {
            lineWinder.set(-windSpd*xcon.getTriggerAxis(Hand.kLeft));
        }
        else {
            lineWinder.set(0);
        }

        if (xcon.getBackButtonPressed()) {
            toggleLifting = !toggleLifting;

            if (toggleLifting) {
                arm.set(-armValtage);
            }
            else {
                arm.set(0); 
            }
        }
    }
}