package com.viethcn.duanandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartActivity extends AppCompatActivity {

    private TextView tvQuantity, tvTotalPrice, tvProductPrice, tvProductName;
    private Button imgDecrease, imgIncrease, btnCheckout;
    private CheckBox cbTopping1, cbTopping2, cbTopping3, cbTopping4, cbTopping5;

    CircleImageView ivProductImage;

    private int quantity = 1; // Số lượng mặc định
    private double basePrice = 100000; // Giá bánh mì cơ bản
    private double totalPrice = basePrice; // Tổng giá

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Liên kết các view từ XML
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        imgDecrease = findViewById(R.id.imgDecrease);
        imgIncrease = findViewById(R.id.imgIncrease);
        btnCheckout = findViewById(R.id.btnCheckout);
        cbTopping1 = findViewById(R.id.cbTopping1);
        cbTopping2 = findViewById(R.id.cbTopping2);
        cbTopping3 = findViewById(R.id.cbTopping3);
        cbTopping4 = findViewById(R.id.cbTopping4);
        cbTopping5 = findViewById(R.id.cbTopping5);
        ivProductImage = findViewById(R.id.ivProductImage);

        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        String img = getIntent().getStringExtra("img");

        tvProductName.setText(name);
        tvProductPrice.setText(price);
        Glide.with(this).load(img).into(ivProductImage);

        // Cập nhật giá cơ bản
//        tvProductPrice.setText(String.format("Giá: %.2f VND", basePrice));

        // Xử lý sự kiện khi chọn topping
        View.OnClickListener toppingListener = v -> calculateTotalPrice();
        cbTopping1.setOnClickListener(toppingListener);
        cbTopping2.setOnClickListener(toppingListener);
        cbTopping3.setOnClickListener(toppingListener);
        cbTopping4.setOnClickListener(toppingListener);
        cbTopping5.setOnClickListener(toppingListener);

        // Nút tăng số lượng
        imgIncrease.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity)); // Cập nhật giao diện
            calculateTotalPrice(); // Tính lại tổng giá
        });

        // Nút giảm số lượng
        imgDecrease.setOnClickListener(v -> {
            if (quantity > 1) { // Không cho phép số lượng < 1
                quantity--;
                tvQuantity.setText(String.valueOf(quantity)); // Cập nhật giao diện
                calculateTotalPrice(); // Tính lại tổng giá
            }
        });

        btnCheckout.setOnClickListener(v -> {
            // Hiển thị thông báo Toast
            Toast.makeText(CartActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

            // Delay 1 giây trước khi chuyển Activity
            new android.os.Handler().postDelayed(() -> {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);

                // Truyền số lượng, tổng giá và topping
                intent.putExtra("quantity", quantity);
                intent.putExtra("totalPrice", totalPrice);

                // Truyền topping đã chọn
                intent.putExtra("topping1", cbTopping1.isChecked());
                intent.putExtra("topping2", cbTopping2.isChecked());
                intent.putExtra("topping3", cbTopping3.isChecked());
                intent.putExtra("topping4", cbTopping4.isChecked());
                intent.putExtra("topping5", cbTopping5.isChecked());

                // Chuyển đến CheckoutActivity
                startActivity(intent);
            }, 1000);
        });
    }

    // Phương thức tính tổng giá
    private void calculateTotalPrice() {
        double toppingsPrice = 0;

        // Kiểm tra từng topping
        if (cbTopping1.isChecked()) toppingsPrice += 10000;
        if (cbTopping2.isChecked()) toppingsPrice += 10000;
        if (cbTopping3.isChecked()) toppingsPrice += 10000;
        if (cbTopping4.isChecked()) toppingsPrice += 10000;
        if (cbTopping5.isChecked()) toppingsPrice += 10000;

        // Tổng giá = (giá cơ bản + giá topping) * số lượng
        totalPrice = (basePrice + toppingsPrice) * quantity;

        // Cập nhật tổng giá hiển thị
        tvTotalPrice.setText(String.format("Tổng tiền: %.2f VND", totalPrice));
    }
}
