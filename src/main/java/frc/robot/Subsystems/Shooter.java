package frc.robot.Subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
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
    // 開始調速度之前先調PID參數，37~38行
    private int[] shootSpdPID = {12, 24};
    // 袁的MotionMagic我不曉得能不能用，如果可以考慮下改用這個，Tune 40~41 行
    private int[] shootSpdMM = {};
    private int speedIdx = 0;

    private NetworkTable visionTable;
    private NetworkTable autoTable;

    private double dist = 0;
    private double stage = 0;

    private List<Double> dList = new ArrayList<Double>();

    private Timer timer;

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

        visionTable = NetworkTableInstance.getDefault().getTable("vision table");
        autoTable = NetworkTableInstance.getDefault().getTable("auto table");
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

    @Override
    public void SubsystemAutoPeriodic() {
        super.SubsystemAutoPeriodic();
        timer.start();

        dist = visionTable.getEntry("distant").getDouble(0.0);
        stage = autoTable.getEntry("stage").getDouble(0);

        /*if(stage == 2){
            if(dList.size() <= 10){
                dList.add(dist);
            }
            else{
                double target_dist = getdistant();
                int speed = XD(target_dist);
                lPID.VelControl(speed);
                rPID.VelControl(speed);
                timer.delay(8);
                StopShooting();

            }
        }*/
    }

    public void StartShooting() {
        // 舊的，電壓控制
        //lShooter.set(-shootSpd[speedIdx]);
        //rShooter.set(-shootSpd[speedIdx]);

        // 新的PID
        lPID.VelControl(-shootSpdPID[speedIdx]);
        rPID.VelControl(-shootSpdPID[speedIdx]);
    }

    public void StopShooting() {
        lShooter.set(0);
        rShooter.set(0);
    }

    public double getdistant(){
        while(dList.size() > 3){
            double avg = 0;
            for(double i : dList){
                avg += i;
            }
            avg /= dList.size();
             double max = 0;
            for(double i : dList){
                if(Math.abs(i-avg) > max-avg)
                    max = i;
            }
            dList.remove(max);
        }
        double avg = 0;
        for(double i : dList){
            avg += i;
        }
        return avg/3;
    }

    private void Calculation_shooting(){
        double target_dist = getdistant();
        int speed = 0;
        //int speed = XD(target_dist);
        lPID.VelControl(speed);
        rPID.VelControl(speed);
        timer.delay(8);
        StopShooting();
    }
}


