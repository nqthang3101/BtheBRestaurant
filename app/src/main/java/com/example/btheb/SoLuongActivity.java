package com.example.btheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btheb.DAO.GoiMonDAO;
import com.example.btheb.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {
    int maban,mamonan;
     EditText edtThemSoLuong;
     Button btnDongYTSL;
     GoiMonDAO goiMonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_themsoluong);

        edtThemSoLuong = (EditText) findViewById(R.id.edtThemSoLuong);
        btnDongYTSL = (Button) findViewById(R.id.btnDongYTSL);

        Intent iMaBan = getIntent();
        maban = iMaBan.getIntExtra("maban",0);
        mamonan = iMaBan.getIntExtra("mamonan",0);
        goiMonDAO = new GoiMonDAO(this);
        btnDongYTSL.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        boolean kiemtra  = goiMonDAO.KiemTraMonAnDaTonTai(magoimon,mamonan);
        if (kiemtra){
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon,mamonan);
            int soluongmoi = Integer.parseInt(edtThemSoLuong.getText().toString());
            int tongsoluong = soluongcu + soluongmoi;

            if (edtThemSoLuong.getText().toString() == null || edtThemSoLuong.getText().toString().equals(""))
                Toast.makeText(SoLuongActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
            else  {
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);
            goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
                finish();
            }

        }
        else {
            if (edtThemSoLuong.getText().toString() == null || edtThemSoLuong.getText().toString().equals(""))
                Toast.makeText(SoLuongActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
            else  {
            int soluong = Integer.parseInt(edtThemSoLuong.getText().toString());

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluong);
            goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
                finish();
            }

        }

    }
}