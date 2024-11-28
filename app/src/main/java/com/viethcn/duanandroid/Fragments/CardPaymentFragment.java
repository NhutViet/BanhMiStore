package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viethcn.duanandroid.Adapters.PaymenAdapter;
import com.viethcn.duanandroid.DAO.ProductDAO;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class CardPaymentFragment extends Fragment {
    RecyclerView rcv;
    List<MainModel> mList;
    PaymenAdapter adapter;

    public CardPaymentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checkout, container, false);
        rcv = view.findViewById(R.id.paymentRcV);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(layout);
        rcv.setHasFixedSize(true);

        return view;
    }
}