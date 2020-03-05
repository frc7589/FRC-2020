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
<<<<<<< HEAD
    baseDrive = new BaseDrive();
    shooter = new Shooter();
    lifter = new Lifter();
    intaker = new Intaker();
    nwTable = new NWTable();
    spinner = new Spinner();
    encTest = new EncTest();
    //tester = new Tester();
    baseDrive.InitSubsystem();
    //shooter.InitSubsystem();
    //lifter.InitSubsystem();
    //intaker.InitSubsystem();
    //nwTable.InitSubsystem();
    //spinner.InitSubsystem();
    //tester.InitSubsystem();
    //encTest.InitSubsystem();
=======
    baseDrive = BaseDrive.GetInstance();
    intaker = Intaker.GetInstance();
    shooter = Shooter.GetInstance(); 
    lifter = Lifter.GetInstance();
    nwTable = NWTable.GetInstance();
    spinner = Spinner.GetInstance();
    encTest = EncTest.GetInstance();
    shooter.InitSubsystem();
>>>>>>> 736ab9af63929375772ee51b6d46189760b95b25
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
<<<<<<< HEAD
    //baseDrive.SubsystemTeleopPeriodic();
    //shooter.SubsystemTeleopPeriodic();
    //lifter.SubsystemTeleopPeriodic();
    //intaker.SubsystemTeleopPeriodic();
    //nwTable.SubsystemTeleopPeriodic();
=======
    baseDrive.SubsystemTeleopPeriodic();
    shooter.SubsystemTeleopPeriodic();
    lifter.SubsystemTeleopPeriodic();
    intaker.SubsystemTeleopPeriodic();
    nwTable.SubsystemTeleopPeriodic();
>>>>>>> 736ab9af63929375772ee51b6d46189760b95b25
    //spinner.SubsystemTeleopPeriodic();
    //tester.SubsystemTeleopPeriodic();
    //encTest.SubsystemTeleopPeriodic();
  }
}
