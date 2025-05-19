package lap.bai_2;

public class Nhac {
    private String tua;
    private String loi;
    private String caSiBieuDien;
    private String tenNhacSiSangTac;
    private String duongDanDenTapTin;
    private int soLanTruyCap;

    public Nhac timKiem(String s) {
        if (s.equals(tua) || s.equals(tenNhacSiSangTac) || s.equals(caSiBieuDien))
            return this;
        return null;
    }

    public String getTenNhacSiSangTac() {
        return tenNhacSiSangTac;
    }

    public String getCaSiBieuDien() {
        return caSiBieuDien;
    }

    public String getLoi() {
        return loi;
    }

    public String getTua() {
        return tua;
    }

    public int getSoLanTruyCap() {
        return soLanTruyCap;
    }
}
