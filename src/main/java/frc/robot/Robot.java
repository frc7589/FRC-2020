/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  protected class PID_Motor {
    private TalonSRX _talon;

    private int targetPositionRotations;
    private int targetVelocity;
    private int slotIdx;
    public PID_Motor(TalonSRX targetTalonSRX, double F_value, double P_value, double I_value,
     double D_value, int pidIdx, int timeout) {
      _talon = targetTalonSRX;
      set(F_value, P_value, I_value, D_value, pidIdx, timeout);
    }

    public void set(double F_value, double P_value, double I_value,
     double D_value, int pidIdx, int timeout) {
       slotIdx = pidIdx;
      _talon.configFactoryDefault();
		  _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
                                            slotIdx,timeout);

		  _talon.setSensorPhase(true);

		  _talon.setInverted(false);

		  _talon.configNominalOutputForward(0, timeout);
		  _talon.configNominalOutputReverse(0, timeout);
		  _talon.configPeakOutputForward(1, timeout);
		  _talon.configPeakOutputReverse(-1, timeout);

		  _talon.configAllowableClosedloopError(0, slotIdx, timeout);
      _talon.config_kF(slotIdx, F_value, timeout);
      _talon.config_kP(slotIdx, P_value, timeout);
      _talon.config_kI(slotIdx, I_value, timeout);
      _talon.config_kD(slotIdx, D_value, timeout);

      int absolutePosition = _talon.getSensorCollection().getPulseWidthPosition();
      absolutePosition &= 0xFFF;
      absolutePosition *= -1;
      _talon.setSelectedSensorPosition(absolutePosition, slotIdx, timeout);
     }
    // Write commonly used function below or simply use PID_Motor._talon.WHATEVERTHEYHAVE()
    int printLoop = 0;
    public void PrintValue() {
      StringBuilder _sb = new StringBuilder();
      double motorOutput = _talon.getMotorOutputPercent();
      _sb.append("\tout:");
		  /* Cast to int to remove decimal places */
		  _sb.append((int) (motorOutput * 100));
		  _sb.append("%");	// Percent

		  _sb.append("\tpos:");
		  _sb.append(_talon.getSelectedSensorPosition(0));
      _sb.append("u"); 	// Native units
      if (_talon.getControlMode() == ControlMode.Position) {
        /* ppend more signals to print when in speed mode. */
        _sb.append("\terr:");
        _sb.append(_talon.getClosedLoopError(0));
        _sb.append("u");	// Native Units
  
        _sb.append("\ttrg:");
        _sb.append(targetPositionRotations);
        _sb.append("u");	/// Native Units
      }
      else if (_talon.getControlMode() == ControlMode.Velocity) {
      _sb.append("\t err:");
			_sb.append(_talon.getClosedLoopError(slotIdx));
			_sb.append("\t trg:");
			_sb.append(targetVelocity);
      }
      printLoop++;
      if (printLoop>=10) {
        printLoop = 0;
        System.out.println(_sb.toString());
      }
      _sb.setLength(0);
    }
    public void PosControl(int targetPos) {
      targetPositionRotations = targetPos;
      _talon.set(ControlMode.Position, targetPositionRotations);
    }
    public void VelControl(int targetVel) {
      targetVelocity = targetVel;
      _talon.set(ControlMode.Velocity, targetVelocity);
    }
  }
  //#region Inputs
  private XboxController controller = new XboxController(0);
  private double baseLeftSpeed;
  private double baseRightSpeed;
  private boolean speedUp;
  private boolean speedDown;
  private boolean armsUp;
  private boolean armsDown;
  private boolean aButton;
  private boolean bButton;
  //#endregion

  //#region Base Diff Drive
  private WPI_VictorSPX lf_motor = new WPI_VictorSPX(0);
  private WPI_VictorSPX lb_motor = new WPI_VictorSPX(2);
  private WPI_VictorSPX rf_motor = new WPI_VictorSPX(1);
  private WPI_VictorSPX rb_motor = new WPI_VictorSPX(3);
  private SpeedControllerGroup l_speedCon = new SpeedControllerGroup(lf_motor, lb_motor);
  private SpeedControllerGroup r_speedCon = new SpeedControllerGroup(rf_motor, rb_motor);
  private DifferentialDrive baseDiffDrive = new DifferentialDrive(l_speedCon, r_speedCon);

  private int speedChanel = 0;
  private double[] speedList = {0.5, 0.6, 0.7, 0.8};

  private int baseReverse = 1;
  //#endregion

  //#region shooting
  private WPI_VictorSPX Lshooter = new WPI_VictorSPX(4);
  private WPI_VictorSPX Rshooter = new WPI_VictorSPX(6);
  private DifferentialDrive shooterDiffDrive = new DifferentialDrive(Lshooter, Rshooter);

  private double fire_speed = 0.5;

  //#endregion

  //#region Climbing
  private WPI_TalonSRX armLeft = new WPI_TalonSRX(0);
  private PID_Motor armLeftPID = new PID_Motor(armLeft, 0, 10, 0, 2, 0, 30); // example value, needs tuning
  private WPI_TalonSRX armRight = new WPI_TalonSRX(1);
  private PID_Motor armRightPID = new PID_Motor(armRight, 0, 10, 0, 2, 0, 30); // example value, needs tuning
  //private WPI_VictorSPX armLeft = new WPI_VictorSPX(1);
  //private WPI_VictorSPX armRight = new WPI_VictorSPX(2);
  private SpeedControllerGroup armsCon = new SpeedControllerGroup(armLeft, armRight);
  private WPI_VictorSPX testmotor = new WPI_VictorSPX(9);

  private double armsSpeed = 0.5;
  //#endregion

  //#region collector
  private Servo frontServo = new Servo(9);
  //#endregion

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    frontServo.set(0.02);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  private void getInput() {
    baseLeftSpeed = controller.getY(Hand.kLeft);
    baseRightSpeed = controller.getY(Hand.kRight);
    speedUp = controller.getBumperPressed(Hand.kRight);
    speedDown = controller.getBumperPressed(Hand.kLeft);
    aButton = controller.getAButton();
    bButton = controller.getBButton();
  }

  @Override
  public void teleopPeriodic() {
    getInput();
     //#region Base Movement
    if (Math.abs(baseLeftSpeed)>0.1 | Math.abs(baseRightSpeed)>0.1) {
      baseDiffDrive.tankDrive(baseLeftSpeed*speedList[speedChanel]*baseReverse, 
                              baseRightSpeed*speedList[speedChanel]*baseReverse);
    }
    else {
      baseDiffDrive.tankDrive(0, 0);
    }
    if (speedUp) {
      speedChanel++;
      if (speedChanel>=speedList.length) speedChanel = speedList.length;
    }
    if (speedDown) {
      speedChanel--;
      if (speedChanel<=-1) speedChanel = 0;
    }
    /* PID class example
    armLeftPID.PrintValue();
    if (controller.getTriggerAxis(Hand.kRight) >= 0.1) {
      armLeftPID.VelControl((int)(100*controller.getTriggerAxis(Hand.kRight)));
    }
    else {
      armLeftPID.VelControl(0);
    }
    if (controller.getBButton()) {
      armLeftPID.PosControl(0);
    }*/

  
    //#endregion
  }

  @Override
  public void testPeriodic() {
    getInput();
    
    
  }
}
