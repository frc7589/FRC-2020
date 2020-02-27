package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Transporter extends SubsystemBase{

    private WPI_VictorSPX transport;

    private boolean toggle = false;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        transport = new WPI_VictorSPX(3);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if(xcon.getAButton()){
            toggle = !toggle;
            if(toggle) transport.set(1.0);
            else transport.set(0.0);
        }
    }
}