/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Subsystems.BaseDrive;
import frc.robot.Subsystems.Intaker;
import frc.robot.Subsystems.Lifter;
import frc.robot.Subsystems.NWTable;
import frc.robot.Subsystems.Shooter;

public class Robot extends TimedRobot {

  BaseDrive baseDrive;
  Intaker intaker;
  Shooter shooter;
  Lifter lifter;
  NWTable nwTable;

  @Override
  public void robotInit() {
    baseDrive = new BaseDrive();
    shooter = new Shooter();
    lifter = new Lifter();
    intaker = new Intaker();
    nwTable = new NWTable();
    baseDrive.InitSubsystem();
    shooter.InitSubsystem();
    lifter.InitSubsystem();
    intaker.InitSubsystem();
    nwTable.InitSubsystem();
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
    Controller.GetInstance().ResetController();
  }

  @Override
  public void testPeriodic() {
    baseDrive.SubsystemTeleopPeriodic();
    shooter.SubsystemTeleopPeriodic();
    lifter.SubsystemTeleopPeriodic();
    intaker.SubsystemTeleopPeriodic();
    nwTable.SubsystemTeleopPeriodic();
  }
}
