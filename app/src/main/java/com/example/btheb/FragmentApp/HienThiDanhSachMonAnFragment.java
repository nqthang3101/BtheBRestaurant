package com.example.btheb.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.btheb.SoLuongActivity;
import com.example.btheb.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.example.btheb.DAO.MonAnDAO;
import com.example.btheb.DTO.MonAnDTO;
import com.example.btheb.R;
import com.example.btheb.SoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {
    GridView gridView;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapter;
    MonAnDAO monAnDAO;
    int maloai;
    int maban = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,null);

        gridView = view.findViewById(R.id.grThucDon);
        Bundle bundle = getArguments();
        if (bundle!= null){
            maloai =  bundle.getInt("maloai");
            maban = bundle.getInt("maban");
            if (maloai !=0) { HienThiDanhSachMonAn();

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());
                        startActivity(iSoLuong);
                    }
                });
            }

            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                    return false;
                }
            });}

        return view;
    }

    private void HienThiDanhSachMonAn() {
        monAnDAO = new MonAnDAO(getActivity());
        monAnDTOList = monAnDAO.LayDSMonAnTheoLoai(maloai);
        adapter = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,monAnDTOList);
        gridView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiDanhSachMonAn();
    }
}