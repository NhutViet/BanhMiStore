package com.viethcn.duanandroid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Adapters.chitietsanphamAdapter;
import com.viethcn.duanandroid.Models.Productchitiet;

import java.util.ArrayList;
import java.util.List;

public class chitietsanpham extends AppCompatActivity {

    private RecyclerView recyclerView;
    private chitietsanphamAdapter productAdapter;
    private List<Productchitiet> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chitietsanpham); // Ensure this layout file exists

        recyclerView = findViewById(R.id.recyclerView);

        // Cài đặt GridLayoutManager với 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Tạo danh sách sản phẩm mẫu
        productList = new ArrayList<>();
        productList.add(new Productchitiet("Bánh bao đặc biệt", "35.000 VND", R.drawable.banhbao));
        productList.add(new Productchitiet("Chả lụa", "140.000 VND", R.drawable.chalua));
        productList.add(new Productchitiet(" Pate gan", "90.000 VND", R.drawable.pate));
        productList.add(new Productchitiet("Xá xíu heo quay", "30.000/lạng", R.drawable.thitheo));
        productList.add(new Productchitiet("Cơm cháy", "50.000 VND", R.drawable.comchay));
        productList.add(new Productchitiet("Giò thủ", "150.000 VND", R.drawable.comchay));

        // Thêm các sản phẩm khác vào productList...

        // Thiết lập Adapter cho RecyclerView
        productAdapter = new chitietsanphamAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
    }
}
