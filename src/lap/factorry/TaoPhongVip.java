package lap.factorry;

public class TaoPhongVip extends TaoPhong {
    @Override
    public Phong createPhongTB(String loaiPhong) {
        Phong phong = null;
        IThietBi iThietBi = new TB_Stander();
        phong = new PhongVIP(iThietBi);
        System.out.println("Da tao Thanh Cong Phong VIP");
        phong.createTB(loaiPhong);
        return phong;
    }
}
