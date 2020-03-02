/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Subsystems.BaseDrive;
import frc.robot.Subsystems.EncTest;
import frc.robot.Subsystems.Intaker;
import frc.robot.Subsystems.Lifter;
import frc.robot.Subsystems.NWTable;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.Spinner;

public class Robot extends TimedRobot {

  BaseDrive baseDrive = BaseDrive.GetInstance();
  Intaker intaker = Intaker.GetInstance();
  Shooter shooter = Shooter.GetInstance(); 
  Lifter lifter = Lifter.GetInstance();
  NWTable nwTable = NWTable.GetInstance();
  Spinner spinner = Spinner.GetInstance();
  EncTest encTest = EncTest.GetInstance();

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
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
    //nwTable.SubsystemTeleopPeriodic();
    //spinner.SubsystemTeleopPeriodic();
    //tester.SubsystemTeleopPeriodic();
    //encTest.SubsystemTeleopPeriodic();
  }
}
