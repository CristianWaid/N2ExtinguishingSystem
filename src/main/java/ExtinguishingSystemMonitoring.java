import java.util.ArrayList;

public class ExtinguishingSystemMonitoring {

    ExtinguishingSystem extinguishingSystem;
    ArrayList<Actions> actions = new ArrayList<Actions>();

    public ExtinguishingSystemMonitoring(ExtinguishingSystem extinguishingSystem) {
        this.extinguishingSystem = extinguishingSystem;
    }

    public void checkCurrentPressure() {
        int currentPressure = extinguishingSystem.getCurrentPressure();
        if (currentPressure < 50) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.LOG_DATA);
        } else if (currentPressure >= 50 && currentPressure <= 180) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            actions.add(Actions.LOG_DATA);
        } else if (currentPressure >= 220 && currentPressure <= 300) {
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            actions.add(Actions.LOG_DATA);
        } else if (currentPressure > 300 && currentPressure <= 500) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.NOTIFICATION_MAINTENANCE);
            actions.add(Actions.NOTIFICATION_OPERATOR);
            actions.add(Actions.LOG_DATA);
        } else if (currentPressure > 500) {
            actions.add(Actions.TRIGGER_ALARM);
            actions.add(Actions.START_EVACUATION);
            actions.add(Actions.LOG_DATA);
        }
    }

    public ArrayList<Actions> checkCurrentVoltage() {
        int currentVoltage = extinguishingSystem.getVoltage();
        if (currentVoltage < 5) {
            actions.add(Actions.ACOUSTIC_SIGNAL);
            actions.add(Actions.LOG_DATA);
        }
        return actions;
    }
}
