package com.example.btheb.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.btheb.FragmentApp.HienThiThucDonFragment;
import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DAO.GoiMonDAO;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.DTO.GoiMonDTO;
import com.example.btheb.FragmentApp.HienThiBanAnFragment;
import com.example.btheb.FragmentApp.HienThiThucDonFragment;
import com.example.btheb.R;
import com.example.btheb.ThanhToanActivity;
import com.example.btheb.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {
Context context;
int layout;
List<BanAnDTO> banAnDTOList;
ViewHolderBanAn viewHolderBanAn;
BanAnDAO banAnDAO;
GoiMonDAO goiMonDAO;
FragmentManager fragmentManager ;
int maban = 0;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList) {
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = layoutInflater.inflate(R.layout.custom_layout_hienthibanan,parent,false);

            viewHolderBanAn.imBanAn = view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imAnButton = view.findViewById(R.id.imAnButton);
            viewHolderBanAn.imGoiMon = view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan = view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);

            if(banAnDTOList.get(position).isDuocChon()){
                BanDuocChon();
            }else{
                AnButton(false);
            }
            view.setTag(viewHolderBanAn);
        }
        else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();

        BanAnDTO banAnDTO = banAnDTOList.get(position);

        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if (kttinhtrang.equals("true"))
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.thembanan);
        else viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);

        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imBanAn.setTag(position);
        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);

        if (banAnDTOList.get(position).isDuocChon()) BanDuocChon() ;

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        int vitri1 = (int) viewHolderBanAn.imBanAn.getTag();
        switch (id) {
            case R.id.imBanAn:
                maban = banAnDTOList.get(vitri1).getMaBan();

                int vitri = (int) v.getTag();
                banAnDTOList.get(vitri).setDuocChon(true);
                BanDuocChon();
                break;

            case R.id.imGoiMon:
                maban = banAnDTOList.get(vitri1).getMaBan();
                Intent layITrangChu = ((TrangChuActivity)context).getIntent();
                int manhanvien = layITrangChu.getIntExtra("manhanvien",0);
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if (tinhtrang.equals("false")) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngaythanhtoan = dateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maban);
                    goiMonDTO.setNgayGoi(ngaythanhtoan);
                    goiMonDTO.setMaNV(manhanvien);
                    goiMonDTO.setTinhTrang("false");

                    long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                    banAnDAO.CapNhatLaiTinhTrang(maban,"true");

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                    Bundle bDuLieuThucDon = new Bundle();
                    bDuLieuThucDon.putInt("maban",maban);
                    hienThiThucDonFragment.setArguments(bDuLieuThucDon);
                    fragmentTransaction.replace(R.id.content,hienThiThucDonFragment).addToBackStack("hienthithucdon").commit();
                }
                break;

            case R.id.imThanhToan:
                maban = banAnDTOList.get(vitri1).getMaBan();
                Intent ithanhToan = new Intent(context, ThanhToanActivity.class);
                ithanhToan.putExtra("maban",maban);
                context.startActivity(ithanhToan);
                break;

            case R.id.imAnButton:
                AnButton(true);
        }

    }

    private void BanDuocChon() {
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void AnButton(boolean hieuung){
        if(hieuung){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }

    public class ViewHolderBanAn {
        ImageView imBanAn, imGoiMon, imThanhToan, imAnButton;
        TextView txtTenBanAn;
    }

}
