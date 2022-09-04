package com.example.btheb.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btheb.DAO.LoaiMonAnDAO;
import com.example.btheb.DTO.LoaiMonAnDTO;
import com.example.btheb.R;

import java.util.List;

public class AdapterHienThiLoaiMonAnThucDon extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;
    LoaiMonAnDAO loaiThucAnDAO;

    public AdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
        loaiThucAnDAO = new LoaiMonAnDAO(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            view = layoutInflater.inflate(R.layout.custom_layout_hienloaimonan, parent, false);
            viewHolderHienThiLoaiThucDon.txtLoaiThucDon = view.findViewById(R.id.txtHienLoaiMonAn);
            viewHolderHienThiLoaiThucDon.imLoaiThucDon = view.findViewById(R.id.imHienThiMonAn);

            view.setTag(viewHolderHienThiLoaiThucDon);
        } else {
            viewHolderHienThiLoaiThucDon = (AdapterHienThiLoaiMonAnThucDon.ViewHolderHienThiLoaiThucDon)
                    view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        int maloai = loaiMonAnDTO.getMaLoaiMonAn();
        byte[] hinhanh = loaiThucAnDAO.LayHinhLoaiMonAn(maloai);

            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
            viewHolderHienThiLoaiThucDon.imLoaiThucDon.setImageBitmap(bitmap);

        viewHolderHienThiLoaiThucDon.txtLoaiThucDon.setText(loaiMonAnDTO.getTenLoaiMonAn());

        return view;
    }
    public class ViewHolderHienThiLoaiThucDon {
        ImageView imLoaiThucDon;
        TextView txtLoaiThucDon;
    }
}