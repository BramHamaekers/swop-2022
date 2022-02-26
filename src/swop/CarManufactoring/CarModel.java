package swop.CarManufactoring;

public class CarModel {

    // TODO: need to make more expandable
    private String body;
    private String color;
    private String engine;
    private String gearbox;
    private String seats;
    private String airco;
    private String wheels;

    public CarModel(String body, String color, String engine, String gearbox, String seats, String airco, String wheels) {
        this.body = body;
        this.color = color;
        this.engine = engine;
        this.gearbox = gearbox;
        this.seats = seats;
        this.airco = airco;
        this.wheels = wheels;
    }
}
