package lap.factorry;

public abstract class Phong {
   protected IThietBi iThietBi;
   protected TV  tv;
   protected DT dt;
   protected TuLanh tuLanh;
   protected double donGia;
   protected String maPhong;
   protected String tenPhong;
   protected int soNguoi;
   int c;

   public Phong(double donGia, String maPhong, String tenPhong, int soNguoi) {
      this.donGia = donGia;
      this.maPhong = maPhong;
      this.tenPhong = tenPhong;
      this.soNguoi = soNguoi;
      dt=null;
      tv=null;
      tuLanh=null;
      c++;
   }

   public Phong() {

   }

   public double giaPhong(){
      return donGia;
   }

   public abstract void createTB(String loaiPhong);
}
