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
import com.example.btheb.R;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtTenBanAnTBA;
    Button btnDongYTBA;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_thembanan);

        edtTenBanAnTBA = (EditText) findViewById(R.id.edtTenBanAnTBA);
        btnDongYTBA = (Button) findViewById(R.id.btnDongYTBA);

        banAnDAO = new BanAnDAO(this);
        btnDongYTBA.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sTenBanAn = edtTenBanAnTBA.getText().toString();
        if (sTenBanAn == null || sTenBanAn.equals(""))
            Toast.makeText(ThemBanAnActivity.this, R.string.vuilongnhapdulieu, Toast.LENGTH_SHORT).show();
        else  {
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem", kiemtra);
        setResult(Activity.RESULT_OK,intent);
        finish();
        }

    }
}