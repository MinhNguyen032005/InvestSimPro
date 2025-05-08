package lap.factorry;

import java.util.ArrayList;
import java.util.List;

public class KhachSan {
    private List<KhachHang> listKH;
    private PhongVIP phongVIPS;
    private PhongStander phongStanders;
    private Phong phong;
    private IDV idv;
    int ttVip = 1;
    int ttStand = 1;
    private TaoPhong taoPhong;

    public KhachSan() {
        listKH = new ArrayList<>();
        phongVIPS = null;
        phongStanders = null;
        idv = null;
        phong = null;
        taoPhong = null;
    }

    public void addKhachHang(KhachHang khachHang) {
        if (khachHang.getLoaiPhong().equalsIgnoreCase("VIP") == true) {

            taoPhong = new TaoPhongVip();
            phong = taoPhong.taoPhong(khachHang.getLoaiPhong());
            phongVIPS = (PhongVIP) phong;
            listKH.add(khachHang);
            ttVip++;
            return;
        } else if (khachHang.getLoaiPhong().equalsIgnoreCase("Standard") == true) {
            taoPhong = new TaoPhongStander();
            phong = taoPhong.taoPhong(khachHang.getLoaiPhong());
            phongStanders = (PhongStander) phong;
            listKH.add(khachHang);
            ttStand++;
        } else {
            System.out.println("Khong co");
        }
    }
}
