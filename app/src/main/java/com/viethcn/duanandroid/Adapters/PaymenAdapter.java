package com.viethcn.duanandroid.Adapters;

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

        MainModel item = mList.get(position);

        holder.txtModelName.setText(item.getName());
        holder.txtModelPrice.setText(item.getPrice());
        Glide.with(holder.imgItemPayout.getContext())
                .load(item.getImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .centerCrop()
                .into(holder.imgItemPayout);
        holder.txtModelQuantity.setText(String.valueOf(item.getQuantity()));

        // Event
        holder.txtIncrease.setOnClickListener(v -> increaseQuantity(position));
        holder.txtDecrease.setOnClickListener(v -> decreaseQuantity(position));
        holder.txtRemove.setOnClickListener(v -> removeItem(position));

    }

    private void increaseQuantity(int position) {
        MainModel item = mList.get(position);
        item.setQuantity(item.getQuantity() + 1);
        notifyItemChanged(position);
    }
    private void decreaseQuantity(int position) {
        MainModel item = mList.get(position);
        if (item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            notifyItemChanged(position);
        }
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public int calcTotal(){
        int total = 0;
        for (MainModel item : mList) {
            total += Integer.parseInt(item.getPrice()) * item.getQuantity();
        }
        return total;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItemPayout;
        TextView txtIncrease,
                txtModelQuantity,
                txtDecrease,
                txtModelName,
                txtModelPrice, txtRemove;

        public PaymentViewHolder (@NonNull View itemView) {
            super(itemView);
            InitUI();
        }
        private void InitUI(){
            txtIncrease = itemView.findViewById(R.id.txtPayoutIncreaseQuantity);
            txtModelQuantity = itemView.findViewById(R.id.txtPayoutItemQuantity);
            txtDecrease = itemView.findViewById(R.id.txtPayoutDecreaseQuantity);
            txtModelName = itemView.findViewById(R.id.txtPayoutProductName);
            txtModelPrice = itemView.findViewById(R.id.txtPayoutProductPrice);
            txtRemove = itemView.findViewById(R.id.txtPayoutRemove);

            imgItemPayout = itemView.findViewById(R.id.imgItemPayout);
        }
    }
}