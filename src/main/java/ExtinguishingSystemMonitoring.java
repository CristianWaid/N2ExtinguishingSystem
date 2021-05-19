import java.util.ArrayList;

public class ExtinguishingSystemMonitoring {

    ExtinguishingSystem extinguishingSystem;
    ArrayList<Actions> actions = new ArrayList<>();
    String errorMessage;
    int currentStatus = 0;

    public ExtinguishingSystemMonitoring(ExtinguishingSystem extinguishingSystem) {
        this.extinguishingSystem = extinguishingSystem;
    }

    public void checkCurrentPressure() {
        // check for permission
        if (!currentUserIsAllowedToCheck(MonitoringSystems.PRESSURE)) {
            return;
        }

        // check current pressure and take necessary actions
        int currentPressure = extinguishingSystem.getCurrentPressure();
        if (currentPressure < 50) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            setErrorMessage("Kritischer Minimalwert (krit. Unterdruck), Löschvorgang ist nicht mehr garantiert.");
            checkStatusChange(1);
            currentStatus = 1;
        } else if (currentPressure >= 50 && currentPressure <= 180) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            setErrorMessage("Unterdruck, Befüllen der Tanks notwendig.");
            checkStatusChange(2);
            currentStatus = 2;
        } else if (currentPressure >= 220 && currentPressure <= 300) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            setErrorMessage("Überdruck, Entlüften der Tanks einleiten.");
            checkStatusChange(3);
            currentStatus = 3;
        } else if (currentPressure > 300 && currentPressure <= 500) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            setErrorMessage("Kritischer Maximalwert (krit. Überdruck), Beschädigung der Tanks möglich.");
            checkStatusChange(4);
            currentStatus = 4;
        } else if (currentPressure > 500) {
            actions.add(Actions.TRIGGER_ALARM);
            actions.add(Actions.START_EVACUATION);
            setErrorMessage("Gefahrenwert erreicht.");
            checkStatusChange(5);
            currentStatus = 5;
        } else {
            clearActions();
            setErrorMessage("Optimale Füllmenge der Tanks.");
            currentStatus = 0;
        }
    }

    public void checkValveControlStatus() {
        // check for permission
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

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
