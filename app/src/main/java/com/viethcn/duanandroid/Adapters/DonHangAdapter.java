package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.List;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {
    private List<DonHang> donHangList;
    private Context context;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_don_hang, parent, false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        holder.tvOwner.setText("Khách Hàng: " + donHang.getOwner());
        holder.tvAddress.setText("Địa Chỉ: " + donHang.getAddress());
        holder.tvPhone.setText("SĐT: " + donHang.getPhone());
        holder.tvTotal.setText("Tổng Bill: " + formatVND(donHang.getTotal()));

        RcvDonHangAdapter DonHangAdapter = new RcvDonHangAdapter(donHang.getListProduct(), context);
        holder.rcvProducts.setAdapter(DonHangAdapter);
        holder.rcvProducts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    private String formatVND(Double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        formatter.setMaximumFractionDigits(2);
        return formatter.format(amount);
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public static class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvOwner, tvAddress, tvPhone, tvTotal;
        RecyclerView rcvProducts;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOwner = itemView.findViewById(R.id.owner);
            tvAddress = itemView.findViewById(R.id.address);
            tvPhone = itemView.findViewById(R.id.phone);
            tvTotal = itemView.findViewById(R.id.total);
            rcvProducts = itemView.findViewById(R.id.rcvProducts);
        }
    }
}
