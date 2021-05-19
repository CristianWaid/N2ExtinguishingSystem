import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class ValveControlTest {
    // initialize classes
    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(0, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);

    @AfterEach
    void cleanUpActions(){
        extinguishingSystemMonitoring.clearActions();
    }

    @Test
    @DisplayName("Test 'working' status of Valve Control")
    void testValveControlStatusWorking() {
        // define values for test
        extinguishingSystem.setCurrentUserGroup(UserGroups.MANAGER);
        extinguishingSystem.setValveStatus(ValveStatus.WORKING);

        assertTimeout(Duration.ofMillis(10), () -> extinguishingSystemMonitoring.checkValveControlStatus()); // abort if operation takes longer than 10ms
        assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.SHOW_VALUE) && extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
    }

    @Test
    @DisplayName("Test 'no response' status of Valve Control")
    void testValveControlStatusNoResponse() {
        // define values for test
        extinguishingSystem.setCurrentUserGroup(UserGroups.MANAGER);
        extinguishingSystem.setValveStatus(ValveStatus.NO_RESPONSE);

        assertTimeout(Duration.ofMillis(10), () -> extinguishingSystemMonitoring.checkValveControlStatus()); // abort if operation takes longer than 10ms
        assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.SHOW_ERROR) && extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
    }

    @Test
    @DisplayName("Test status of Valve Control without access rights")
    void testValveControlStatusWithoutRights() {
        // define values for test
        extinguishingSystem.setCurrentUserGroup(UserGroups.WORKER);
        extinguishingSystem.setValveStatus(ValveStatus.WORKING);

        assertTimeout(Duration.ofMillis(10), () -> extinguishingSystemMonitoring.checkValveControlStatus()); // abort if operation takes longer than 10ms
        assertTrue(extinguishingSystemMonitoring.actions.isEmpty());
    }
}
