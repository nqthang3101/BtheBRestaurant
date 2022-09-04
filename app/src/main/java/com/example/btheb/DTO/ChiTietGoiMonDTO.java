package com.example.btheb.DTO;

public class ChiTietGoiMonDTO {
    public int getMaMonAn() {
        return MaMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        MaMonAn = maMonAn;
    }

    public int getMaGoiMon() {
        return MaGoiMon;
    }

    public void setMaGoiMon(int maGoiMon) {
        MaGoiMon = maGoiMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    int MaMonAn, MaGoiMon, SoLuong;
}
