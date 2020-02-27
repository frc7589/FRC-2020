package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotionMagicMotor {
    private TalonSRX _talon;

    private int targetPositionRotations;
    private int tmp;
    private int targetVelocity;
    private int slotIdx;
    public MotionMagicMotor(TalonSRX targetTalonSRX, double F_value, double P_value, double I_value,
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

      _talon.configMotionCruiseVelocity(150000, timeout);
      _talon.configMotionAcceleration(50, timeout);

      int absolutePosition = _talon.getSensorCollection().getPulseWidthPosition();
      absolutePosition &= 0xFFF;
      absolutePosition *= -1;
      _talon.setSelectedSensorPosition(absolutePosition, slotIdx, timeout);
     }

    public void PosControl(int targetPos) {
      targetPositionRotations = targetPos;
      _talon.set(ControlMode.Position, targetPositionRotations);
    }
    public void VelControl(int targetVel) {
        targetVelocity = targetVel;
        _talon.set(ControlMode.Velocity, targetVelocity);
    }
    public void testing(int number){
        tmp = number;
        _talon.set(ControlMode.MotionMagic,tmp);
    }
  }