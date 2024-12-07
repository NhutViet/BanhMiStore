package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<MainModel> mList;

    public ProductAdapter(Context context, List<MainModel> productList) {
        this.context = context;
        this.mList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.product_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        MainModel item = mList.get(position);

        Glide.with(holder.imgProduct.getContext())
                .load(item.getImg())
                .placeholder(R.drawable.erroimage)
                .error(R.drawable.erroimage)
                .centerCrop()
                .into(holder.imgProduct);

        holder.txtName.setText(item.getName());
        String priceValue = "Giá: " + item.getPrice();
        holder.txtPrice.setText(priceValue);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ProductViewHolder extends ViewHolder {

        ImageView imgProduct;
        TextView txtName, txtPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.img_product);
            txtName = itemView.findViewById(R.id.txt_prodcut_name);
            txtPrice = itemView.findViewById(R.id.txt_prodcut_price);

        }

    }
}