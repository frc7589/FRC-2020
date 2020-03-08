/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Subsystems.*;

public class Robot extends TimedRobot {

  BaseDrive baseDrive;
  Intaker intaker;
  Shooter shooter;
  Lifter lifter;
  NWTable nwTable;
  Spinner spinner;
  EncTest encTest;

  @Override
  public void robotInit() {
    //baseDrive = BaseDrive.GetInstance();
    intaker = Intaker.GetInstance();
    shooter = Shooter.GetInstance(); 
    lifter = Lifter.GetInstance();
    nwTable = NWTable.GetInstance();
    spinner = Spinner.GetInstance();
    encTest = EncTest.GetInstance();
    shooter.InitSubsystem();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    baseDrive.SubsystemAutoPeriodic();
  }

  @Override
  public void teleopInit() { 
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    baseDrive.SubsystemTeleopPeriodic();
    shooter.SubsystemTeleopPeriodic();
    lifter.SubsystemTeleopPeriodic();
    intaker.SubsystemTeleopPeriodic();
    nwTable.SubsystemTeleopPeriodic();
    //spinner.SubsystemTeleopPeriodic();
    //tester.SubsystemTeleopPeriodic();
    //encTest.SubsystemTeleopPeriodic();
  }
}
