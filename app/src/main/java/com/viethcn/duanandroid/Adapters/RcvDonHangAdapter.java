package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.Models.Product;
import com.viethcn.duanandroid.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RcvDonHangAdapter extends RecyclerView.Adapter<RcvDonHangAdapter.DonHangVhd> {
    private List<MainModel> listproduct;
    private Context context;
    @NonNull
    @Override
    public DonHangVhd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang_adapter, parent, false);
        return new DonHangVhd(view);
    }

    public RcvDonHangAdapter(List<MainModel> listproduct, Context context) {
        this.listproduct = listproduct;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangVhd holder, int position) {
        MainModel item = listproduct.get(position);

        String mName = "TenSanPham: " + item.getName();
        holder.tvTenSp.setText(mName);

        String mQuant = "Soluong:  " + item.getQuantity();
        holder.tvSl.setText(mQuant);

        String mPrice = "GiaSanPham: " + formatVND(Integer.parseInt(item.getPrice()));
        holder.tvGiaSp.setText(mPrice);
    }

    private String formatVND(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        formatter.setMaximumFractionDigits(0);
        return formatter.format(amount);
    }

    @Override
    public int getItemCount() {
        return listproduct.size();
    }

    public static class DonHangVhd  extends RecyclerView.ViewHolder{
        TextView tvTenSp,tvGiaSp,tvSl;
        public DonHangVhd (@NonNull View itemView){
            super(itemView);
            tvTenSp = itemView.findViewById(R.id.tvTensp);
            tvGiaSp = itemView.findViewById(R.id.tvGiaSp);
            tvSl = itemView.findViewById(R.id.tvSl);
        }
    }

}

