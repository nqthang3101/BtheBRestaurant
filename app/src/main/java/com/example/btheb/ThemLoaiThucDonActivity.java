package com.example.btheb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btheb.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.btheb.DAO.LoaiMonAnDAO;
import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.R;

import java.util.List;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtTenBanAnTLTA;
    Button btnDongYTLTA;
    LoaiMonAnDAO loaiThucAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_themloaithucdon);

        edtTenBanAnTLTA = (EditText) findViewById(R.id.edtTenBanAnTLTA);
        btnDongYTLTA = (Button) findViewById(R.id.btnDongYTLTA);
        btnDongYTLTA.setOnClickListener(this);

        loaiThucAnDAO = new LoaiMonAnDAO(this);

    }

    @Override
    public void onClick(View v) {
        String sTenLoaiThucAn = edtTenBanAnTLTA.getText().toString();
        if (sTenLoaiThucAn != null || sTenLoaiThucAn.equals(""))
        {
            boolean kiemtra = loaiThucAnDAO.ThemLoaiThucAn(sTenLoaiThucAn);
            if (kiemtra) {
                Toast.makeText(this, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, R.string.themthatbai, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra("ketquathemloaithucan", kiemtra);
            setResult(Activity.RESULT_OK,intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            finish();
        }
    }
}