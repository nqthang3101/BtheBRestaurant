package com.example.btheb.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.Database.CreateDatabase;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    SQLiteDatabase db;

    public NhanVienDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db  = createDatabase.open();
    }

    public long ThemNhanVien (NhanVienDTO nhanVienDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND,nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN,nhanVienDTO.getMAQUYEN());

        long kiemtra = db.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);
        return kiemtra;
    }

    public boolean KiemTraNhanVien () {
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = db.rawQuery(truyvan, null);
        if (cursor.getCount() != 0 )
            return true;
        else return false;
    }

    public int KiemTraDangNhap(String tennhanvien,String matkhau ){
        String truyvan = " SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERe " + CreateDatabase.TB_NHANVIEN_TENDN +  " = '" + tennhanvien + "' and " + CreateDatabase.TB_NHANVIEN_MATKHAU + " = '" + matkhau + "' ";
        Cursor cursor = db.rawQuery(truyvan, null);
       cursor.moveToFirst();
       int manhanvien = 0;
       while (!cursor.isAfterLast()) {
           manhanvien = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV));
           cursor.moveToNext();
       }
       return manhanvien;
    }

    public List<NhanVienDTO> LayTatCaNhanVien() {
        List<NhanVienDTO> nhanVienDTOS = new ArrayList<NhanVienDTO>();
        String truyvan = " Select * from " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_TENDN)));;
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_NGAYSINH)));;
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_CMND)));;
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MATKHAU)));;
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTOS.add(nhanVienDTO);
            cursor.moveToNext();
        }
        return nhanVienDTOS;
    }

    public boolean XoaNhanVien (int manv) {
        long kiemtra = db.delete(CreateDatabase.TB_NHANVIEN,CreateDatabase.TB_NHANVIEN_MANV + " = " +manv,null);
        if (kiemtra !=0 ) return true;
        else return false;

    }

    public NhanVienDTO LayTatCaNhanVienTheoMa(int manv) {
        String truyvan = " Select * from " + CreateDatabase.TB_NHANVIEN + " where " + CreateDatabase.TB_NHANVIEN_MANV + " = " + manv ;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        while (!cursor.isAfterLast()) {

            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_TENDN)));;
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_NGAYSINH)));;
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_CMND)));;
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MATKHAU)));;
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            cursor.moveToNext();
        }
        return nhanVienDTO;
    }

    public boolean SuaNhanVien (NhanVienDTO nhanVienDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND,nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());

        int kiemtra = db.update(CreateDatabase.TB_NHANVIEN, contentValues,CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMANV(),null);

        if (kiemtra !=0 )return true;
        else return false;
    }

    public int LayMaQuyenNhanVien (int manv) {
        int maquyen = 0;
        String truyvan = " select * from " + CreateDatabase.TB_NHANVIEN +" where "+ CreateDatabase.TB_NHANVIEN_MANV + " = " + manv;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maquyen =  cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }
        return maquyen;
    }

}
