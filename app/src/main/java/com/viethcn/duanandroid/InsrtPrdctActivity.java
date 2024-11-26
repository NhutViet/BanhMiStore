package com.viethcn.duanandroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InsrtPrdctActivity extends AppCompatActivity {

    EditText edtPrdctName, edtPrdctPrice, edtPrdctImg;
    Button btnInsertPrdct, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insrt_prdct);

        snapshotComponent();

        btnInsertPrdct.setOnClickListener( v-> InsertData() );
        btnBack.setOnClickListener( v-> finish() );

    }
    private void snapshotComponent(){
        edtPrdctName = findViewById(R.id.edtPrdctName);
        edtPrdctPrice = findViewById(R.id.edtPrdctPrice);
        edtPrdctImg = findViewById(R.id.edtPrdctImg);

        btnInsertPrdct = findViewById(R.id.btnInsertPrdct);
        btnBack = findViewById(R.id.btnBack);
    }
    private void InsertData(){
        Map<String, Object> map = new HashMap<>();

        map.put("name", edtPrdctName.getText().toString());
        map.put("price", edtPrdctPrice.getText().toString());
        map.put("img", edtPrdctImg.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Product").push()
                .setValue(map)
                .addOnCompleteListener(task -> {
                    Toast.makeText(InsrtPrdctActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    clearAll();
                    finish();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(this, "lỗi khi thêm", Toast.LENGTH_SHORT).show();
                });
    }
    private void clearAll(){
        edtPrdctName.setText("");
        edtPrdctPrice.setText("");
        edtPrdctImg.setText("");
    }
}