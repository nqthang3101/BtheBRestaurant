package com.example.btheb.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btheb.Database.CreateDatabase;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {
    SQLiteDatabase db;

    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        db = createDatabase.open();


    }
    public boolean ThemBanAn (String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,"false");

        long kiemtra = db.insert(CreateDatabase.TB_BANAN, null, contentValues);
        if (kiemtra !=0) return true; else return false;
    }

    public List<BanAnDTO> LayTatCaBanAn() {
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String truyvan = " Select * from " + CreateDatabase.TB_BANAN;
        Cursor cursor = db.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_TENBAN)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang = "";
        String truyvan = "Select * from " + CreateDatabase.TB_BANAN +" where " + CreateDatabase.TB_BANAN_MABAN + " = " + "'" + maban + "' ";
        Cursor cursor = db.rawQuery(truyvan, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            tinhtrang = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhtrang;
    }

    public boolean CapNhatLaiTinhTrang(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,tinhtrang);

        long kiemtra = db.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);

        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean XoaBanAnTheoMa(int maban) {
        long kiemtra = db.delete(CreateDatabase.TB_BANAN,CreateDatabase.TB_GOIMON_MABAN + " = " + maban,null);
        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean CapNhatLaiTenBan(int maban, String tenban) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,tenban);

        long kiemtra = db.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);

        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }
}
