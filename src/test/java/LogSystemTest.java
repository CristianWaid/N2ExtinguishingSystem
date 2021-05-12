import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LogSystemTest {
    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(0, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);
    int previousStatusType = 200;

    @ParameterizedTest
    @ValueSource(ints = {200, 200, 250, 250, 500})
    void testLogSystem(int statusType) {
        extinguishingSystem.setCurrentPressure(statusType);

        if (statusType != previousStatusType) {
            assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        } else {
            assertFalse(extinguishingSystemMonitoring.actions.contains(Actions.LOG_DATA));
        }
        System.out.println(statusType);
        previousStatusType = statusType;
    }
}