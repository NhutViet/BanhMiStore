package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Models.Product;
import com.viethcn.duanandroid.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ChiTietSanPhamAdapter extends RecyclerView.Adapter<ChiTietSanPhamAdapter.ProductViewHolder> {

     List<Product> mList;
     Context context;

    // Constructor sửa lại một tham số
    public ChiTietSanPhamAdapter(List<Product> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productchitietsanpham, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mList.get(position);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceView = formatter.format(product.getPrice()) + " VND";

        holder.productName.setText(product.getName());
        holder.productPrice.setText(priceView);
        holder.productImage.setImageResource(product.getImg());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Button btnBuyNow;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
        }
    }
}
