package lap.bai_1;

public class CurrencyUnit {
    private String name;
    private String symbol;
    private CurrencyMeasure currencyMeasure;

    public CurrencyUnit(String name, String symbol, CurrencyMeasure currencyMeasure) {
        this.name = name;
        this.symbol = symbol;
        this.currencyMeasure = currencyMeasure;
    }

    private double chuyenDoiLuongTienCoSo() {
        double tiGia = 0.0;
        String donVi = currencyMeasure.getUnit();
        switch (donVi) {
            case "VND": {
                tiGia = 225055;
                break;
            }
            case "JPY": {
                tiGia = 111.8;
                break;
            }
            case "EUR": {
                tiGia = 0.89;
                break;
            }
            case "GBP": {
                tiGia = 0.77;
                break;
            }
        }
        return currencyMeasure.getValue() / tiGia;
    }

    public double chuyenDoiLuongTien(CurrencyUnit currencyUnit) {
        double tiGia = 0.0;
        String donVi = currencyUnit.currencyMeasure.getUnit();
        switch (donVi) {
            case "VND": {
                tiGia = 225055;
                break;
            }
            case "JPY": {
                tiGia = 111.8;
                break;
            }
            case "EUR": {
                tiGia = 0.89;
                break;
            }
            case "GBP": {
                tiGia = 0.77;
                break;
            }
        }
        return chuyenDoiLuongTienCoSo() * tiGia;
    }
}
