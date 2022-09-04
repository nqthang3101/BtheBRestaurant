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

import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DTO.BanAnDTO;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtSuaBanAn;
    Button btnDongYSBA;
    BanAnDAO banAnDAO;
    int maban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_suabanan);

        edtSuaBanAn = (EditText) findViewById(R.id.edtSuaBanAn);
        btnDongYSBA = (Button) findViewById(R.id.btnDongYSBA);

        maban = getIntent().getIntExtra("maban",0);
        String tenban = getIntent().getStringExtra("tenban");
        edtSuaBanAn.setText(tenban);
        banAnDAO = new BanAnDAO(this);
        btnDongYSBA.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tenban = edtSuaBanAn.getText().toString();
        if (tenban== null || tenban.equals(""))
            Toast.makeText(SuaBanAnActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
        else  {
            boolean kiemtra = banAnDAO.CapNhatLaiTenBan(maban, tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtra", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}