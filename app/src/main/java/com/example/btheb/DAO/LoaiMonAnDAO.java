package com.example.btheb.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    SQLiteDatabase db;

    public LoaiMonAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.open();


    }

    public boolean ThemLoaiThucAn(String tenloai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);
        long kiemtra = db.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if (kiemtra != 0) return true;
        else return false;
    }

    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn() {
        List<LoaiMonAnDTO> loaiMonAnDTOList = new ArrayList<LoaiMonAnDTO>();
        String truyvan = " Select * from " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoaiMonAn(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoaiMonAn(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_LOAIMONAN_TENLOAI)));
            loaiMonAnDTOList.add(loaiMonAnDTO);

            cursor.moveToNext();
        }
        return loaiMonAnDTOList;
    }

    public byte[] LayHinhLoaiMonAn (int maloai) {
        byte[] hinhanh = new byte[0];
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1 ";
        Cursor cursor = db.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hinhanh = cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhanh;
    }
}