package com.viethcn.duanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Models.User;

public class RegisterActivity extends AppCompatActivity {

    TextView txtDangNhap;
    Button btnDangKy;
    EditText edtTenDangNhap, edtNhapPass, edtRePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        txtDangNhap = findViewById(R.id.txtDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtNhapPass = findViewById(R.id.edtNhapPass);
        edtRePass = findViewById(R.id.edtRePass);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDangNhap = edtTenDangNhap.getText().toString().trim();
                String matKhau = edtNhapPass.getText().toString().trim();
                String rePass = edtRePass.getText().toString().trim();

                // Kiểm tra nhập liệu
                if (tenDangNhap.isEmpty() || matKhau.isEmpty() || rePass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu và xác nhận mật khẩu
                if (!matKhau.equals(rePass)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu thông tin tài khoản vào Firebase Realtime Database
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Accounts");
                String userId = userRef.push().getKey(); // Tạo ID duy nhất cho người dùng

                if (userId != null) {
                    User user = new User(userId, tenDangNhap, matKhau, ""); // Truyền ID vào User
                    userRef.child(userId).setValue(user).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi hệ thống. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtDangNhap.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }
}
