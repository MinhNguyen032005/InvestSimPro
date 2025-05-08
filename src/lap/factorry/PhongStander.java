package lap.factorry;

public class PhongStander extends Phong{


    public PhongStander(double donGia, String maPhong, String tenPhong, int soNguoi) {
        super(donGia, maPhong, tenPhong, soNguoi);
        c++;
    }

    public PhongStander(IThietBi iThietBi) {
        super();
        maPhong="Stander00"+(++c);
        tenPhong="PhongStander";
        donGia=150000;
        soNguoi=4;
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
