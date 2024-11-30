package com.viethcn.duanandroid.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.PaymenAdapter;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;
import com.viethcn.duanandroid.Repositories.MainModelRepository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;

public class CardPaymentFragment extends Fragment {
    RecyclerView rcv;
    List<MainModel> mList;
    PaymenAdapter adapter;
    MainModelRepository mViewModel;

    TextView txtTotal;
    EditText edtOwner, edtAddress, edtPhone, edtDiscription;
    Button btnCheckout;

    int total;
    String totalString;

    public CardPaymentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checkout, container, false);
        InitUI(view);
        rcv = view.findViewById(R.id.paymentRcV);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(layout);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainModelRepository.class);
        mViewModel.getSelectedItems().observe(getViewLifecycleOwner(), items -> {
            mList.clear();

            adapter = new PaymenAdapter(mViewModel.getSelectedItems(), getViewLifecycleOwner());
            rcv.setAdapter(adapter);

            total = mViewModel.calcTotal();
            totalString = "Tổng cộng: " + formatVND(total);
            txtTotal.setText(totalString);

            adapter.notifyDataSetChanged();

        });


        btnCheckout.setOnClickListener( v -> {

            List<MainModel> list = mViewModel.getSelectedItems().getValue();
            String owner = edtOwner.getText().toString();
            String address = edtAddress.getText().toString();
            String phone = edtPhone.getText().toString();

            Map<String, Object> map = new HashMap<>();
            map.put("owner", owner);
            map.put("address", address);
            map.put("phone", phone);
            map.put("listProduct", list);

            if(owner.isEmpty() || address.isEmpty() || phone.isEmpty()){
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

            FirebaseDatabase.getInstance().getReference().child("Recipts").push()
                    .setValue(map)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        clearAll();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    })
                    .addOnFailureListener(exception -> Toast.makeText(requireContext(), "Lỗi khi thêm", Toast.LENGTH_SHORT).show());
        });

        return view;
    }

    private void clearAll(){
        edtOwner.setText("");
        edtAddress.setText("");
        edtPhone.setText("");
        edtDiscription.setText("");
    }

    private static String formatVND(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        formatter.setMaximumFractionDigits(0);
        return formatter.format(amount);
    }


    private void InitUI(View view){
        txtTotal = view.findViewById(R.id.totalAmount);
        edtOwner = view.findViewById(R.id.cartBillOwner);
        edtAddress = view.findViewById(R.id.cartBillAddressOwner);
        edtPhone = view.findViewById(R.id.cartBillPhoneOwner);
        edtDiscription = view.findViewById(R.id.etNote);
        btnCheckout = view.findViewById(R.id.btnCheckout);

    }
}
