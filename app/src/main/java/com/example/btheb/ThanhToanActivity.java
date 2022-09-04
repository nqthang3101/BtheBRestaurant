package com.example.btheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DAO.GoiMonDAO;
import com.example.btheb.CustomAdapter.AdapterHienThiThanhToan;
import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DAO.GoiMonDAO;
import com.example.btheb.DTO.ThanhToanDTO;
import com.example.btheb.FragmentApp.HienThiBanAnFragment;
import com.example.btheb.R;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtTongTien;
    GridView gvThanhToan;
    Button btnThanhToan, btnThoatTT;
    GoiMonDAO goiMonDAO;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterHienThiThanhToan adapter;
    long tongtien = 0;
    BanAnDAO banAnDAO;
    int maban,magoimon;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);


        gvThanhToan = (GridView) findViewById(R.id.gvThanhToan);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnThoatTT = (Button) findViewById(R.id.btnThoatThanhToan);

        fragmentManager = getSupportFragmentManager();
        banAnDAO = new BanAnDAO(this);
        goiMonDAO = new GoiMonDAO(this);
        maban = getIntent().getIntExtra("maban",0);
        if (maban!=0) {

            HienThiThanhToan();

            for (int i=0; i< thanhToanDTOS.size(); i++){
                tongtien += thanhToanDTOS.get(i).getGiaTien() * thanhToanDTOS.get(i).getSoLuong();
            }

            txtTongTien.setText("Tổng cộng: " + String.valueOf(tongtien) + "đ");
        }

        btnThanhToan.setOnClickListener(this);
        btnThoatTT.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnThanhToan:
                    banAnDAO.CapNhatLaiTinhTrang(maban,"false");
                    goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban,"true");
                    HienThiThanhToan();
                    txtTongTien.setText("Tổng cộng: 0đ");
                    break;

                case R.id.btnThoatThanhToan:
                    finish();
                    break;
            }
    }

    private void HienThiThanhToan() {
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        thanhToanDTOS = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapter = new AdapterHienThiThanhToan(this,R.layout.custom_layout_hienthithucdon,thanhToanDTOS);
        gvThanhToan.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}