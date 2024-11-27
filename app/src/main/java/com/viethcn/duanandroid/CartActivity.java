package com.viethcn.duanandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class CartActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện người dùng
    private TextView tvCartTotal, tvTotalPayment, tvDeliveryFee, tvQuantity1, tvQuantity2;
    private ImageView btnIncrease1, btnDecrease1, btnIncrease2, btnDecrease2, btnDelete1, btnDelete2;
    private CheckBox toppingEgg1, toppingCheese1, toppingEgg2, toppingCheese2;
    private Button btnApplyPromo, btnProceedCheckout;
    private EditText etPromoCode;

    private int item1Quantity = 1;
    private int item2Quantity = 1;
    private int item1Price = 80000;
    private int item2Price = 80000;
    private int deliveryFee = 15000;
    private int discount = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện người dùng
        tvCartTotal = findViewById(R.id.tv_cart_total);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        tvDeliveryFee = findViewById(R.id.tv_delivery_fee);
        tvQuantity1 = findViewById(R.id.tv_quantity_1);
        tvQuantity2 = findViewById(R.id.tv_quantity_2);
        btnIncrease1 = findViewById(R.id.btn_increase_1);
        btnDecrease1 = findViewById(R.id.btn_decrease_1);
        btnIncrease2 = findViewById(R.id.btn_increase_2);
        btnDecrease2 = findViewById(R.id.btn_decrease_2);
        btnDelete1 = findViewById(R.id.ic_delete);
        btnDelete2 = findViewById(R.id.ic_delete_2);
        toppingEgg1 = findViewById(R.id.topping_egg_1);
        toppingCheese1 = findViewById(R.id.topping_cheese_1);
        toppingEgg2 = findViewById(R.id.topping_egg);
        toppingCheese2 = findViewById(R.id.topping_cheese);
        btnApplyPromo = findViewById(R.id.btn_apply_promo);
        btnProceedCheckout = findViewById(R.id.btn_proceed_checkout);
        etPromoCode = findViewById(R.id.et_promo_code);

        // Cập nhật giá trị ban đầu
        updateCartSummary();

        // Tăng/giảm số lượng cho món 1
        btnIncrease1.setOnClickListener(v -> {
            item1Quantity++;
            tvQuantity1.setText(String.valueOf(item1Quantity));
            updateCartSummary();
        });

        btnDecrease1.setOnClickListener(v -> {
            if (item1Quantity > 1) {
                item1Quantity--;
                tvQuantity1.setText(String.valueOf(item1Quantity));
                updateCartSummary();
            }
        });

        // Tăng/giảm số lượng cho món 2
        btnIncrease2.setOnClickListener(v -> {
            item2Quantity++;
            tvQuantity2.setText(String.valueOf(item2Quantity));
            updateCartSummary();
        });

        btnDecrease2.setOnClickListener(v -> {
            if (item2Quantity > 1) {
                item2Quantity--;
                tvQuantity2.setText(String.valueOf(item2Quantity));
                updateCartSummary();
            }
        });

        // Xóa món 1
        btnDelete1.setOnClickListener(v -> {
            item1Quantity = 0;
            updateCartSummary();
        });

        // Xóa món 2
        btnDelete2.setOnClickListener(v -> {
            item2Quantity = 0;
            updateCartSummary();
        });

        // Xử lý chọn topping cho món 1
        toppingEgg1.setOnCheckedChangeListener((buttonView, isChecked) -> updateCartSummary());
        toppingCheese1.setOnCheckedChangeListener((buttonView, isChecked) -> updateCartSummary());

        // Xử lý chọn topping cho món 2
        toppingEgg2.setOnCheckedChangeListener((buttonView, isChecked) -> updateCartSummary());
        toppingCheese2.setOnCheckedChangeListener((buttonView, isChecked) -> updateCartSummary());

        // Áp dụng mã giảm giá
        btnApplyPromo.setOnClickListener(v -> {
            String promoCode = etPromoCode.getText().toString();
            if (promoCode.equalsIgnoreCase("DISCOUNT10")) {
                discount = 10; // Giảm giá 10%
            } else {
                discount = 0;
            }
            updateCartSummary();
        });

        // Tiến hành thanh toán
        btnProceedCheckout.setOnClickListener(v -> {
            // Logic để chuyển sang màn hình thanh toán
            Toast.makeText(CartActivity.this, "Tiến hành thanh toán", Toast.LENGTH_SHORT).show();
        });
        // Tạo một Intent để chuyển sang màn hình CheckoutActivity
        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);

        // Truyền dữ liệu cần thiết (ví dụ như tổng giỏ hàng, phí giao hàng, v.v.)
        intent.putExtra("TOTAL_CART", tvCartTotal.getText().toString());
        intent.putExtra("TOTAL_PAYMENT", tvTotalPayment.getText().toString());
        intent.putExtra("DELIVERY_FEE", tvDeliveryFee.getText().toString());

        // Khởi động màn hình CheckoutActivity
        startActivity(intent);
    }

    private void updateCartSummary() {
        // Tính toán tổng giá trị giỏ hàng
        int item1Total = item1Price * item1Quantity;
        int item2Total = item2Price * item2Quantity;

        // Tính tổng giá trị các topping
        int toppingCost1 = (toppingEgg1.isChecked() ? 5000 : 0) + (toppingCheese1.isChecked() ? 7000 : 0);
        int toppingCost2 = (toppingEgg2.isChecked() ? 5000 : 0) + (toppingCheese2.isChecked() ? 7000 : 0);

        // Tính tổng chi phí
        int cartTotal = item1Total + item2Total + toppingCost1 + toppingCost2 + deliveryFee;
        int totalAfterDiscount = cartTotal - (cartTotal * discount / 100);

        // Cập nhật giao diện
        tvCartTotal.setText("Tổng giỏ hàng: " + NumberFormat.getInstance().format(cartTotal));
        tvDeliveryFee.setText("Phí giao hàng: " + NumberFormat.getInstance().format(deliveryFee));
        tvTotalPayment.setText("Tổng thanh toán: " + NumberFormat.getInstance().format(totalAfterDiscount));
    }
}

