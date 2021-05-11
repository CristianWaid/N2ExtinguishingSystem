public class ExtinguishingSystem {
    private int currentPressure;
    private int voltage;

    public ExtinguishingSystem(int currentPressure, int voltage) {
        this.currentPressure = currentPressure;
        this.voltage = voltage;
    }

    public void setCurrentPressure(int pressure) {
        this.currentPressure = pressure;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getCurrentPressure() {
        return currentPressure;
    }

    public int getVoltage() {
        return voltage;
    }
}
