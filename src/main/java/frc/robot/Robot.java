// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Joystick controller = new Joystick(0);

  // Motors initialized here
  TalonFX myMotor = new TalonFX(1);
  TalonFX primaryMotor = new TalonFX(2);
  TalonFX followerMotor1 = new TalonFX(3);
  TalonFX followerMotor2 = new TalonFX(4);

  TalonFX leftMotor = new TalonFX(5);
  TalonFX rightMotor = new TalonFX(6);



  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    // Motors are setup here
    // Reset the motor to its factory default configuration
    myMotor.getConfigurator().apply(new TalonFXConfiguration());
    primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
    followerMotor1.getConfigurator().apply(new TalonFXConfiguration());
    followerMotor2.getConfigurator().apply(new TalonFXConfiguration());
    leftMotor.getConfigurator().apply(new TalonFXConfiguration());
    rightMotor.getConfigurator().apply(new TalonFXConfiguration());

    /* Configure the current of the motor. */
    var currentConfiguration = new CurrentLimitsConfigs();
    currentConfiguration.StatorCurrentLimit = 80;
    currentConfiguration.StatorCurrentLimitEnable = true;

    /* Refreshing and then applying the current configuration.*/
    myMotor.getConfigurator().refresh(currentConfiguration);
    primaryMotor.getConfigurator().refresh(currentConfiguration);
    followerMotor1.getConfigurator().refresh(currentConfiguration);
    followerMotor2.getConfigurator().refresh(currentConfiguration);
    leftMotor.getConfigurator().refresh(currentConfiguration);
    rightMotor.getConfigurator().refresh(currentConfiguration);

    myMotor.getConfigurator().apply(currentConfiguration);
    primaryMotor.getConfigurator().apply(currentConfiguration);
    followerMotor1.getConfigurator().apply(currentConfiguration);
    followerMotor2.getConfigurator().apply(currentConfiguration);
    leftMotor.getConfigurator().apply(currentConfiguration);
    rightMotor.getConfigurator().apply(currentConfiguration);

    /* Configure the follower motors */
    followerMotor1.setControl(new Follower(2, false));
    followerMotor2.setControl(new Follower(2, false));

    rightMotor.setInverted(true);

    //SmartDashboard.putString("First Printout", "Hello World!");
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    /* Live readout of the primary motor's percent out */
    SmartDashboard.putNumber("Primary Motor % Out", primaryMotor.get());

    /* Live readout of the primary motor's voltage */
    SmartDashboard.putNumber("Primary Motor Voltage", primaryMotor.getMotorVoltage().getValueAsDouble());

    SmartDashboard.putBoolean("Joystick 0 A button", controller.getRawButton(1));

    SmartDashboard.putNumber("Joystick 0 Right x Axis", controller.getRawAxis(0));

    
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    /* Motors do stuff here. */
    myMotor.set(0.5);
   // primaryMotor.set(0.5);

   if (controller.getRawButton(1)){
    primaryMotor.set(0.5);
   } else{
    primaryMotor.set(0);
   }
   
   // leftMotor.set(controller.getRawAxis(0));
   // rightMotor.set(controller.getRawAxis(0));

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
