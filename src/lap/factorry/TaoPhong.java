package lap.factorry;

public abstract class TaoPhong {
    public TaoPhong() {
    }

    public abstract Phong createPhongTB(String loaiPhong);
    public Phong taoPhong(String type){
        Phong phong= createPhongTB(type);
        System.out.println("Giai Doan Lap Rap Phong");
        System.out.println("Giao phong cho khach hang");
        return phong;
    }
}
