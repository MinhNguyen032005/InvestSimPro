package lap.bai_1;

public class CurrencyMeasure {
    private double value;
    private String unit;

    public CurrencyMeasure(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
