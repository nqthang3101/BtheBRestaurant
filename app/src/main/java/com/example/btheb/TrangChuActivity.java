package com.example.btheb;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.btheb.FragmentApp.HienThiThucDonFragment;
import com.example.btheb.FragmentApp.HienThiBanAnFragment;
import com.example.btheb.FragmentApp.HienThiNhanVienFragment;
import com.example.btheb.FragmentApp.HienThiThucDonFragment;
import com.example.btheb.R;
import com.google.android.material.navigation.NavigationView;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView txtTenNV;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navTrangChu;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.toolbar);
        navTrangChu = (NavigationView) findViewById(R.id.navTrangChu);

        Intent intent = getIntent();
        String sTenNV = intent.getStringExtra("tendn");

        View view = navTrangChu.inflateHeaderView(R.layout.layout_header_navigation_trangchu) ;
        txtTenNV = view.findViewById(R.id.txtTenNV);
        txtTenNV.setText(sTenNV);

        toolbar.setTitleTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.mo,R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navTrangChu.setItemIconTintList(null);
        navTrangChu.setBackgroundColor(getResources().getColor(R.color.blue));
        navTrangChu.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        navTrangChu.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content,new HienThiBanAnFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
                tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                tranHienThiBanAn.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                tranHienThiThucDon.addToBackStack("ithucdon").commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itNhanVien:
                FragmentTransaction tranHienThiNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranHienThiNhanVien.replace(R.id.content,hienThiNhanVienFragment);
                tranHienThiNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                tranHienThiNhanVien.addToBackStack("inhanvien").commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itDangXuat:
                Intent intent = new Intent(TrangChuActivity.this,DangNhapActivity.class);
                intent.putExtra("thoatapp",1);
                startActivity(intent);
                finish();
        }
        return false;
    }
}