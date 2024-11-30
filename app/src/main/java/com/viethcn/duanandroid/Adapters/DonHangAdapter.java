package com.viethcn.duanandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

// Adapter sử dụng FirebaseRecyclerAdapter
public class DonHangAdapter extends FirebaseRecyclerAdapter<DonHang, DonHangAdapter.DonHangViewHolder> {

    public DonHangAdapter(@NonNull FirebaseRecyclerOptions<DonHang> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DonHangViewHolder holder, int position, @NonNull DonHang model) {
        holder.tvProducts.setText("Sản phẩm: " + model.getListProduct());
        holder.tvProducts.setText("Giá: " + model.getListProduct() + " VND");
        holder.tvProducts.setText("Số lượng: " + model.getListProduct());
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new DonHangViewHolder(view);
    }

    public static class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvOwner, tvAddress, tvPhone, tvTotal, tvProducts;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOwner = itemView.findViewById(R.id.owner);
            tvAddress = itemView.findViewById(R.id.address);
            tvPhone = itemView.findViewById(R.id.phone);
            tvTotal = itemView.findViewById(R.id.total);
            tvProducts = itemView.findViewById(R.id.tvProducts);
        }
    }
}
