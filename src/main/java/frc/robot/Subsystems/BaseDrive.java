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
import frc.robot.Constants.MotorSpeeds;

public class BaseDrive extends SubsystemBase {

  // Singleton instance
  private BaseDrive() {
    InitSubsystem();
  }

  private static BaseDrive instance = null;

  public static BaseDrive GetInstance() {
    if (instance == null)
      instance = new BaseDrive();
    return instance;
  }

  private WPI_VictorSPX victor_l;
  private WPI_TalonSRX talon_l;
  private WPI_VictorSPX victor_r;
  private WPI_TalonSRX talon_r;

  private int speedChanel = 0;
  private double[] speedList = {.4, .6, .8, 1}; 

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
        talon_l = new WPI_TalonSRX(0);
        victor_r = new WPI_VictorSPX(1);
        talon_r =  new WPI_TalonSRX(1);

        leftGroup = new SpeedControllerGroup(victor_l, talon_l);
        rightGroup = new SpeedControllerGroup(victor_r, talon_r);
        baseDiffDrive = new DifferentialDrive(leftGroup, rightGroup);
        leftPID = new PID_Motor(talon_l, .0015, 1., .0012, 2, 0, 30);
        rightPID = new PID_Motor(talon_r, .0015, 1.2, .0015, 2, 0, 30);
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
        leftPID.PrintValue();
    }

    @Override
    public void SubsystemAutoInit() {
      talon_l.setSelectedSensorPosition(0);
      talon_r.setSelectedSensorPosition(0);
      victor_l.follow(talon_l);
      victor_r.follow(talon_r);
      pathIdx = 0;
      actionDone = false;
    }

    @Override
    public void SubsystemAutoPeriodic() {
      super.SubsystemAutoPeriodic();
      /*
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
      */
      switch (pathIdx) {
        case 0:
          BaseDriveStraightMeter(2);
          CheckNextAction();
          break;
        case 1:
          BaseDriveTurn(90);
          CheckNextAction();
          break;
        case 2:
          BaseDriveStraightMeter(1);
          CheckNextAction();
          break;
        case 3:
          BaseDriveTurn(-90);
          CheckNextAction();
          break;
        default:
        System.out.println("Bruh");
        break;
      }
    }

    private int pathIdx;
    private boolean actionDone;
    int dir(int pos, int tar) {
      return (pos-tar)/Math.abs(pos-tar);
    }
    void CheckNextAction() {
      if (actionDone) {
        actionDone=!actionDone;
        pathIdx++;
        talon_l.setSelectedSensorPosition(0);
        talon_r.setSelectedSensorPosition(0);
      }
    }
    /**
     * Drive a not-so-precise distance of meters.
     * Positive for forward, negative for backward.
     * Returns true when the errors of both side of the
     * robot are below 200.
     */
    public void BaseDriveStraightMeter(double targetMeter) {
      // turn meters to units
      int target = (int)(targetMeter*16400);
      // change speed around error
      if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>12000) {
        leftPID.VelControl(1500*dir(target,talon_l.getSelectedSensorPosition(0)));
        rightPID.VelControl(1500*dir(-target,talon_r.getSelectedSensorPosition(0)));
      }
      else if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>4000){
        leftPID.VelControl(900*dir(target,talon_l.getSelectedSensorPosition(0)));
        rightPID.VelControl(900*dir(-target,talon_r.getSelectedSensorPosition(0)));
      }
      else if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>400){
        leftPID.VelControl(600*dir(target,talon_l.getSelectedSensorPosition(0)));
        rightPID.VelControl(600*dir(-target,talon_r.getSelectedSensorPosition(0)));
      }
      else{
        leftPID.PosControl(target);
        rightPID.PosControl(-target);
      }
      if (Math.abs(target-talon_l.getSelectedSensorPosition(0))<200&&Math.abs(target+talon_r.getSelectedSensorPosition(0))<200) {
        actionDone = true;
      }
      System.out.println(target-talon_l.getSelectedSensorPosition(0));
    } 

    /**
     * Turn a not-so-precise amount of degrees.
     * Positive for right turn, negative for left turn.
     * Done when the errors of both side of the
     * robot are below 200.
     */
    public void BaseDriveTurn(int targetDegree) {
      int target = targetDegree*90;
      if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>12000) {
        leftPID.VelControl(1200*dir(target,talon_l.getSelectedSensorPosition(0)));
        rightPID.VelControl(1200*dir(target,talon_l.getSelectedSensorPosition(0)));
      }
      else if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>1000) {
        leftPID.VelControl(600*dir(target,talon_l.getSelectedSensorPosition(0)));
        rightPID.VelControl(600*dir(target,talon_l.getSelectedSensorPosition(0)));
      }
      else if (Math.abs(target-talon_l.getSelectedSensorPosition(0))>200) {
        leftPID.PosControl(target);
        rightPID.PosControl(target);
      }
      if (Math.abs(target-talon_l.getSelectedSensorPosition(0))<100&&Math.abs(target-talon_r.getSelectedSensorPosition(0))<100) {
        actionDone = true;
      }
      System.out.println(target-talon_l.getSelectedSensorPosition(0));
    }

    public void BaseRawDrive(int driveSpeed) {
      leftPID.VelControl(driveSpeed);
      rightPID.VelControl(-driveSpeed);
    }
    
    public void BaseRawTurn(int turnSpeed) {
      leftPID.VelControl(turnSpeed);
      rightPID.VelControl(turnSpeed);
    }
  }