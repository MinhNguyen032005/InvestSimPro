package lap.decord;

public abstract class DichVu extends XayNha {
    protected XayNha xayNha;

    public DichVu(XayNha xayNha) {
        this.xayNha = xayNha;
    }

    public abstract double donGia();
    public abstract String mota();
}
