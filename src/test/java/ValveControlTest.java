import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValveControlTest {
    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(0, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);

    @Test
    void testValveControlStatusWorking() {
        extinguishingSystem.setCurrentUserGroup(UserGroups.MANAGER);
        extinguishingSystem.setValveStatus(ValveStatus.WORKING);
        extinguishingSystemMonitoring.checkValveControlStatus();
        assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.SHOW_VALUE) && extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        extinguishingSystemMonitoring.clearActions();
    }

    @Test
    void testValveControlStatusNoResponse() {
        extinguishingSystem.setCurrentUserGroup(UserGroups.MANAGER);
        extinguishingSystem.setValveStatus(ValveStatus.NO_RESPONSE);
        extinguishingSystemMonitoring.checkValveControlStatus();
        assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.SHOW_ERROR) && extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        extinguishingSystemMonitoring.clearActions();
    }

    @Test
    void testValveControlStatusWithoutRights() {
        extinguishingSystem.setCurrentUserGroup(UserGroups.WORKER);
        extinguishingSystem.setValveStatus(ValveStatus.WORKING);
        extinguishingSystemMonitoring.checkValveControlStatus();
        assertTrue(extinguishingSystemMonitoring.actions.isEmpty());
        extinguishingSystemMonitoring.clearActions();
    }
}
