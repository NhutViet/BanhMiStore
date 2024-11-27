package com.viethcn.duanandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etAddress, etPhone;
    private RadioGroup paymentOptions;
    private Button btnCheckout;
    private TextView  grandTotal, tvSelectedToppings;

    private int productPrice = 100000;  // Giá sản phẩm cơ bản
    private int shippingCost = 20000;   // Phí vận chuyển cố định
    private int toppingCost = 10000;     // Giả sử mỗi topping có giá 5000 VND

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Ánh xạ các thành phần giao diện
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        paymentOptions = findViewById(R.id.paymentOptions);
        btnCheckout = findViewById(R.id.btnCheckout);
        grandTotal = findViewById(R.id.grandTotal);
        tvSelectedToppings = findViewById(R.id.selectedToppings);
        LinearLayout subMenuCreditCard = findViewById(R.id.subMenuCreditCard);
        LinearLayout subMenuEWallet = findViewById(R.id.subMenuEWallet);

        // Nhận dữ liệu từ Intent
        int quantity = getIntent().getIntExtra("quantity", 1);
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0);

        boolean topping1 = getIntent().getBooleanExtra("topping1", false);
        boolean topping2 = getIntent().getBooleanExtra("topping2", false);
        boolean topping3 = getIntent().getBooleanExtra("topping3", false);
        boolean topping4 = getIntent().getBooleanExtra("topping4", false);
        boolean topping5 = getIntent().getBooleanExtra("topping5", false);

        // Lấy và hiển thị tên topping đã chọn
        StringBuilder toppings = new StringBuilder("Toppings: ");
        if (topping1) toppings.append("Thịt nguội, ");
        if (topping2) toppings.append("Chả lụa, ");
        if (topping3) toppings.append("Pate, ");
        if (topping4) toppings.append("Nước suối, ");
        if (topping5) toppings.append("Rau thêm, ");

        // Xóa dấu phẩy cuối cùng nếu có
        if (toppings.length() > 9) {
            toppings.setLength(toppings.length() - 2);
        } else {
            toppings.append("Không có");
        }

        tvSelectedToppings.setText(toppings.toString());

        // Hiển thị tùy chọn thanh toán
        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            subMenuCreditCard.setVisibility(View.GONE);
            subMenuEWallet.setVisibility(View.GONE);

            if (checkedId == R.id.rbCreditCard) {
                subMenuCreditCard.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbEWallet) {
                subMenuEWallet.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện cho nút "Hoàn tất thanh toán"
        btnCheckout.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPaymentId = paymentOptions.getCheckedRadioButtonId();
            if (selectedPaymentId == -1) {
                Toast.makeText(CheckoutActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedPaymentOption = findViewById(selectedPaymentId);
            String paymentMethod = selectedPaymentOption.getText().toString();

            Toast.makeText(CheckoutActivity.this, "Thanh toán thành công bằng " + paymentMethod, Toast.LENGTH_LONG).show();
            // Logic thanh toán và xác nhận đơn hàng ở đây
        });

        // Cập nhật thông tin tổng cộng khi có thay đổi về số lượng và topping
        updateTotal(quantity, topping1, topping2, topping3, topping4, topping5);
    }

    private void updateTotal(int quantity, boolean topping1, boolean topping2, boolean topping3, boolean topping4, boolean topping5) {
        // Tính toán tổng tiền sản phẩm, topping và phí vận chuyển
        int toppingCount = 0;
        if (topping1) toppingCount++;
        if (topping2) toppingCount++;
        if (topping3) toppingCount++;
        if (topping4) toppingCount++;
        if (topping5) toppingCount++;

        // Tổng giá trị sản phẩm
        int productTotal = productPrice * quantity;
        // Tổng phí topping
        int toppingTotal = toppingCount * toppingCost;
        // Tổng phí vận chuyển
        int total = productTotal + toppingTotal;
        int grandTotalAmount = total + shippingCost;

        grandTotal.setText("Tổng cộng: " + grandTotalAmount + " VND");
    }
}
