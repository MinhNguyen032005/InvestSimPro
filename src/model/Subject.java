package model;

import lap.decord.ObServer;

public interface Subject {
    void addObServer(ObServer obServer);

    void removeObServer(ObServer obServer);

    void notiify();

    void updateThongBao(String ndTB);
}
