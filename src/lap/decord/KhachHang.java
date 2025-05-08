package lap.decord;

public class KhachHang implements ObServer {
    private String maKH;
    private String tenKH;
    private XayNha xd;
    private boolean checkTB = false;

    public String HXD() {

        return null;
    }

    public double tt() {
        return xd.donGia();
    }

    @Override
    public void hienThiNDThongBao(String nd) {
        System.out.println(nd);
    }

    @Override
    public double pay() {
        if (!checkTB)
            return tt() * 0.95;
        return tt();
    }

    public void setCheckTB(boolean checkTB) {
        this.checkTB = checkTB;
    }

    @Override
    public String print() {
        return "";
    }

    @Override
    public boolean setDangKy(boolean b) {
        return checkTB=b;
    }
}
