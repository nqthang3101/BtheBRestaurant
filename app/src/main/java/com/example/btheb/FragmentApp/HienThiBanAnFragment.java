package com.example.btheb.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btheb.CustomAdapter.AdapterHienThiBanAn;
import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.ThemBanAnActivity;
import com.example.btheb.TrangChuActivity;
import com.example.btheb.CustomAdapter.AdapterHienThiBanAn;
import com.example.btheb.DAO.BanAnDAO;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.R;
import com.example.btheb.SuaBanAnActivity;
import com.example.btheb.ThemBanAnActivity;
import com.example.btheb.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    GridView grHienBanAn;
    BanAnDAO banAnDAO;
    List<BanAnDTO> banAnDTOList;
    AdapterHienThiBanAn adapterHienThiBanAn;
    public static int REQUEST_CODE_THEM = 111;
    public static int REQUEST_CODE_SUA = 116;
    SharedPreferences sharedPreferences;
    int maquyen;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,null);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.banan);

        grHienBanAn = view.findViewById(R.id.gvHienBanAn);

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        if (maquyen == 1){
            setHasOptionsMenu(true);
            registerForContextMenu(grHienBanAn);
        }
        HienThiBanAn();

        return view;
    }

    private void HienThiBanAn() {
        banAnDAO = new BanAnDAO(getActivity());
        banAnDTOList= banAnDAO.LayTatCaBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(),R.layout.custom_layout_hienthibanan,banAnDTOList);
        grHienBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
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
        int maban = banAnDTOList.get(vitri).getMaBan();
        String tenban = banAnDTOList.get(vitri).getTenBan();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban",maban);
                intent.putExtra("tenban",tenban);
                startActivityForResult(intent,REQUEST_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
                if (kiemtra) {
                    Toast.makeText(getActivity(), R.string.xoathanhcong, Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), R.string.loi, Toast.LENGTH_SHORT).show();
                HienThiBanAn();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem itThemBanAn  = menu.add(1,R.id.itThemBanAn,1,R.string.thembanan);
        itThemBanAn.setIcon(R.drawable.thembanan);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,REQUEST_CODE_THEM);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEM) {
            if (requestCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem", false);
                if (kiemtra) {
                    Toast.makeText(getActivity(), R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), R.string.themthatbai, Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_CODE_SUA) {
            if (requestCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra", false);
                HienThiBanAn();
                if (kiemtra) {
                    Toast.makeText(getActivity(), R.string.suathanhcong, Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), R.string.loi, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HienThiBanAn();
    }
}