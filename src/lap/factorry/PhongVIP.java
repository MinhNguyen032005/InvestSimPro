package lap.factorry;

public class PhongVIP extends Phong{


    public PhongVIP(IThietBi iThietBi) {
        super();
        maPhong="VIP00"+(++c);
        tenPhong="PhongVIP";
        donGia=300000;
        soNguoi=2;
        this.iThietBi=iThietBi;
    }

    @Override
    public void createTB(String phong) {
        System.out.println(phong);
        dt= iThietBi.createDT();
        tuLanh= iThietBi.createTuLanh();
        tv= iThietBi.createTV();
    }
}
