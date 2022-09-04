package com.example.btheb.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btheb.Database.CreateDatabase;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.DTO.QuyenDTO;
import com.example.btheb.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuyenDAO {
    SQLiteDatabase db;

    public QuyenDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db  = createDatabase.open();
    }

    public long ThemQuyen (String tenquyen) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN,tenquyen);
        long kiemtra = db.insert(CreateDatabase.TB_QUYEN, null, contentValues);
        return kiemtra;
    }

    public List<QuyenDTO> LayTatCaQuyen() {
        List<QuyenDTO> quyenDTOS = new ArrayList<QuyenDTO>();
        String truyvan = " Select * from " + CreateDatabase.TB_QUYEN;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QuyenDTO quyenDTO = new QuyenDTO();
            quyenDTO.setTenQuyen(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_QUYEN_TENQUYEN)));;
            quyenDTO.setMaQuyen(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyenDTOS.add(quyenDTO);
            cursor.moveToNext();
        }
        return quyenDTOS;
    }
}
