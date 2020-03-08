package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Lifter extends SubsystemBase {

    // Singleton instance
    private Lifter() {
        InitSubsystem();}
      private static Lifter instance = null;
      public static Lifter GetInstance() {
        if (instance==null) instance = new Lifter();
        return instance;
    }

    private WPI_VictorSPX elevator;
    private WPI_VictorSPX lift1;
    private WPI_VictorSPX lift2;

    private SpeedControllerGroup lifter;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        elevator = new WPI_VictorSPX(6); 
        lift1 = new WPI_VictorSPX(3);
        lift2 = new WPI_VictorSPX(5);

        lifter = new SpeedControllerGroup(lift1, lift2);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if(xcon.getPOV() == 0){
            elevator.set(.6);
        }
        else if(xcon.getPOV() == 180){
            elevator.set(-.6);
        }
        else{
            elevator.set(0.0);
        }

        if(xcon.getBackButton()){
            lifter.set(0.7589);
        }
        else{
            lifter.set(0.0);
        }
    }

    @Override
    public void SubsystemTestPeriodic() {
        super.SubsystemTestPeriodic();
        if(xcon.getBackButton()){
            lifter.set(-0.7589);
        }
        else{
            lifter.set(0.0);
        }
    }
}