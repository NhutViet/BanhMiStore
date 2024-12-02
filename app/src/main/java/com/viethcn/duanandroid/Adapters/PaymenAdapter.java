package com.viethcn.duanandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;
import com.viethcn.duanandroid.Repositories.MainModelRepository;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymenAdapter extends RecyclerView.Adapter<PaymenAdapter.PaymentViewHolder> {

    List<MainModel> mList;
    MainModelRepository repo;

    public PaymenAdapter(LiveData<List<MainModel>> liveData, LifecycleOwner lifecycleOwner) {
        liveData.observe(lifecycleOwner, updatedList -> {
            this.mList = updatedList;
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        repo = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(MainModelRepository.class);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {

        MainModel item = mList.get(position);

        holder.txtModelName.setText(item.getName());
        holder.txtModelPrice.setText(formatVND(Integer.parseInt(item.getPrice())));
        Glide.with(holder.imgItemPayout.getContext())
                .load(item.getImg())
                .placeholder(R.drawable.erroimage)
                .error(R.drawable.erroimage)
                .centerCrop()
                .into(holder.imgItemPayout);
        holder.txtModelQuantity.setText(String.valueOf(item.getQuantity()));

        // Event
        holder.txtIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            repo.updateQuanTityItem(item);
            notifyItemChanged(position);
        });

        holder.txtDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                repo.updateQuanTityItem(item);
                notifyItemChanged(position);
            }else {
                repo.removeItem(item);
                notifyItemRemoved(position);
            }
        });

        holder.txtRemove.setOnClickListener(v -> {
            repo.removeItem(item);
            notifyItemRemoved(position);
        });
    }

    private static String formatVND(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        formatter.setMaximumFractionDigits(0);
        return formatter.format(amount);
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