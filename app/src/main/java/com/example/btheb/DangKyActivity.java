package com.example.btheb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btheb.DAO.NhanVienDAO;
import com.example.btheb.DAO.QuyenDAO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.DTO.QuyenDTO;
import com.example.btheb.FragmentApp.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText edtTenDangNhapDK, edtMatKhauDK, edtNgaySinhDK, edtCMND;
    RadioButton rdNam, rdNu;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    String sGioiTinh;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    TextView txtDangKy;
    int manv = 0;
    int landautien = 0;
    Spinner spQuyen;
    List<QuyenDTO> quyenDTOS;
    List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        edtTenDangNhapDK = findViewById(R.id.edtTenDangNhapDK);
        edtMatKhauDK = findViewById(R.id.edtMatKhauDK);
        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);
        edtNgaySinhDK = findViewById(R.id.edtNgaySinhDK);
        edtCMND = findViewById(R.id.edtCMND);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        txtDangKy = findViewById(R.id.txtDangKy);
        spQuyen = findViewById(R.id.spQuyen);
        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        dataAdapter = new ArrayList<String>();
        quyenDTOS = quyenDAO.LayTatCaQuyen();
        for (int i = 0; i<quyenDTOS.size();i++)
        {
            String tenquyen = quyenDTOS.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        manv = getIntent().getIntExtra("manv",0);
        landautien = getIntent().getIntExtra("landautien",0);

        if (landautien == 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.custom_spinner_item,dataAdapter);
            spQuyen.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else spQuyen.setVisibility(View.GONE);

        if (manv!=0) {
            txtDangKy.setText(R.string.capnhatnhanvien);
            spQuyen.setVisibility(View.GONE);
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayTatCaNhanVienTheoMa(manv);
            edtTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edtMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edtCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
            edtNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());

            if (nhanVienDTO.getGIOITINH().equals("Nam")) {
                rdNam.setChecked(true);
            } else {
                rdNu.setChecked(true);
            }
        }
        nhanVienDAO = new NhanVienDAO(this);
        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edtNgaySinhDK.setOnFocusChangeListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDongYDK:
                if (manv != 0 ) {
                    SuaNhanVien();
                }
                else {
                    ThemNhanVien();
                }
                break;

            case R.id.btnThoatDK:
                finish();
                break;
        }
    }

    private void ThemNhanVien() {
        String sTenDangNhap = edtTenDangNhapDK.getText().toString();
        String sMatKhau = edtMatKhauDK.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam: sGioiTinh = "Nam";
            case R.id.rdNu: sGioiTinh = "Nữ";
                String sNgaySinh = edtNgaySinhDK.getText().toString();

                 if (sTenDangNhap == null || sTenDangNhap.equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.loinhaptendangnhap, Toast.LENGTH_SHORT).show();
                else if (sMatKhau == null || sMatKhau.equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.loinhapmatkhau, Toast.LENGTH_SHORT).show();
                else if (edtCMND.getText().toString() == null || edtCMND.getText().toString().equals(""))
                     Toast.makeText(DangKyActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
                else if (sNgaySinh == null || sNgaySinh.equals(""))
                     Toast.makeText(DangKyActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
                else {
                     int sCMND = Integer.parseInt(edtCMND.getText().toString());
                    NhanVienDTO nhanVienDTO = new NhanVienDTO();
                    nhanVienDTO.setTENDN(sTenDangNhap);
                    nhanVienDTO.setMATKHAU(sMatKhau);
                    nhanVienDTO.setGIOITINH(sGioiTinh);
                    nhanVienDTO.setCMND(sCMND);
                    nhanVienDTO.setNGAYSINH(sNgaySinh);
                    if (landautien!= 0 ) {
//                        quyenDAO.ThemQuyen("Quản lý");
//                        quyenDAO.ThemQuyen("Nhân viên");
                        nhanVienDTO.setMAQUYEN(1);}
                    else {
                        int vitri = spQuyen.getSelectedItemPosition();
                        int maquyen = quyenDTOS.get(vitri).getMaQuyen();
                        nhanVienDTO.setMAQUYEN(maquyen);
                    }
                    long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
                     if (kiemtra != 0) Toast.makeText(this, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                     else Toast.makeText(this, R.string.loi, Toast.LENGTH_SHORT).show();
                }}
    }

    private void SuaNhanVien() {
        String sTenDangNhap = edtTenDangNhapDK.getText().toString();
        String sMatKhau = edtMatKhauDK.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam: sGioiTinh = "Nam";
            case R.id.rdNu: sGioiTinh = "Nữ";
                String sNgaySinh = edtNgaySinhDK.getText().toString();

                if (sTenDangNhap == null || sTenDangNhap.equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.loinhaptendangnhap, Toast.LENGTH_SHORT).show();
                else if (sMatKhau == null || sMatKhau.equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.loinhapmatkhau, Toast.LENGTH_SHORT).show();
                else if (edtCMND.getText().toString() == null || edtCMND.getText().toString().equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
                else if (sNgaySinh == null || sNgaySinh.equals(""))
                    Toast.makeText(DangKyActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
                else {
                    int sCMND = Integer.parseInt(edtCMND.getText().toString());

                    NhanVienDTO nhanVienDTO = new NhanVienDTO();
                    nhanVienDTO.setMANV(manv);
                    nhanVienDTO.setTENDN(sTenDangNhap);
                    nhanVienDTO.setMATKHAU(sMatKhau);
                    nhanVienDTO.setCMND(sCMND);
                    nhanVienDTO.setNGAYSINH(sNgaySinh);
                    nhanVienDTO.setGIOITINH(sGioiTinh);

                    boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
                    if (kiemtra) Toast.makeText(this, R.string.suathanhcong, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, R.string.loi, Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (edtNgaySinhDK.hasFocus()) {DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "Ngay sinh");
        }
    }
}