package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Controller;

public abstract class SubsystemBase {

    protected XboxController xcon;

    public void InitSubsystem() {
        xcon = Controller.GetInstance().xcon;
    }

    public void SubsystemTeleopPeriodic() {}

    public void SubsystemAutoInit() {}

    public void SubsystemAutoPeriodic() {}

    public void SubsystemTestInit() {}

    public void SubsystemTestPeriodic() {}
}