public class ExtinguishingSystem {
    private int currentPressure;
    private int voltage;
    private UserGroups currentUserGroup;
    private ValveStatus valveStatus;

    public ExtinguishingSystem(int currentPressure, int voltage, UserGroups currentUserGroup, ValveStatus valveStatus) {
        this.currentPressure = currentPressure;
        this.voltage = voltage;
        this.currentUserGroup = currentUserGroup;
        this.valveStatus = valveStatus;
    }

    public int getCurrentPressure() {
        return currentPressure;
    }

    public void setCurrentPressure(int pressure) {
        this.currentPressure = pressure;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public UserGroups getCurrentUserGroup(){
        return currentUserGroup;
    }

    public void setCurrentUserGroup(UserGroups currentUserGroup){
        this.currentUserGroup = currentUserGroup;
    }

    public ValveStatus getValveStatus(){
        return valveStatus;
    }

    public void setValveStatus(ValveStatus valveStatus){
        this.valveStatus = valveStatus;
    }

}
