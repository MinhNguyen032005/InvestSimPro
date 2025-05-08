package lap.decord;

import java.util.List;

public class CongTy implements Subject {
    private String ndTB;
    private String tenCTy;
    private List<KhachHang> listKH;
    private List<ObServer> listObServer;


    public CongTy() {
    }

    public CongTy(String ndTB, String tenCTy) {
        this.ndTB = ndTB;
        this.tenCTy = tenCTy;
    }

    public double doanhThu() {
        double re = 0;
        for (KhachHang khachHang : listKH) {
            re += khachHang.tt();
        }
        return re;
    }

    public void print() {

    }

    public void addKH(KhachHang khachHang) {
        listKH.add(khachHang);
    }

    @Override
    public void addObServer(ObServer obServer) {
        listObServer.add(obServer);
        obServer.setDangKy(true);
    }

    @Override
    public void removeObServer(ObServer obServer) {
        listObServer.remove(obServer);
        obServer.setDangKy(false);
    }

    @Override
    public void notiify() {
        for (ObServer obServer : listObServer) {
            obServer.hienThiNDThongBao(ndTB);
        }
    }

    @Override
    public void updateThongBao(String ndTB) {
        setNdTB(ndTB);
        for (KhachHang khachHang : listKH) {
            khachHang.setCheckTB(true);
        }
    }

    public double tongDoanhThu() {
        double re = 0;
        for (ObServer obServer : listObServer) {
            re += obServer.pay();
        }
        return re;
    }

    public void setNdTB(String ndTB) {
        this.ndTB = ndTB;
    }
}
