package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.viethcn.duanandroid.Models.Product;
import com.viethcn.duanandroid.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Product> listProduct;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.listProduct = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.product_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product item = listProduct.get(position);

        holder.imgProduct.setImageResource(item.getImg());
        holder.txtName.setText(item.getName());
        String priceValue = "Giá: " + item.getPrice();
        holder.txtPrice.setText(priceValue);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
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