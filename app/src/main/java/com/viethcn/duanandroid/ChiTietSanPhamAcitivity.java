package com.viethcn.duanandroid;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Adapters.ChiTietSanPhamAdapter;
import com.viethcn.duanandroid.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPhamAcitivity extends AppCompatActivity {

     RecyclerView recyclerView;
     ChiTietSanPhamAdapter productAdapter;
     List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham); // Ensure this layout file exists

        recyclerView = findViewById(R.id.recyclerViewSanPhamKhac);

        // Cài đặt GridLayoutManager với 2 cột
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false );
        recyclerView.setLayoutManager(layout);

        // Tạo danh sách sản phẩm mẫu
        productList = new ArrayList<>();
        productList.add(new Product( R.drawable.banhbao, "Bánh bao đặc biệt",35000));
        productList.add(new Product(R.drawable.chalua,"Chả lụa",140000));
        productList.add(new Product(R.drawable.pate,"Pate gan",90000));
        productList.add(new Product(R.drawable.thitheo,"Xá xíu heo quay",300000));
        productList.add(new Product(R.drawable.comchay,"Cơm cháy",50000));
        productList.add(new Product(R.drawable.giothu,"Giò thủ",150000));

        // Thêm các sản phẩm khác vào productList...

        // Thiết lập Adapter cho RecyclerView
        productAdapter = new ChiTietSanPhamAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
    }
}
