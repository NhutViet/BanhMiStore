package com.viethcn.duanandroid;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView paymentRecyclerView;
    private TextView totalAmountTextView;
    private EditText cartBillOwner, cartBillAddressOwner, cartBillPhoneOwner;
    private RadioGroup paymentOptions;
    private RadioButton rdbCreditCard, rdbCashOnDelivery, rdbEWallet;
    private LinearLayout subMenuEWallet;
    private Spinner eWalletSpinner;
    private EditText etVoucher, etNote;
    private Button btnCheckout;

    private double totalAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        paymentRecyclerView = findViewById(R.id.paymentRcV);
        totalAmountTextView = findViewById(R.id.totalAmount);
        cartBillOwner = findViewById(R.id.cartBillOwner);
        cartBillAddressOwner = findViewById(R.id.cartBillAddressOwner);
        cartBillPhoneOwner = findViewById(R.id.cartBillPhoneOwner);
        paymentOptions = findViewById(R.id.paymentOptions);
        rdbCreditCard = findViewById(R.id.rdbCreditCard);
        rdbCashOnDelivery = findViewById(R.id.rdbCashOnDelivery);
        rdbEWallet = findViewById(R.id.rdbEWallet);
        subMenuEWallet = findViewById(R.id.subMenuEWallet);
        eWalletSpinner = findViewById(R.id.eWalletSpinner);
        etVoucher = findViewById(R.id.etVoucher);
        etNote = findViewById(R.id.etNote);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Set up RecyclerView
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Set up adapter for RecyclerView if necessary, e.g., paymentRecyclerView.setAdapter(adapter);

        // Update total amount
        updateTotalAmount();

        // Handle radio button selection
        paymentOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rdbEWallet) {
                subMenuEWallet.setVisibility(View.VISIBLE);
            } else {
                subMenuEWallet.setVisibility(View.GONE);
            }
        });

        // Handle checkout button click
        btnCheckout.setOnClickListener(v -> handleCheckout());
    }

    private void updateTotalAmount() {
        // Logic to update total amount based on selected products in RecyclerView
        // This could involve iterating through a list of items and summing up their prices
        totalAmount = 100000; // Example, replace with actual calculation logic
        totalAmountTextView.setText("Tổng cộng: " + totalAmount + " VND");
    }

    private void handleCheckout() {
        // Validate input fields
        String ownerName = cartBillOwner.getText().toString().trim();
        String address = cartBillAddressOwner.getText().toString().trim();
        String phone = cartBillPhoneOwner.getText().toString().trim();
        String voucherCode = etVoucher.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (ownerName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check payment method
        String paymentMethod = "Không chọn phương thức";
        if (rdbCreditCard.isChecked()) {
            paymentMethod = "Thẻ tín dụng/Ghi nợ";
        } else if (rdbCashOnDelivery.isChecked()) {
            paymentMethod = "Thanh toán khi nhận hàng";
        } else if (rdbEWallet.isChecked()) {
            paymentMethod = "Ví điện tử: " + eWalletSpinner.getSelectedItem().toString();
        }

        // Process payment and order
        // For example, create an order object, send it to the server, etc.
        String orderSummary = "Thông tin đơn hàng:\n" +
                "Tên người nhận: " + ownerName + "\n" +
                "Địa chỉ: " + address + "\n" +
                "Số điện thoại: " + phone + "\n" +
                "Voucher: " + voucherCode + "\n" +
                "Ghi chú: " + note + "\n" +
                "Phương thức thanh toán: " + paymentMethod + "\n" +
                "Tổng cộng: " + totalAmount + " VND";

        // Show order summary as a Toast for demonstration (can be replaced with actual order submission)
        Toast.makeText(this, orderSummary, Toast.LENGTH_LONG).show();

        // Optionally, navigate to confirmation screen or complete the order
        // Intent intent = new Intent(CheckoutActivity.this, ConfirmationActivity.class);
        // startActivity(intent);
    }
}
