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
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //#region Inputs
  private XboxController controller = new XboxController(0);
  private double baseLeftSpeed;
  private double baseRightSpeed;
  private boolean speedUp;
  private boolean speedDown;
  private boolean armsUp;
  private boolean armsDown;
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
  private double[] speedList = {0.4, 0.7, 1.0};

  private int baseReverse = 1;
  //#endregion

  //#region Climbing
  private WPI_TalonSRX armLeft = new WPI_TalonSRX(0);
  private WPI_TalonSRX armRight = new WPI_TalonSRX(1);
  //private WPI_VictorSPX armLeft = new WPI_VictorSPX(1);
  //private WPI_VictorSPX armRight = new WPI_VictorSPX(2);
  private SpeedControllerGroup armsCon = new SpeedControllerGroup(armLeft, armRight);

  private double armsSpeed = 0.5;
  //#endregion

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
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
    //#endregion
  }

  @Override
  public void testPeriodic() {
  }
}
