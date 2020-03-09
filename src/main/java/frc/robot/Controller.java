package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {

    // Singleton instance
    private Controller() {};

    private static Controller Instance;

    public static Controller GetInstance() {
        if (Instance == null) {
            Instance = new Controller();
        }
        return Instance;
    }

    public XboxController xcon = new XboxController(0);
    
    private boolean UpPressed = false;
    public boolean Get_POV_UpPressed() {
        if (!UpPressed && xcon.getPOV() == 0) {
            UpPressed = true;
            return true;
        }
        if (xcon.getPOV() != 0) UpPressed = false;
        return (xcon.getPOV() == 0);
    }
    
    private boolean RightPressed = false;
    public boolean Get_POV_RightPressed() {
        if (!RightPressed && xcon.getPOV() == 90) {
            RightPressed = true;
            return true;
        }
        if (xcon.getPOV() != 90) RightPressed = false;
        return (xcon.getPOV() == 90);
    }
}