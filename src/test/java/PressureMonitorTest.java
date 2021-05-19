import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PressureMonitorTest {

    ExtinguishingSystem extinguishingSystem = new ExtinguishingSystem(0, 6, UserGroups.MANAGER, ValveStatus.WORKING);
    ExtinguishingSystemMonitoring extinguishingSystemMonitoring = new ExtinguishingSystemMonitoring(extinguishingSystem);

    @ParameterizedTest
    @ValueSource(ints = {49, 50, 51, 179, 180, 181, 219, 220, 221, 299, 300, 301, 499, 500, 501})
    @DisplayName("Pressure Monitoring Test")
    void testPressureMonitoring(int pressure) {
        extinguishingSystem.setCurrentPressure(pressure);

        assertTimeout(Duration.ofMillis(10), () -> extinguishingSystemMonitoring.checkCurrentPressure());

        switch (pressure) {
            case 49:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.ACOUSTIC_SIGNAL) && extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_OPERATOR));
                assertEquals("Kritischer Minimalwert (krit. Unterdruck), Löschvorgang ist nicht mehr garantiert.", extinguishingSystemMonitoring.getErrorMessage());
                break;
            case 50:
            case 51:
            case 179:
            case 180:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_MAINTENANCE) && extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_OPERATOR));
                assertEquals("Unterdruck, Befüllen der Tanks notwendig.", extinguishingSystemMonitoring.getErrorMessage());
                break;
            case 181:
            case 219:
                assertTrue(extinguishingSystemMonitoring.actions.isEmpty());
                assertEquals("Optimale Füllmenge der Tanks.", extinguishingSystemMonitoring.getErrorMessage());
                break;
            case 220:
            case 221:
            case 299:
            case 300:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_MAINTENANCE) && extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_OPERATOR));
                assertEquals("Überdruck, Entlüften der Tanks einleiten.", extinguishingSystemMonitoring.getErrorMessage());
                break;
            case 301:
            case 499:
            case 500:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.ACOUSTIC_SIGNAL) && extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_OPERATOR) && extinguishingSystemMonitoring.actions.contains(Actions.NOTIFICATION_MAINTENANCE));
                assertEquals("Kritischer Maximalwert (krit. Überdruck), Beschädigung der Tanks möglich.", extinguishingSystemMonitoring.getErrorMessage());
                break;
            case 501:
                assertTrue(extinguishingSystemMonitoring.actions.contains(Actions.TRIGGER_ALARM) && extinguishingSystemMonitoring.actions.contains(Actions.START_EVACUATION));
                assertEquals("Gefahrenwert erreicht.", extinguishingSystemMonitoring.getErrorMessage());
                break;
        }
        extinguishingSystemMonitoring.clearActions();
    }
}
