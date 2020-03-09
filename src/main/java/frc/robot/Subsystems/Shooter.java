package frc.robot.Subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.PID_Motor;

public class Shooter extends SubsystemBase {

    // Singleton instance
    private Shooter() {}
      private static Shooter instance = null;
      public static Shooter GetInstance() {
        if (instance==null) instance = new Shooter();
        return instance;
    }

    private WPI_TalonSRX lShooter;
    private WPI_TalonSRX rShooter;

    private PID_Motor lPID;
    private PID_Motor rPID;
    
    private double[] shootSpd = {.087, .85};
    private int[] shootSpdPID = {12, 32};
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
                                      //f  P  I  D
        lPID = new PID_Motor(lShooter, 20.5, 10, 0.2, 10, 0, 30);
        rPID = new PID_Motor(rShooter, 20.5, 15, 0.1, 0, 0, 30);
    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        if (xcon.getAButton()) {
            speedIdx = 0;
            StartShooting();
        }
        else if (xcon.getBButton()) { 
            speedIdx = 1;
            StartShooting();
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
        //lShooter.set(-shootSpd[speedIdx]);
        //rShooter.set(-shootSpd[speedIdx]);

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


