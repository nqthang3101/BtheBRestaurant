package com.example.btheb.Database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//KHỞI TẠO DATABASE
public class CreateDatabase extends SQLiteOpenHelper {

    public static final String TB_NHANVIEN = "NHANVIEN";
    public static final String TB_MONAN = "MONAN";
    public static final String TB_LOAIMONAN = "LOAIMONAN";
    public static final String TB_BANAN = "BANAN";
    public static final String TB_GOIMON = "GOIMON";
    public static final String TB_CHITIETGOIMON = "CHITIETGOIMON";
    public static final String TB_QUYEN = "QUYEN";

    public static final String TB_QUYEN_MAQUYEN = "MAQUYEN";
    public static final String TB_QUYEN_TENQUYEN = "TENQUYEN";

    public static final String TB_NHANVIEN_MANV = "MANV";
    public static String TB_NHANVIEN_MAQUYEN = "MAQUYEN";
    public static final String TB_NHANVIEN_TENDN = "TENDN";
    public static final String TB_NHANVIEN_MATKHAU = "MATKHAU";
    public static final String TB_NHANVIEN_GIOITINH = "GIOITINH";
    public static final String TB_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static final String TB_NHANVIEN_CMND = "CMND";

    public static final String TB_MONAN_MAMON = "MAMON";
    public static final String TB_MONAN_TENMONAN = "TENMONAN";
    public static final String TB_MONAN_GIATIEN = "GIATIEN";
    public static final String TB_MONAN_MALOAI = "MALOAI";
    public static final String TB_MONAN_HINHANH = "HINHANH";

    public static String TB_LOAIMONAN_MALOAI = "MALOAI";
    public static String TB_LOAIMONAN_TENLOAI = "TENLOAI";

    public static String TB_BANAN_MABAN = "MABAN";
    public static String TB_BANAN_TENBAN = "TENBAN";
    public static String TB_BANAN_TINHTRANG = "TINHTRANG";

    public static String TB_GOIMON_MAGOIMON = "MAGOIMON";
    public static String TB_GOIMON_MANV = "MANV";
    public static String TB_GOIMON_NGAYGOI = "NGAYGOI";
    public static String TB_GOIMON_TINHTRANG = "TINHTRANG";
    public static String TB_GOIMON_MABAN = "MABAN";

    public static String TB_CHITIETGOIMON_MAGOIMON = "MAGOIMON";
    public static String TB_CHITIETGOIMON_MAMONAN = "MAMONAN";
    public static String TB_CHITIETGOIMON_SOLUONG = "SOLUONG";

    //KHỞI TẠO DATABASE
    public CreateDatabase(@Nullable Context context) {
        super(context, "BtheB", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String tbNhanVien = "CREATE TABLE " + TB_NHANVIEN + " ( " + TB_NHANVIEN_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_NHANVIEN_TENDN + " TEXT, " + TB_NHANVIEN_MATKHAU + " TEXT, " + TB_NHANVIEN_NGAYSINH + " TEXT, " + TB_NHANVIEN_GIOITINH + " TEXT, " + TB_NHANVIEN_CMND + " INTEGER," + TB_NHANVIEN_MAQUYEN + " INTEGER )";
        String tbMonAn = "CREATE TABLE " + TB_MONAN + " ( " + TB_MONAN_MAMON  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_MONAN_TENMONAN + " TEXT, " + TB_MONAN_MALOAI + " TEXT, " + TB_MONAN_GIATIEN + " INTEGER, " + TB_MONAN_HINHANH + " BLOB )"  ;
        String tbQUYEN = "CREATE TABLE " + TB_QUYEN + " ( " + TB_QUYEN_MAQUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT," + TB_QUYEN_TENQUYEN + " TEXT )";
        String tbLoaiMonAn = "CREATE TABLE " + TB_LOAIMONAN + " ( " + TB_LOAIMONAN_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_LOAIMONAN_TENLOAI + " TEXT )" ;
        String tbBanAn =  "CREATE TABLE " + TB_BANAN + " ( " + TB_BANAN_MABAN  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_BANAN_TENBAN + " TEXT, " + TB_BANAN_TINHTRANG + " TEXT )" ;
        String tbGoiMon = "CREATE TABLE " + TB_GOIMON + " ( " + TB_GOIMON_MAGOIMON + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_GOIMON_MABAN + " INTEGER , " + TB_GOIMON_MANV + " INTEGER , " + TB_GOIMON_NGAYGOI + " TEXT, " + TB_GOIMON_TINHTRANG + " TEXT )" ;
        String tbChiTietGoiMon = "CREATE TABLE " + TB_CHITIETGOIMON + " ( " + TB_CHITIETGOIMON_SOLUONG + " INTEGER ," + TB_CHITIETGOIMON_MAMONAN + " INTEGER, " + TB_CHITIETGOIMON_MAGOIMON + " INTEGER, " + "PRIMARY key ( " + TB_CHITIETGOIMON_MAMONAN + " , " + TB_CHITIETGOIMON_MAGOIMON + " )) ";

        db.execSQL(tbNhanVien);
        db.execSQL(tbMonAn);
        db.execSQL(tbLoaiMonAn);
        db.execSQL(tbBanAn);
        db.execSQL(tbGoiMon);
        db.execSQL(tbChiTietGoiMon);
        db.execSQL(tbQUYEN);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

//KHỞI TẠO DATABASE

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
