package com.viethcn.duanandroid.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

            totalString = "Tổng cộng: " + formatVND(mViewModel.calcTotal());
            txtTotal.setText(totalString);

            adapter.notifyDataSetChanged();
        });

        btnCheckout.setOnClickListener( v -> {
            boolean flagCheck = true;
            StringBuilder userErrorCheck = new StringBuilder();


            List<MainModel> list = mViewModel.getSelectedItems().getValue();
            String owner = edtOwner.getText().toString();
            String address = edtAddress.getText().toString();
            String phone = edtPhone.getText().toString();
            String note = edtDiscription.getText().toString();
            int totala = mViewModel.calcTotal();

            if ( list.isEmpty() ){
                flagCheck = false;
                userErrorCheck.append("Hiện bạn không có đơn nào để thanh toán\n");
            }
            if (owner.isEmpty()){
                flagCheck = false;
                userErrorCheck.append("Họ tên người nhận không được bỏ trống\n");
            }
            if (address.isEmpty()){
                flagCheck = false;
                userErrorCheck.append("Địa chỉ không hợp lệ\n");
            }
            if (phone.length() != 10){
                flagCheck = false;
                userErrorCheck.append("Số điện thoại không hợp lệ\n");
            }

            if (flagCheck){
                Payout(list, owner, address, phone, note, totala);
            }else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Thêm hoá đơn không thành công")
                        .setMessage(userErrorCheck.toString().trim())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        return view;
    }

    private void Payout(List<MainModel> list,  String owner,  String address,  String phone,  String note, int total ){
        Map<String, Object> map = new HashMap<>();

        map.put("owner", owner);
        map.put("address", address);
        map.put("phone", phone);
        map.put("listProduct", list);
        map.put("note", note);
        map.put("total", total);

        if(owner.isEmpty() || address.isEmpty() || phone.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase.getInstance().getReference().child("Recipts").push()
                .setValue(map)
                .addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), "Thêm hoá đơn thành công", Toast.LENGTH_SHORT).show();
                    clearAll();
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .addOnFailureListener(exception -> Toast.makeText(requireContext(), "Lỗi khi thêm", Toast.LENGTH_SHORT).show());
    }

    private void clearAll(){
        edtOwner.setText("");
        edtAddress.setText("");
        edtPhone.setText("");
        edtDiscription.setText("");
        mViewModel.clearAll();
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
