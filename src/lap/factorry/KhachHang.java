package lap.factorry;


import java.util.ArrayList;
import java.util.List;

public class KhachHang {
    private String name;
    private String soCMND;
    private String quocTich;
    private Date ngayNhanPhong;
    private Date nagyTraPhong;
    private double tienCoc;
    private String loaiPhong;
    private List<DichVu> listDV;

    public KhachHang(String name, String soCMND, String quocTich, Date ngayNhanPhong, Date nagyTraPhong, double tienCoc, String loaiPhong) {
        this.name = name;
        this.soCMND = soCMND;
        this.quocTich = quocTich;
        this.ngayNhanPhong = ngayNhanPhong;
        this.nagyTraPhong = nagyTraPhong;
        this.tienCoc = tienCoc;
        this.loaiPhong = loaiPhong;
        this.listDV=new ArrayList<>();
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public Date getNagyTraPhong() {
        return nagyTraPhong;
    }

    public Date getNgayNhanPhong() {
        return ngayNhanPhong;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public String getName() {
        return name;
    }
    public void addDichVu(KhachHang khachHang,String loaiDv){

    }
    public void addDichVu(){

    }
}
