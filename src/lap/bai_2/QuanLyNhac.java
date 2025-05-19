package lap.bai_2;

import java.util.*;

public class QuanLyNhac {
    private List<Album> list;

    public Nhac timKiemBanNhac(String... value) {
        for (Album album : list) {
            return album.timKiemBanNhac(value);
        }
        return null;
    }

    public List<Nhac> sort() {
        List<Nhac> re = new ArrayList<>();
        list.sort(new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.getTheLoai().compareTo(o2.getTheLoai());
            }
        });
        for (Album album : list) {
            re.addAll(album.sort());

        }
        return re;
    }

    public List<Nhac> top10() {
        List<Nhac> result = new ArrayList<>();
        for (Album album : list) {
            result.addAll(album.getList());
        }
        result.sort((o1, o2) -> {
            int access1 = o1.getSoLanTruyCap();
            int access2 = o2.getSoLanTruyCap();
            return Integer.compare(access2, access1); // Giảm dần
        });
        return result.size() > 10 ? result.subList(0, 10) : result;
    }

}
