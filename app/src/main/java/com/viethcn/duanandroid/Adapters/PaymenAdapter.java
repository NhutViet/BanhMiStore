package com.viethcn.duanandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.List;

public class PaymenAdapter extends RecyclerView.Adapter<PaymenAdapter.PaymentViewHolder> {

    List<MainModel> mList;

    public PaymenAdapter(List<MainModel> products) {
        this.mList = products;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImg;
        TextView txtName, txtPrice, txtQuanti;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cartImg);
        }
    }
}