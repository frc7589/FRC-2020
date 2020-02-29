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

  BaseDrive baseDrive;
  Intaker intaker;
  Shooter shooter;
  Lifter lifter;
  NWTable nwTable;
  Spinner spinner;
  EncTest encTest;

  @Override
  public void robotInit() {
    baseDrive = new BaseDrive();
    shooter = new Shooter();
    lifter = new Lifter();
    intaker = new Intaker();
    nwTable = new NWTable();
    spinner = new Spinner();
<<<<<<< HEAD
    baseDrive.InitSubsystem();
    //shooter.InitSubsystem();
    //lifter.InitSubsystem();
    //intaker.InitSubsystem();
    nwTable.InitSubsystem();
    //spinner.InitSubsystem();
=======
    encTest = new EncTest();
    //tester = new Tester();
    //baseDrive.InitSubsystem();
    //shooter.InitSubsystem();
    //lifter.InitSubsystem();
    //intaker.InitSubsystem();
    //nwTable.InitSubsystem();
    spinner.InitSubsystem();
>>>>>>> f3859d3f99289d166eb3837bfdd8b527e02a65a8
    //tester.InitSubsystem();
    //encTest.InitSubsystem();
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
    //baseDrive.SubsystemTeleopPeriodic();
    //shooter.SubsystemTeleopPeriodic();
    //lifter.SubsystemTeleopPeriodic();
    //intaker.SubsystemTeleopPeriodic();
<<<<<<< HEAD
    nwTable.SubsystemTeleopPeriodic();
    //spinner.SubsystemTeleopPeriodic();
=======
    //nwTable.SubsystemTeleopPeriodic();
    spinner.SubsystemTeleopPeriodic();
    //tester.SubsystemTeleopPeriodic();
    //encTest.SubsystemTeleopPeriodic();
>>>>>>> f3859d3f99289d166eb3837bfdd8b527e02a65a8
  }
}
