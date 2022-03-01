package swop.UI;

public enum UIType {
    LOGIN("login"), GARAGE_HOLDER("garage holder"), CAR_MECHANIC("car mechanic"), MANAGER("manager");
    private final String type;

    UIType(String str) {
        type = str;
    }

    /**
     * @return String of current swop.CarManufactoring.UI type in lowercase
     */
    public String getType() {
        return type;
    }
}
