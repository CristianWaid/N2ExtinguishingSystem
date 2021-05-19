import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogSystemTest {
    // initialize classes
    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(0, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);
    int previousStatusType = 200;

    // test with multiple values
    @ParameterizedTest
    @ValueSource(ints = {200, 200, 250, 250, 500})
    @DisplayName("Log-System Test")
    void testLogSystem(int statusType) {
        // define values for test
        extinguishingSystem.setCurrentPressure(statusType);
        extinguishingSystemMonitoring.checkCurrentPressure();

        if (statusType != previousStatusType) {
            assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        } else {
            assertFalse(extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        }
        System.out.println(statusType);
        previousStatusType = statusType;
    }
}
