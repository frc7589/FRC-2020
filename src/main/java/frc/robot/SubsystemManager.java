package frc.robot;

import java.util.List;

import frc.robot.Subsystems.SubsystemBase;

public class SubsystemManager {

    // Singleton class
    private SubsystemManager() {};

    private static SubsystemManager Instance;

    public static SubsystemManager GetInstance() {
        if (Instance == null) {
            Instance = new SubsystemManager();
        }
        return Instance;
    }

    private List<SubsystemBase> ActiveSubsystems;

    public void InitAllSubsystem() {
        for (int i = 0; i < ActiveSubsystems.size(); i++) {
            ActiveSubsystems.get(i).InitSubsystem();
        }
    }

    public boolean ActivateSubsystem(SubsystemBase subsystem) {
        return ActiveSubsystems.add(subsystem);
    }

    public boolean DeactivateSubsystem(SubsystemBase subsystem) {
        return ActiveSubsystems.remove(subsystem);
    }

    public void RunAllActivePeriodic() {
        for (int i = 0; i < ActiveSubsystems.size(); i++) {
            ActiveSubsystems.get(i).SubsystemTeleopPeriodic();
        }
    }
}