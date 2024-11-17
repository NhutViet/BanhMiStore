package com.viethcn.duanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Models.Product;
import com.viethcn.duanandroid.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ThucDonAdapter extends RecyclerView.Adapter<ThucDonAdapter.MenuViewHolder> {

    private List<Product> mList; // Danh sách sản phẩm
    private Context context;

    // Constructor
    public ThucDonAdapter(List<Product> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Product product = mList.get(position);

        // Format giá thành chuỗi có đơn vị tiền tệ
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceFormatted = formatter.format(product.getPrice()) + " VND";

        // Gán dữ liệu vào các view
        holder.menuItemName.setText(product.getName());
        holder.menuItemPrice.setText(priceFormatted);
        holder.menuItemImage.setImageResource(product.getImg()); // Đảm bảo img là resource ID
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // ViewHolder đồng bộ với menu.xml
    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView menuItemName, menuItemPrice;
        ImageView menuItemImage;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuItemName = itemView.findViewById(R.id.menuItemName);
            menuItemPrice = itemView.findViewById(R.id.menuItemPrice);
            menuItemImage = itemView.findViewById(R.id.menuItemImage);
        }
    }
}

