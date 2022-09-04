package com.example.btheb.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.btheb.ThemThucDonActivity;
import com.example.btheb.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.btheb.DAO.LoaiMonAnDAO;
import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.R;
import com.example.btheb.ThemBanAnActivity;
import com.example.btheb.ThemThucDonActivity;
import com.example.btheb.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {
    GridView gridView;
    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    LoaiMonAnDAO loaiThucAnDAO;
    FragmentManager fragmentManager ;
    int maban = 0;
    int maquyen;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,null);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        if (maquyen == 1) setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);
        fragmentManager = getActivity().getSupportFragmentManager();


        loaiThucAnDAO = new LoaiMonAnDAO(getActivity());
        gridView = view.findViewById(R.id.grThucDon);
        HienThiLoaiThucDon();

        Bundle bHienThiThucDon = getArguments();
        if (bHienThiThucDon!=null)
        {maban = bHienThiThucDon.getInt("maban",0);}

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonAnDTOList.get(position).getMaLoaiMonAn();

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putInt("maban", maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack("hienthiloai");
                transaction.replace(R.id.content,hienThiDanhSachMonAnFragment);
                transaction.commit();
            }
        });
        return view;


    }

    private void HienThiLoaiThucDon() {
        loaiMonAnDTOList = loaiThucAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_layout_hienloaimonan,loaiMonAnDTOList);
        gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
                MenuItem itThemBanAn = menu.add(1,R.id.itThemThucDon,1,R.string.themthucdon);
        itThemBanAn.setIcon(R.drawable.logodangnhap);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiLoaiThucDon();
    }
}