package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class OldBaseDrive extends SubsystemBase {
    private WPI_VictorSPX l1;
    private WPI_VictorSPX l2;
    private WPI_VictorSPX r1;
    private WPI_VictorSPX r2;

    private SpeedControllerGroup lConGrp;
    private SpeedControllerGroup rConGrp;

    private DifferentialDrive diffDrive;
    
    private int speedChanel = 0;
    private double[] speedList = {.4, .7, 1.};

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();
        l1 = new WPI_VictorSPX(1);
        l2 = new WPI_VictorSPX(3);
        r1 = new WPI_VictorSPX(0);
        r2 = new WPI_VictorSPX(2);
        
        lConGrp = new SpeedControllerGroup(l1, l2);
        rConGrp = new SpeedControllerGroup(r1, r2);

        diffDrive = new DifferentialDrive(lConGrp, rConGrp);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();
        if (Math.abs(xcon.getY(Hand.kLeft))>.1||Math.abs(xcon.getY(Hand.kRight))>.1) {
            diffDrive.tankDrive(xcon.getY(Hand.kLeft)*speedList[speedChanel], xcon.getY(Hand.kRight)*speedList[speedChanel]);
        }
        if (xcon.getBumperPressed(Hand.kRight)) {
            speedChanel++;
            if (speedChanel>speedList.length-1) speedChanel=speedList.length-1;
        }
        if (xcon.getBumperPressed(Hand.kLeft)) {
            speedChanel--;
            if (speedChanel<0) speedChanel = 0;
        }

    }
}