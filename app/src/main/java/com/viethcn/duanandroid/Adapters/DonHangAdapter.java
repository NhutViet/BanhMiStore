package com.viethcn.duanandroid.Adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Fragments.OrderProcessFragment;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {

    private List<DonHang> donHangList;
    private FragmentActivity context;

    public DonHangAdapter(FragmentActivity context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);

        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {

        DonHang donHang = donHangList.get(position);
        holder.tvOwner.setText("Khách Hàng: " + donHang.getOwner());
        holder.tvAddress.setText("Địa Chỉ: " + donHang.getAddress());
        holder.tvPhone.setText("SĐT: " + donHang.getPhone());
        holder.tvTotal.setText("Tổng Bill: " + formatVND(donHang.getTotal()));
        holder.tvStatus.setText("Trạng Thái: " + donHang.getStatus());
        String create = "Ngày tạo: " +formatCurrentTimeMillis(donHang.getCreateAt());
        holder.tvCreateAt.setText(create);

        RcvDonHangAdapter DonHangAdapter = new RcvDonHangAdapter(donHang.getListProduct(), holder.itemView.getContext());
        holder.rcvProducts.setAdapter(DonHangAdapter);
        holder.rcvProducts.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        holder.itemView.setOnClickListener(v -> {
            OrderProcessFragment orderProcessFragment = new OrderProcessFragment(context);

            Bundle bundle = new Bundle();
            bundle.putString("owner", donHang.getOwner());
            bundle.putString("address", donHang.getAddress());
            bundle.putString("phone", donHang.getPhone());
            bundle.putString("status", donHang.getStatus());
            bundle.putDouble("total", donHang.getTotal());
            bundle.putString("reciptsID", donHang.getId());
            bundle.putString("note", donHang.getNote());
            bundle.putSerializable("list", (Serializable) donHang.getListProduct());
            orderProcessFragment.setArguments(bundle);

            context.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainViewHomePage, orderProcessFragment)
                    .addToBackStack(null)
                    .commit();

            this.notifyDataSetChanged();
        });
    }
    public static String formatCurrentTimeMillis(Double currentTimeMillis) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date date = new Date(currentTimeMillis.longValue());
        return formatter.format(date);
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

    public class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvOwner, tvAddress, tvPhone, tvTotal, tvStatus, tvCreateAt;
        RecyclerView rcvProducts;
        CardView selectedRecipts;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOwner = itemView.findViewById(R.id.owner);
            tvAddress = itemView.findViewById(R.id.address);
            tvPhone = itemView.findViewById(R.id.phone);
            tvTotal = itemView.findViewById(R.id.total);
            tvStatus = itemView.findViewById(R.id.status);
            tvCreateAt = itemView.findViewById(R.id.createTime);
            rcvProducts = itemView.findViewById(R.id.rcvProducts);
            selectedRecipts = itemView.findViewById(R.id.selectedRecipts);
        }
    }
}