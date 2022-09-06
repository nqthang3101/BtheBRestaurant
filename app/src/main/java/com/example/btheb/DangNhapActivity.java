package com.example.btheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btheb.DAO.NhanVienDAO;
import com.example.btheb.DAO.QuyenDAO;
import com.example.btheb.DAO.NhanVienDAO;
import com.example.btheb.DAO.QuyenDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtTenDangNhapDN, edtMatKhauDN;
    Button btnDongYDN, btnDangKyDN;
    NhanVienDAO nhanVienDAO;
    int thoatapp;
    Intent iDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        edtTenDangNhapDN = findViewById(R.id.edtTenDangNhapDN);
        edtMatKhauDN = findViewById(R.id.edtMatKhauDN);
        btnDongYDN = findViewById(R.id.btnDongYDN);
        btnDangKyDN =  findViewById(R.id.btnDangKyDN);
        LanDauTien();

        thoatapp = getIntent().getIntExtra("thoatapp",0);
        if (thoatapp != 0) finish();

        btnDangKyDN.setVisibility(View.GONE);
        nhanVienDAO = new NhanVienDAO(this);

        btnDangKyDN.setOnClickListener(this);
        btnDongYDN.setOnClickListener(this);

        HienThiButtonDangKyVaDongY();
    }

    private void LanDauTien() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            QuyenDAO quyenDAO = new QuyenDAO(this);
            quyenDAO.ThemQuyen("Quản lý");
            quyenDAO.ThemQuyen("Nhân viên");
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
    }

    private void HienThiButtonDangKyVaDongY() {
            boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
            if (kiemtra) {
                btnDongYDN.setVisibility(View.VISIBLE);
                btnDangKyDN.setVisibility(View.GONE);
            } else {
                btnDongYDN.setVisibility(View.GONE);
                btnDangKyDN.setVisibility(View.VISIBLE);
            }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDongYDN: btnDongY();
                break;

            case R.id.btnDangKyDN:
                iDangKy = new Intent(DangNhapActivity.this,DangKyActivity.class);
                iDangKy.putExtra("dangnhap",1);
                startActivity(iDangKy);
                break;
        }
    }

    private void btnDongY() {
        String sTenDN = edtTenDangNhapDN.getText().toString();
        String sMatKhauDN = edtMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDN, sMatKhauDN);
        int maquyen = nhanVienDAO.LayMaQuyenNhanVien(kiemtra);
        if (kiemtra!=0) {

            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.commit();

            Intent iTrangChu = new Intent(getApplicationContext(),TrangChuActivity.class);
            iTrangChu.putExtra("tendn", edtTenDangNhapDN.getText().toString());
            iTrangChu.putExtra("manhanvien",kiemtra);
            startActivity(iTrangChu);
        }
        else Toast.makeText(DangNhapActivity.this,"Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDangKyVaDongY();
    }
}