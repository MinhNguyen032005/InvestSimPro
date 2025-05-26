package model;

import lap.decord.ObServer;

public interface Subject {
    void attach(Observer o);     // Đăng ký người theo dõi

    void detach(Observer o);     // Gỡ người theo dõi

    void notifyObservers();      // Gửi thông báo đến tất cả

}
