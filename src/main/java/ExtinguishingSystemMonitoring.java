import java.util.ArrayList;

public class ExtinguishingSystemMonitoring {

    ExtinguishingSystem extinguishingSystem;
    ArrayList<Actions> actions = new ArrayList<>();
    int currentStatus = 0;

    public ExtinguishingSystemMonitoring(ExtinguishingSystem extinguishingSystem) {
        this.extinguishingSystem = extinguishingSystem;
    }

    public void checkCurrentPressure() {
        if (!currentUserIsAllowedToCheck(MonitoringSystems.PRESSURE)) {
            return;
        }
        int currentPressure = extinguishingSystem.getCurrentPressure();
        if (currentPressure < 50) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            checkStatusChange(1);
            currentStatus = 1;
        } else if (currentPressure >= 50 && currentPressure <= 180) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            checkStatusChange(2);
            currentStatus = 2;
        } else if (currentPressure >= 220 && currentPressure <= 300) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            checkStatusChange(3);
            currentStatus = 3;
        } else if (currentPressure > 300 && currentPressure <= 500) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            checkStatusChange(4);
            currentStatus = 4;
        } else if (currentPressure > 500) {
            actions.add(Actions.TRIGGER_ALARM);
            actions.add(Actions.START_EVACUATION);
            checkStatusChange(5);
            currentStatus = 5;
        } else {
            clearActions();
            currentStatus = 0;
        }
    }

    public void checkValveControlStatus() {
        if (!currentUserIsAllowedToCheck(MonitoringSystems.VALVECONTROL)) {
            return;
        }
        if (extinguishingSystem.getValveStatus() == ValveStatus.WORKING) {
            actions.add(Actions.SHOW_VALUE);
        } else {
            actions.add(Actions.SHOW_ERROR);
        }
        actions.add(Actions.LOG_DATA);
    }

    private boolean currentUserIsAllowedToCheck(MonitoringSystems monitoredSystem) {
        var currentUser = extinguishingSystem.getCurrentUserGroup();
        if (currentUser != UserGroups.WORKER) {
            if (currentUser == UserGroups.MAINTENANCE && monitoredSystem == MonitoringSystems.VALVECONTROL) {
                return false;
            }
            return true;
        }
        return false;
    }

    private void checkStatusChange(int newStatus) {
        if (currentStatus != newStatus) {
            actions.add(Actions.LOG_DATA);
        }
    }

    public void clearActions() {
        actions.clear();
    }

    public void checkCurrentVoltage() {
        int currentVoltage = extinguishingSystem.getVoltage();
        if (currentVoltage < 5) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.LOG_DATA);
        }
    }
}
