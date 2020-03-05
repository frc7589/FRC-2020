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
import frc.robot.Controller;
import edu.wpi.first.wpilibj.DriverStation;

public class Spinner extends SubsystemBase {

    // Singleton instance
    private Spinner() {
        InitSubsystem();}
      private static Spinner instance = null;
      public static Spinner GetInstance() {
        if (instance==null) instance = new Spinner();
        return instance;
    }
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

    boolean toggleP2 = false;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

<<<<<<< HEAD
        wheelArm = new Servo(0);
        wheelSpinner = new WPI_VictorSPX(6);
=======
        wheelArm = new Servo(9);
        wheelSpinner = new WPI_VictorSPX(5);
>>>>>>> 736ab9af63929375772ee51b6d46189760b95b25

        targetColorIdx = 0;
        start = false;
        wheelArm.set(0.0);
        num = 0;
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        Color sensedColor = SensedColor();
        SmartDashboard.putString("target color", color[targetColorIdx]);

        // Toggle Spinner Arm
        if(xcon.getTriggerAxis(Hand.kRight) >= 0.5){
            wheelArm.set(num*.5);
            num = (num+1)%2;
        }

        // Manual control
        if(xcon.getTriggerAxis(Hand.kLeft) >= 0.5){
            wheelSpinner.set(.3);
        }
        else{
            wheelSpinner.set(0.0);
        }

        // Phase 2, Desired Color
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (Controller.GetInstance().Get_POV_RightPressed()) {
            toggleP2 = !toggleP2;
        }
        if (toggleP2) {
            // Check if the sensed color matches the given color
            if (SensedColor()!=DesiredColor_P2("G")) {
                wheelSpinner.set(.3);
            }
            else {
                wheelSpinner.set(0);
                toggleP2 = false;
            }
        }
        SensedColor();
    }

    Color SensedColor() {
        Color detectedColor = m_colorSensor.getColor();
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = color[0];
            System.out.println("Blue");
        } 
        else if (match.color == kRedTarget) {
            colorString = color[1];
            System.out.println("Red");
        } 
        else if (match.color == kGreenTarget) {
            colorString = color[2];
            System.out.println("Gr");
        } 
        else if (match.color == kYellowTarget) {
            colorString = color[3];
            System.out.println("Yel");
        } 
        else {
            colorString = "Unknown";
            System.out.println("----");
        }

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);

        return match.color;
    }

    Color DesiredColor_P2(String ColorMsg) {
        switch (ColorMsg.charAt(0))
        {
            case 'B' :
                return kBlueTarget;
            case 'G' :
                return kGreenTarget;
            case 'R' :
                return kRedTarget;
            case 'Y' :
                return kYellowTarget;
            default :
                return null;
        }    
    }
}