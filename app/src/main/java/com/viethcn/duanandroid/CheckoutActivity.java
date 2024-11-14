// CheckoutActivity.java
package com.viethcn.duanandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.viethcn.duanandroid.R;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etAddress, etPhone;
    private RadioGroup paymentOptions;
    private Button btnCheckout;
    private TextView totalAmount, shippingFee, grandTotal;

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
        totalAmount = findViewById(R.id.totalAmount);
        shippingFee = findViewById(R.id.shippingFee);
        grandTotal = findViewById(R.id.grandTotal);

        // Tính toán và cập nhật tổng cộng
        updateTotal();

        // Xử lý sự kiện cho nút "Hoàn tất thanh toán"
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    private void updateTotal() {
        int productPrice = 100000;
        int quantity = 1;
        int shippingCost = 20000;
        int total = productPrice * quantity;
        int grandTotalAmount = total + shippingCost;

        totalAmount.setText("Thành tiền: " + total + " VND");
        shippingFee.setText("Phí vận chuyển: " + shippingCost + " VND");
        grandTotal.setText("Tổng cộng: " + grandTotalAmount + " VND");
    }
}
