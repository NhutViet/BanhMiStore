package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.R;

import java.util.HashMap;
import java.util.Map;

public class InsrtPrdctFraqment extends Fragment {

    EditText edtPrdctName, edtPrdctPrice, edtPrdctImg;
    Button btnInsertPrdct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insrt_prdct, container, false);

        initUI(view);

        btnInsertPrdct.setOnClickListener(v -> InsertData());

        return view;
    }
    private void initUI(View view){
        edtPrdctName = view.findViewById(R.id.edtPrdctName);
        edtPrdctPrice = view.findViewById(R.id.edtPrdctPrice);
        edtPrdctImg = view.findViewById(R.id.edtPrdctImg);

        btnInsertPrdct = view.findViewById(R.id.btnInsertPrdct);
    }
    private void InsertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", edtPrdctName.getText().toString());
        map.put("price", edtPrdctPrice.getText().toString());
        map.put("img", edtPrdctImg.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Product").push()
                .setValue(map)
                .addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    clearAll();
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .addOnFailureListener(exception -> Toast.makeText(requireContext(), "Lỗi khi thêm", Toast.LENGTH_SHORT).show());
    }

    private void clearAll(){
        edtPrdctName.setText("");
        edtPrdctPrice.setText("");
        edtPrdctImg.setText("");
    }
}