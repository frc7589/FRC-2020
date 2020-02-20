package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.PID_Motor;

public class Spinner extends SubsystemBase {

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private final Color kBlueTarget = ColorMatch.makeColor(0.140, 0.433, 0.422);
    private final Color kGreenTarget = ColorMatch.makeColor(0.166, 0.576, 0.256);
    private final Color kRedTarget = ColorMatch.makeColor(0.380, 0.420, 0.240);
    private final Color kYellowTarget = ColorMatch.makeColor(0.267, 0.494, 0.180);

    private WPI_TalonSRX wheelArm;
    private PID_Motor armPID;
    private WPI_VictorSPX wheelSpinner;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        armPID = new PID_Motor(wheelArm, 0, 2, 0, 1, 0, 30);

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);  
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        armPID.PrintValue(); 
    }

    public void Sensing() {
        Color detectedColor = m_colorSensor.getColor();
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = "Blue";
        } 
        else if (match.color == kRedTarget) {
            colorString = "Red";
        } 
        else if (match.color == kGreenTarget) {
            colorString = "Little Green";
        } 
        else if (match.color == kYellowTarget) {
            colorString = "Yellow";
        } 
        else {
            colorString = "Unknown";
        }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    }
}