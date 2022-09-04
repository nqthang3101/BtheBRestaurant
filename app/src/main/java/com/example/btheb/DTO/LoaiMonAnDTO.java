package com.example.btheb.DTO;

public class LoaiMonAnDTO {
    int MaLoaiMonAn;
    String TenLoaiMonAn;
    byte[] HinhAnh;

    public LoaiMonAnDTO() {
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getMaLoaiMonAn() {
        return MaLoaiMonAn;
    }

    public void setMaLoaiMonAn(int maLoaiMonAn) {
        MaLoaiMonAn = maLoaiMonAn;
    }
    public String getTenLoaiMonAn() {
        return TenLoaiMonAn;
    }


    public void setTenLoaiMonAn(String tenLoaiMonAn) {
        TenLoaiMonAn = tenLoaiMonAn;
    }
}
