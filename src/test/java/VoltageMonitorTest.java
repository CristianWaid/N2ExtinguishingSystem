import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VoltageMonitorTest {
    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(200, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    @DisplayName("Voltage Monitoring Test")
    void testVoltageMonitoring(int voltage) {
        extinguishingSystem.setVoltage(voltage);

        assertTimeout(Duration.ofMillis(50), () -> {
            extinguishingSystemMonitoring.checkCurrentVoltage();
        });

        switch (voltage) {
            case 4:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.ACOUSTIC_SIGNAL));
                break;
            case 5:
            case 6:
                assertTrue(extinguishingSystemMonitoring.actions.isEmpty());
                break;
        }
        extinguishingSystemMonitoring.clearActions();
    }
}
