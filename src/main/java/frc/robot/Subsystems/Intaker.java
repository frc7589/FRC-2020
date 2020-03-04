package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intaker extends SubsystemBase {

    // Singleton instance
    private Intaker() {
        InitSubsystem();}
      private static Intaker instance = null;
      public static Intaker GetInstance() {
        if (instance==null) instance = new Intaker();
        return instance;
    }

    private WPI_VictorSPX intake;

    private double Spd1 = -.4;
    private double Spd2 = .7;
    private double Spd3 = -.7;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        intake = new WPI_VictorSPX(4);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        if (xcon.getXButton()&&xcon.getYButton()) {
            intake.set(Spd3);
        }
        else if (xcon.getXButton()) {
            intake.set(Spd1);
        }else if(xcon.getYButton()){
            intake.set(Spd2);
        }
        else intake.set(0);

    }
}