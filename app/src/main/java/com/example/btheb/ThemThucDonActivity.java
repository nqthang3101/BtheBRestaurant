package com.example.btheb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btheb.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.btheb.DAO.LoaiMonAnDAO;
import com.example.btheb.DAO.MonAnDAO;
import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.DTO.MonAnDTO;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtTenMonTTD, edtGiaTTD;
    Spinner spLoaiMonAn;
    ImageButton imbtnThemThucDon;
    Button btnDongYTTD, btnThoatTTD;
    ImageView imHinhThucDon;
    String sDuongDanHinh;
    MonAnDAO monAnDAO;
    int maloai = 0;

    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    LoaiMonAnDAO loaiThucAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        edtTenMonTTD = (EditText) findViewById(R.id.edtTenMonTTD);
        edtGiaTTD = (EditText) findViewById(R.id.edtGiaTTD);
        spLoaiMonAn = (Spinner) findViewById(R.id.spLoaiMonAn);
        imbtnThemThucDon = (ImageButton) findViewById(R.id.imbtnThemThucDon);
        btnDongYTTD = (Button) findViewById(R.id.btnDongYTTD);
        btnThoatTTD = (Button) findViewById(R.id.btnThoatTTD);
        imHinhThucDon = findViewById(R.id.imHinhThucDon);

        monAnDAO = new MonAnDAO(this);
        loaiThucAnDAO = new LoaiMonAnDAO(this);
        imbtnThemThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYTTD.setOnClickListener(this);
        btnThoatTTD.setOnClickListener(this);

        HienThiSpinnerLoaiMonAn();
    }

    private void HienThiSpinnerLoaiMonAn() {
        loaiMonAnDTOList = loaiThucAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(this, R.layout.custom_layout_spinloaithucdon, loaiMonAnDTOList);
        spLoaiMonAn.setAdapter(adapterHienThiLoaiMonAn);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDongYTTD:
                int vitri = spLoaiMonAn.getSelectedItemPosition();
                if (vitri >= 0) {
                maloai = loaiMonAnDTOList.get(vitri).getMaLoaiMonAn();}
                String tenmonan = edtTenMonTTD.getText().toString();
                String giatien = edtGiaTTD.getText().toString();

                if (tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("") && maloai != 0){
                MonAnDTO monAnDTO = new MonAnDTO();
                monAnDTO.setGiaTien(giatien);
                monAnDTO.setMaLoai(maloai);
                monAnDTO.setHinhAnh(imageViewtoByte(imHinhThucDon));

                monAnDTO.setTenMonAn(tenmonan);

                boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if (kiemtra) {
                        Toast.makeText(ThemThucDonActivity.this, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ThemThucDonActivity.this, R.string.themthatbai, Toast.LENGTH_SHORT).show();
                }
                else  Toast.makeText(ThemThucDonActivity.this, R.string.loithemmonan, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnThoatTTD:
                finish();
                break;

            case R.id.imbtnThemThucDon:
                Intent iThemLoaiThucDon = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiThucDon, REQUEST_CODE_THEMLOAITHUCDON);
                break;

            case R.id.imHinhThucDon:
                Intent iMoHinh = new Intent(Intent.ACTION_PICK);
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình thực đơn"), REQUEST_CODE_MOHINH);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON) {
            if (requestCode == Activity.RESULT_OK) {
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("ketquathemloaithucan", false);
                if (kiemtra) {
                    Toast.makeText(ThemThucDonActivity.this, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ThemThucDonActivity.this, R.string.themthatbai, Toast.LENGTH_SHORT).show();
            }
        } else if (REQUEST_CODE_MOHINH == requestCode) {
            Uri uri = data.getData();
            sDuongDanHinh = data.getData().toString();
            if (uri != null) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imHinhThucDon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else Toast.makeText(ThemThucDonActivity.this, R.string.loi, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiSpinnerLoaiMonAn();
    }

    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}