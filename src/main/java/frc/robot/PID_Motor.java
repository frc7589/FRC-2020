package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class PID_Motor {
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
        _sb.append("u");	// Native Units
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