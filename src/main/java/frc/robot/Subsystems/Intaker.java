package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Controller;

public class Intaker extends SubsystemBase {

    private Servo arm;
    private WPI_VictorSPX wheel;

    private boolean toggleArmpos = false;
    private boolean toggleWheel = false;

    private double armDef = .08;
    private double armOut = .5;
    private double wheelSpd = .3;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        arm = new Servo(9);
        wheel = new WPI_VictorSPX(0);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (Controller.GetInstance().GetDPadUpPressed()) {
            toggleArmpos = !toggleArmpos;
            if (toggleArmpos) arm.set(armDef);
            else arm.set(armOut);
        }

        if (xcon.getXButtonPressed()) {
            toggleWheel = !toggleWheel;
            if (toggleWheel) wheel.set(wheelSpd);
            else wheel.set(0);
        }
    }
}