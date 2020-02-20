package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class BaseDrive extends SubsystemBase {

    private WPI_TalonSRX talon_l;
    private WPI_VictorSPX victor_l;
    private WPI_TalonSRX talon_r;
    private WPI_VictorSPX victor_r;

    private int speedChanel = 0;
    private double[] speedList = {.4, .7, 1.};

    private SpeedControllerGroup leftGroup = new SpeedControllerGroup(talon_l, victor_l);
    private SpeedControllerGroup rightGroup = new SpeedControllerGroup(talon_r, victor_r);
    private DifferentialDrive baseDiffDrive = new DifferentialDrive(leftGroup, rightGroup);

    private PID_Motor leftPID;
    private PID_Motor rightPID;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        talon_l = new WPI_TalonSRX(0);
        victor_l = new WPI_VictorSPX(5);
        talon_r = new WPI_TalonSRX(1);
        victor_r =  new WPI_VictorSPX(6);

        leftGroup = new SpeedControllerGroup(talon_l, victor_l);
        rightGroup = new SpeedControllerGroup(talon_r, victor_r);
        baseDiffDrive = new DifferentialDrive(leftGroup, rightGroup);
        leftPID = new PID_Motor(talon_l, 0, 5, 0, 2, 0, 30);
        rightPID = new PID_Motor(talon_r, 0, 5, 0, 2, 0, 30);
        SmartDashboard.putNumber("Base Speed", speedList[speedChanel]);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        SmartDashboard.putNumber("Base Speed", speedList[speedChanel]);
        if (Math.abs(xcon.getY(Hand.kLeft)) > .1 | Math.abs(xcon.getY(Hand.kRight)) > .1) {
            baseDiffDrive.tankDrive(xcon.getY(Hand.kLeft)*-speedList[speedChanel], 
                                    xcon.getY(Hand.kRight)*-speedList[speedChanel]);
        }
        else {
          baseDiffDrive.tankDrive(0, 0);
        }
        if (xcon.getBumperPressed(Hand.kRight)) {
          speedChanel++;
          if (speedChanel>speedList.length-1) speedChanel = speedList.length-1;
        }
        else if (xcon.getBumperPressed(Hand.kLeft)) {
          speedChanel--;
          if (speedChanel<0) speedChanel = 0;
        }
    }

    @Override
    public void SubsystemAutoPeriodic() {
      super.SubsystemAutoPeriodic();
    }
}