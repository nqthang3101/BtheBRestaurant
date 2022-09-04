package com.example.btheb.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btheb.CustomAdapter.AdapterHienThiNhanVien;
import com.example.btheb.DAO.NhanVienDAO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.DangKyActivity;
import com.example.btheb.TrangChuActivity;
import com.example.btheb.CustomAdapter.AdapterHienThiNhanVien;
import com.example.btheb.DAO.NhanVienDAO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.DangKyActivity;
import com.example.btheb.R;
import com.example.btheb.TrangChuActivity;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {
    ListView lv;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOS;
    SharedPreferences sharedPreferences;
    int maquyen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);
        lv = view.findViewById(R.id.listViewNhanVien);
        nhanVienDAO = new NhanVienDAO(getActivity());
        HienThiDSNhanVien();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        if (maquyen == 1){
            setHasOptionsMenu(true);
            registerForContextMenu(lv);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOS.get(vitri).getMANV();
        switch (id){
            case  R.id.itSua:
                Intent intent = new Intent(getActivity(), DangKyActivity.class);
                intent.putExtra("manv",manhanvien);
                startActivity(intent);
                break;
            case R.id.itXoa:
                boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
                if (kiemtra) {
                    HienThiDSNhanVien();
                    Toast.makeText(getActivity(), R.string.xoathanhcong, Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), R.string.loi, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void HienThiDSNhanVien() {
        nhanVienDTOS = nhanVienDAO.LayTatCaNhanVien();
        AdapterHienThiNhanVien adapter = new AdapterHienThiNhanVien(getActivity(),R.layout.custom_layout_hienthinhanvien,nhanVienDTOS);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemNhanVien = menu.add(1,R.id.itThemNhanVien,1,R.string.themnhanvien);
        itThemNhanVien.setIcon(R.drawable.addemployee);
        itThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiDSNhanVien();
    }
}