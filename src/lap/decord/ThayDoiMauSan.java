package lap.decord;

public class ThayDoiMauSan extends DichVu {


    public ThayDoiMauSan(XayNha xayNha) {
        super(xayNha);
    }

    @Override
    public double donGia() {
        return 500;
    }

    @Override
    public String mota() {
        return "Thay doi mau son";
    }
}
