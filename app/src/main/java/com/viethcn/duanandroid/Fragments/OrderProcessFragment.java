package com.viethcn.duanandroid.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viethcn.duanandroid.Adapters.OrderProcessAdpater;
import com.viethcn.duanandroid.Models.DonHang;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;

public class OrderProcessFragment extends Fragment {
    TextView tvOPOwner, tvOPAdress, tvOPPhone, tvOPStatus, tvOPTotal ;
    Button btnCancel, btnApprove;
    RecyclerView rcvOPProducts;
    OrderProcessAdpater adapter;
    DonHang mDonHang;

    ArrayList<MainModel> mList;

    DatabaseReference reciptsDB;
    FragmentActivity context;

    public OrderProcessFragment(FragmentActivity context) {this.context = context;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reciptsDB = FirebaseDatabase.getInstance().getReference().child("Recipts");

        mList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String owner = bundle.getString("owner");
            String address = bundle.getString("address");
            String phone = bundle.getString("phone");
            String status = bundle.getString("status");
            String reciptsID = bundle.getString("reciptsID");
            String note = bundle.getString("note");
            double total = bundle.getDouble("total");

            ArrayList<MainModel> receivedList = (ArrayList<MainModel>) bundle.getSerializable("list");

            Log.i("////DATA", reciptsID);

            mList.addAll(receivedList);
            mDonHang = new DonHang(reciptsID, owner, address, phone, total, note,  receivedList, status);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_process, container, false);
        initUI(view);

        SharedPreferences sharedPreferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);

        String loai = sharedPreferences.getString("rule", "");

        if (!"admin".equals(loai)) {
            btnCancel.setVisibility(View.GONE);
            btnApprove.setVisibility(View.GONE);
        }



        tvOPOwner.setText("Khách Hàng: " + mDonHang.getOwner());
        tvOPAdress.setText("Địa Chỉ: " + mDonHang.getAddress());
        tvOPPhone.setText("SĐT: " + mDonHang.getPhone());
        tvOPStatus.setText("Trạng thái: "+ mDonHang.getStatus());
        tvOPTotal.setText(("Tổng tiền: " + mDonHang.getTotal()));

        adapter = new OrderProcessAdpater( mList ,getActivity());
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvOPProducts.setLayoutManager(layout);
        rcvOPProducts.setAdapter(adapter);

        btnCancel.setOnClickListener( v -> {
            mDonHang.setStatus("Đã hủy");
            updateRecipts(mDonHang);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainViewHomePage, new SettingFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnApprove.setOnClickListener( v-> {
            mDonHang.setStatus("Đã xác nhận");
            updateRecipts(mDonHang);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainViewHomePage, new SettingFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void updateRecipts(DonHang mItem) {
        reciptsDB.orderByChild("reciptsID").equalTo(mItem.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isAdded()) return;
                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        item.getRef().child("status").setValue(mItem.getStatus())
                                .addOnSuccessListener(aVoid -> Toast.makeText(context, mItem.getStatus() + " thành công", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(context, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi truy cập cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI(View view){
        tvOPOwner = view.findViewById(R.id.tvOPOwner);
        tvOPAdress = view.findViewById(R.id.tvOPAdress);
        tvOPPhone = view.findViewById(R.id.tvOPPhone);
        rcvOPProducts = view.findViewById(R.id.rcvOPProducts);
        tvOPStatus = view.findViewById(R.id.tvOPStatus);
        tvOPTotal = view.findViewById(R.id.tvOPTotal);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnApprove = view.findViewById(R.id.btnApprove);
    }
}