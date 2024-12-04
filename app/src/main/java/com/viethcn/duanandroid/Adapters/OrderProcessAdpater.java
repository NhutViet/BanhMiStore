package com.viethcn.duanandroid.Adapters;

import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class OPviewHolder extends RecyclerView.ViewHolder{
        TextView tvItemOpName, tvItemOpPrice, tvItemOpQuantity;
        ImageView imgItemPayout;
        public OPviewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
