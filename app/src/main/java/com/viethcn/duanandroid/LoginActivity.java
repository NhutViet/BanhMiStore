package com.viethcn.duanandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private TextView txtDangKy, txtQuenMk;
    private EditText edtTenDangNhap, edtMatKhau;
    private TextView btnDangNhap;
    private ImageView imgGG;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient googleSignInClient;
    private final int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        txtDangKy = findViewById(R.id.txtDangky);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtQuenMk = findViewById(R.id.txtQuenMk);
        imgGG = findViewById(R.id.imgGG);

        // Firebase Authentication & Database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Cấu hình Google Sign-In
        GoogleSignInOptions gos = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gos);

        // Xử lý sự kiện click
        imgGG.setOnClickListener(v -> googleSignIn());
        btnDangNhap.setOnClickListener(v -> handleLogin());
        txtQuenMk.setOnClickListener(v -> showDialogQuenMK());
        txtDangKy.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }


    private void handleLogin() {
        String tenDangNhap = edtTenDangNhap.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        // Kiểm tra đầu vào
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy tham chiếu tới Firebase Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Accounts");

        // Tìm kiếm tài khoản theo tên đăng nhập
        userRef.orderByChild("name").equalTo(tenDangNhap).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String correctPassword = userSnapshot.child("password").getValue(String.class);
                        String role = userSnapshot.child("rule").getValue(String.class);
                        String userID = userSnapshot.child("id").getValue(String.class);

                        if (correctPassword != null && correctPassword.equals(matKhau)) {
                            // Lưu vai trò vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("thongtin", LoginActivity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("rule", role);
                            editor.apply();

                            // Kiểm tra vai trò và chuyển hướng
                            if ("admin".equals(role)) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } else if ("".equals(role)) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Lỗi cơ sở dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    // XỬ lý đăng nhập bằng google
    private void googleSignIn() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthAccount(account.getIdToken());
                SharedPreferences tokenRef = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = tokenRef.edit();
                editor.putString("token", account.getIdToken());
                editor.apply();
            } catch (ApiException e) {
                Toast.makeText(this, "Lỗi đăng nhập: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthAccount(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    DatabaseReference userRef = database.getReference().child("Accounts").child(user.getUid());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String role = "google";
                            if (!snapshot.exists()) {
                                // Người dùng mới - Lưu vào Firebase
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id", user.getUid());
                                map.put("name", user.getDisplayName());
                                map.put("profile", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
                                map.put("rule", role);
                                userRef.setValue(map);
                            } else {
                                // Người dùng cũ - Lấy vai trò từ Firebase
                                role = snapshot.child("rule").getValue(String.class);
                            }

                            // Lưu role vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("rule", role);
                            editor.apply();

                            SharedPreferences userIDShared = getSharedPreferences("userID", MODE_PRIVATE);
                            SharedPreferences.Editor userIDEditor = userIDShared.edit();
                            userIDEditor.putString("userID", user.getUid());
                            userIDEditor.apply();

                            Toast.makeText(LoginActivity.this, "Đăng nhập Google thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Lỗi cơ sở dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }





    // Xử lý quên mật khẩu
    private void showDialogQuenMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtUsername = view.findViewById(R.id.edtUserName);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        btnSend.setOnClickListener(v -> {
            String email = edtUsername.getText().toString().trim();
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập email hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Đã gửi email đặt lại mật khẩu. Kiểm tra hộp thư đến của bạn.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Không thể gửi email đặt lại mật khẩu. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            });
        });
    }
}
