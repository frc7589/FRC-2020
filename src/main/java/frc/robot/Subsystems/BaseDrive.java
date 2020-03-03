package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;

public class BaseDrive extends SubsystemBase {

    // Singleton instance
    private BaseDrive() {
      InitSubsystem();}
    private static BaseDrive instance = null;
    public static BaseDrive GetInstance() {
      if (instance==null) instance = new BaseDrive();
      return instance;
    }

    private WPI_VictorSPX victor_l;
    private WPI_VictorSPX victor_l1;
    private WPI_TalonSRX talon_l;
    private WPI_VictorSPX victor_r;
    private WPI_VictorSPX victor_r1;
    private WPI_TalonSRX talon_r;

    private int speedChanel = 0;
    private double[] speedList = {.4, .6, .8, 1.};

    private SpeedControllerGroup leftGroup;
    private SpeedControllerGroup rightGroup;
    private DifferentialDrive baseDiffDrive;

    private PID_Motor leftPID;
    private PID_Motor rightPID;

    private NetworkTable vTable;
    private double gap = 0.0;
    private double dist = 0.0;


    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        victor_l = new WPI_VictorSPX(0);
        victor_l1 = new WPI_VictorSPX(2);
        //talon_l = new WPI_TalonSRX(0);
        victor_r = new WPI_VictorSPX(1);
        victor_r1 = new WPI_VictorSPX(3);
        //talon_r =  new WPI_TalonSRX(1);

        leftGroup = new SpeedControllerGroup(victor_l, victor_l1);
        rightGroup = new SpeedControllerGroup(victor_r, victor_r1);
        baseDiffDrive = new DifferentialDrive(leftGroup, rightGroup);
        //leftPID = new PID_Motor(talon_l, 0, 5, 0, 2, 0, 30);
        //rightPID = new PID_Motor(talon_r, 0, 5, 0, 2, 0, 30);
        SmartDashboard.putNumber("Base Speed", speedList[speedChanel]);
        
        vTable = NetworkTableInstance.getDefault().getTable("vision table");
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
      
      gap = vTable.getEntry("Error Gap").getDouble(0.0);
      dist = vTable.getEntry("distant").getDouble(0.0);

      if(gap >= 3.0){
        baseDiffDrive.tankDrive(0.5, -0.5);
      }
      else if(gap <= -3.0){
        baseDiffDrive.tankDrive(-0.5, 0.5);
      }
      else{
        baseDiffDrive.tankDrive(0.0, 0.0);
      }
    }

    public void SubsystemAutoInit() {
      talon_l.setSelectedSensorPosition(0);
      talon_r.setSelectedSensorPosition(0);
    }
}