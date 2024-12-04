package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.List;

public class OrderProcessAdpater extends RecyclerView.Adapter<OrderProcessAdpater.OPviewHolder> {

    List<MainModel> mList;
    Context context;

    public OrderProcessAdpater(List<MainModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public OPviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_oder_process, parent, false);
        return new OPviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OPviewHolder holder, int position) {
        MainModel item = mList.get(position);

        String owner = "Họ tên: " + item.getName();
        holder.tvOpProductName.setText(owner);

        String price = "Giá: " + item.getPrice();
        holder.tvOpProductPrice.setText(price);

        String quant = "Số lượng: " + item.getQuantity();
        holder.tvOpProductQuant.setText(quant);

        Glide.with(context)
                .load(item.getImg())
                .placeholder(R.drawable.erroimage)
                .error(R.drawable.erroimage)
                .into(holder.imgOpProduct);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class OPviewHolder extends RecyclerView.ViewHolder{
        TextView tvOpProductName, tvOpProductPrice, tvOpProductQuant;
        ImageView imgOpProduct;
        public OPviewHolder(@NonNull View itemView) {
            super(itemView);
            tvOpProductName = itemView.findViewById(R.id.tvOpProductName);
            tvOpProductPrice = itemView.findViewById(R.id.tvOpProductPrice);
            tvOpProductQuant = itemView.findViewById(R.id.tvOpProductQuant);
            imgOpProduct = itemView.findViewById(R.id.imgOpProduct);
        }
    }
}
