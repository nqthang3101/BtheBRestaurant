package com.example.btheb.CustomAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.DTO.NhanVienDTO;
import com.example.btheb.R;

import java.util.List;

public class AdapterHienThiNhanVien extends BaseAdapter{
    Context context;
    int layout;
    List<NhanVienDTO> nhanVienDTOS;
    ViewHolderNhanVien viewHolder;

    public AdapterHienThiNhanVien(Context context, int layout, List<NhanVienDTO> nhanVienDTOS) {
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOS = nhanVienDTOS;
    }

    @Override
    public int getCount() {
        return nhanVienDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return nhanVienDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nhanVienDTOS.get(position).getMANV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_hienthinhanvien,parent,false);
            viewHolder = new ViewHolderNhanVien();
            viewHolder.imgHinhNV = view.findViewById(R.id.imHinhNhanVien);
            viewHolder.txtCMND = view.findViewById(R.id.txtCMNDNV);
            viewHolder.txtTenNV = view.findViewById(R.id.txtTenNhanVien);

            view.setTag(viewHolder);
        }
        else viewHolder = (ViewHolderNhanVien) view.getTag();

        NhanVienDTO nhanVienDTO = nhanVienDTOS.get(position);
        viewHolder.txtTenNV.setText(nhanVienDTO.getTENDN());
        viewHolder.txtCMND.setText("DOB: " + nhanVienDTO.getNGAYSINH());

        return view;
    }
    public class ViewHolderNhanVien {
        ImageView imgHinhNV;
        TextView txtTenNV, txtCMND;
    }
}

