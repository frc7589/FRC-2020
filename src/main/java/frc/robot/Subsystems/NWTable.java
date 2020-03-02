package frc.robot.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NWTable extends SubsystemBase{

    // Singleton instance
    private NWTable() {
        InitSubsystem();}
      private static NWTable instance = null;
      public static NWTable GetInstance() {
        if (instance==null) instance = new NWTable();
        return instance;
    }

    private NetworkTableEntry startButtonEntry;

    @Override
    public void InitSubsystem(){
        super.InitSubsystem();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        NetworkTable vTable = inst.getTable("vision table");

        startButtonEntry = inst.getTable("vision table").getEntry("start button");

    }

    @Override
    public void SubsystemTeleopPeriodic() {
        super.SubsystemTeleopPeriodic();

        startButtonEntry.setBoolean(xcon.getStartButton());
    }
}