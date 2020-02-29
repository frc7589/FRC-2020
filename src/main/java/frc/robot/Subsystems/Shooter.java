package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.PID_Motor;

public class Shooter extends SubsystemBase {
    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;

    private boolean toggleHigh = false;
    private boolean toggleLow = false;

    private PID_Motor lPID;
    private PID_Motor rPID;

    // 速度在這裡調，shootSpd是舊的，也就是調電壓
    private double[] shootSpd = {-.3, -.5};
    // 這邊是PID的速度控制，單位是更新率/100ms，用飛輪的話12代表1/10秒跑一圈=每秒10圈
    // 開始調速度之前先調PID參數，33~34行
    private double[] shootSpdPID = {12, 24};
    private int speedIdx = 0;

    @Override
    public void InitSubsystem() {
        super.InitSubsystem();

        lShooter = new WPI_TalonSRX(2);
        rShooter = new WPI_TalonSRX(3);
        // Tune P 和 D 就好，分別是第二個和第四個數字
        // e.g. (lshooter, 0.02, 0.01, 0, 0.1, 0, 30)
        // 兩個需要的參數可能不一樣，把他們調到加速度差不多就好 
        lPID = new PID_Motor(lShooter, 0.02, 0, 0, 0, 0, 30);
        rPID = new PID_Motor(rShooter, 0.02, 0, 0, 0, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getAButtonPressed()) {
            toggleLow = !toggleLow;
            toggleHigh = false;
            speedIdx = 0;
            if (toggleLow) StartShooting();
        }
        else if (xcon.getBButton()) {
            toggleHigh = !toggleHigh;
            toggleLow = false;
            speedIdx = 1;
            if (toggleHigh) StartShooting();
        }
        else StopShooting();
    }

    public void StartShooting() {
        // 舊的，電壓控制
        //lShooter.set(-shootSpd[speedIdx]);
        //rShooter.set(-shootSpd[speedIdx]);

        // 新的PID
        lShooter.set(-shootSpdPID[speedIdx]);
        rShooter.set(-shootSpdPID[speedIdx]);
    }

    public void StopShooting() {
        lShooter.set(0);
        rShooter.set(0);
    }
}


