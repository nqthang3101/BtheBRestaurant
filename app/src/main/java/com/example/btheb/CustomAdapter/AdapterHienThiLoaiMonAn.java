package com.example.btheb.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.DTO.BanAnDTO;
import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.R;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter {
Context context;
int layout;
List<LoaiMonAnDTO> loaiMonAnDTOList;
ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
    }

    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonAnDTOList.get(position).getMaLoaiMonAn();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view== null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            view = layoutInflater.inflate(R.layout.custom_layout_spinloaithucdon,parent,false);
            viewHolderLoaiMonAn.txtLoaiTMonAn = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtLoaiTMonAn.setText(loaiMonAnDTO.getTenLoaiMonAn());
        viewHolderLoaiMonAn.txtLoaiTMonAn.setTag(loaiMonAnDTO.getMaLoaiMonAn());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view== null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            view = layoutInflater.inflate(R.layout.custom_layout_spinloaithucdon,parent,false);
            viewHolderLoaiMonAn.txtLoaiTMonAn = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtLoaiTMonAn.setText(loaiMonAnDTO.getTenLoaiMonAn());
        viewHolderLoaiMonAn.txtLoaiTMonAn.setTag(loaiMonAnDTO.getMaLoaiMonAn());

        return view;
    }
    public class ViewHolderLoaiMonAn {
        TextView txtLoaiTMonAn;
    }


}
