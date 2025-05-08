package lap.decord;

public class ThietKeCanhQuan extends DichVu{


    public ThietKeCanhQuan(XayNha xayNha) {
        super(xayNha);
    }

    @Override
    public double donGia() {
        return 1000;
    }

    @Override
    public String mota() {
        return "Thiet ke canh quan";
    }
}
