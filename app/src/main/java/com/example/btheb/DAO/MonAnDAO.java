package com.example.btheb.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btheb.DTO.MonAnDTO;
import com.example.btheb.Database.CreateDatabase;
import com.example.btheb.DTO.MonAnDTO;
import com.example.btheb.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    SQLiteDatabase db;

    public MonAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.open();
}

public boolean ThemMonAn(MonAnDTO monAnDTO){
    ContentValues contentValues = new ContentValues();
    contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn() );
    contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
    contentValues.put(CreateDatabase.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
    contentValues.put(CreateDatabase.TB_MONAN_HINHANH,monAnDTO.getHinhAnh());


    long kiemtra = db.insert(CreateDatabase.TB_MONAN, null, contentValues);
    if (kiemtra !=0) return true; else return false;
}

    public List<MonAnDTO> LayDSMonAnTheoLoai (int maloai) {

        List<MonAnDTO> monAnDTOList = new ArrayList<MonAnDTO>();

        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maloai + "' ";
        Cursor cursor = db.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setHinhAnh( cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_HINHANH)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_MONAN_MALOAI)));
            monAnDTOList.add(monAnDTO);
            cursor.moveToNext();
        }
        return monAnDTOList;
    }
}
