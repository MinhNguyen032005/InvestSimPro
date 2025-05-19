package lap.bai_2;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Album {
    private String tuaAlbum;
    private LocalTime ngayPhatHanh;
    private String hang;
    private List<Nhac> list;
    private String theLoai;

    public Nhac timKiemBanNhac(String[] value) {
        for (int i = 0; i < value.length; i++) {
            if (value[i].equals(theLoai)) {
                for (Nhac nhac : list) {
                    return nhac.timKiem(value[i]);
                }
            } else {
                for (Nhac nhac : list) {
                    return nhac.timKiem(value[i]);
                }
            }
        }
        return null;
    }

    public List<Nhac> sort() {
        list.sort(Comparator.comparing(Nhac::getTua).thenComparing(Nhac::getTenNhacSiSangTac).thenComparing(Comparator.comparing(Nhac::getCaSiBieuDien).reversed()));
        List<Nhac> re = new ArrayList<>(list);
        return re;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public List<Nhac> getList() {
        return list;
    }
}
