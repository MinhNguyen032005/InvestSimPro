package lap.factorry;

public class TaoPhongStander extends TaoPhong{
    @Override
    public Phong createPhongTB(String loaiPhong) {
        Phong phong=null;
        IThietBi iThietBi=new TB_Stander();
        phong=new PhongStander(iThietBi);
        System.out.println("Da tao Thanh Cong Phong Stander");
        phong.createTB(loaiPhong);
        return phong;
    }
}
