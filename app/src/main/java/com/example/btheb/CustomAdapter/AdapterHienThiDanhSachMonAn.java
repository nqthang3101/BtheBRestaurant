package com.example.btheb.CustomAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btheb.DTO.MonAnDTO;
import com.example.btheb.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<MonAnDTO> monAnDTOList;
    ViewHolderHienThiDanhSachMonAn viewHolder;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList) {
        this.context = context;
        this.layout = layout;
        this.monAnDTOList = monAnDTOList;
    }

    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTOList.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolderHienThiDanhSachMonAn();
            view = layoutInflater.inflate(R.layout.custom_layout_hienthidanhsachmonan,parent,false);

            viewHolder.imHinhMonAn = view.findViewById(R.id.imHinhDSMonAn);
            viewHolder.txtGiaMonAn = view.findViewById(R.id.txtGiaDSMonAn);
            viewHolder.txtTenMonAn = view.findViewById(R.id.txtTenDSMonAn);

            view.setTag(viewHolder);
        }
        else viewHolder = (ViewHolderHienThiDanhSachMonAn) view.getTag();

        MonAnDTO monAnDTO = monAnDTOList.get(position);

        byte[] hinhanh = monAnDTO.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
        viewHolder.imHinhMonAn.setImageBitmap(bitmap);

        viewHolder.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        viewHolder.txtGiaMonAn.setText("Giá : "+ monAnDTO.getGiaTien() + "đ");
        return view;
    }

    public class ViewHolderHienThiDanhSachMonAn {
        TextView txtTenMonAn, txtGiaMonAn;
        ImageView imHinhMonAn;
    }
}


