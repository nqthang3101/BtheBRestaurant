package com.example.btheb.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btheb.DTO.ThanhToanDTO;
import com.example.btheb.DTO.ThanhToanDTO;
import com.example.btheb.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter {
    Context context;
    int layout;
    List<ThanhToanDTO> thanhToanDTOS;
    ViewHolderThanhToan viewHolderThanhToan;
    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOS) {
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOS = thanhToanDTOS;
    }

    @Override
    public int getCount() {
        return thanhToanDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_hienthithucdon,parent,false);

            viewHolderThanhToan.txtGiaTien = view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuong = view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.txtTenMonAn = view.findViewById(R.id.txtTenMonAnThanhToan);
            view.setTag(viewHolderThanhToan);
        }
        else viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();

        ThanhToanDTO thanhToanDTO = thanhToanDTOS.get(position);
        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));
        return view;
    }

    public class ViewHolderThanhToan {
        TextView txtTenMonAn, txtGiaTien, txtSoLuong;
    }
}
