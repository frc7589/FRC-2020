package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class Spinner extends SubsystemBase {

    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private final Color kBlueTarget = ColorMatch.makeColor(0.140, 0.433, 0.422);
    private final Color kGreenTarget = ColorMatch.makeColor(0.166, 0.576, 0.256);
    private final Color kRedTarget = ColorMatch.makeColor(0.380, 0.420, 0.240);
    private final Color kYellowTarget = ColorMatch.makeColor(0.267, 0.494, 0.180);

    private String[] color = {"Blue","Green","Red","Yellow"};

    private Servo wheelArm;
    private WPI_VictorSPX wheelSpinner;

    private String colorString;
    private int targetColorIdx;
    private boolean start;
    private int num;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

        wheelArm = new Servo(0);
        wheelSpinner = new WPI_VictorSPX(0);

        targetColorIdx = 0;
        start = false;
        wheelArm.set(0.0);
        num = 0;
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        Sensing();
        SmartDashboard.putString("target color", color[targetColorIdx]);

        if(xcon.getTriggerAxis(Hand.kRight) >= 0.5){
            wheelArm.set(num);
            num = (num+1)%2;
        }

        if(xcon.getTriggerAxis(Hand.kLeft) >= 0.5){
            wheelSpinner.set(0.5);
        }
        else{
            wheelSpinner.set(0.0);
        }

        if(xcon.getPOV() == 90){
            targetColorIdx = (targetColorIdx+1)%4;
        }

        if(xcon.getPOV() == 270 || start){

            if(!start){
                targetColorIdx = (targetColorIdx+2)%4;
            }

            if(colorString != color[targetColorIdx]){
                start = true;
                wheelSpinner.set(1.0);
            }
            else{
                start = false;
                wheelSpinner.set(0.0);
            }
        }
    }

    public void Sensing() {
        Color detectedColor = m_colorSensor.getColor();
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = color[0];
        } 
        else if (match.color == kRedTarget) {
            colorString = color[1];
        } 
        else if (match.color == kGreenTarget) {
            colorString = color[2];
        } 
        else if (match.color == kYellowTarget) {
            colorString = color[3];
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