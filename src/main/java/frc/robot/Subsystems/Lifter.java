package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Lifter extends SubsystemBase {

    private WPI_VictorSPX elevator;
    private WPI_VictorSPX lift1;
    private WPI_VictorSPX lift2;

    private SpeedControllerGroup lifter;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        elevator = new WPI_VictorSPX(4);
        lift1 = new WPI_VictorSPX(5);
        lift2 = new WPI_VictorSPX(6);

        lifter = new SpeedControllerGroup(lift1, lift2);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if(xcon.getPOV() == 0){
            elevator.set(1.0);
        }
        else if(xcon.getPOV() == 180){
            elevator.set(-1.0);
        }
        else{
            elevator.set(0.0);
        }

        if(xcon.getBackButton()){
            lifter.set(1.0);
        }
        else{
            lifter.set(0.0);
        }
    }
}