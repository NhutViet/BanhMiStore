package com.viethcn.duanandroid.Fragments;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Adapters.OrdersAdapter;
import com.viethcn.duanandroid.Models.Order;
import com.viethcn.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class PaidOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;
    private List<Order> ordersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paid_orders, container, false);

        recyclerView = view.findViewById(R.id.rv_paid_orders);
        TextView tvNoOrders = view.findViewById(R.id.tv_no_orders);

        // Setup RecyclerView
        ordersList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(ordersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(ordersAdapter);

        // Fetch orders (mock data for now)
        fetchPaidOrders(tvNoOrders);

        return view;
    }

    private void fetchPaidOrders(TextView tvNoOrders) {
        // Dữ liệu giả lập
        ordersList.add(new Order("Bánh mì ABC", "29/11/2024", 20000));
        ordersList.add(new Order("Bánh mì XYZ", "28/11/2024", 15000));

        if (ordersList.isEmpty()) {
            tvNoOrders.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoOrders.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            ordersAdapter.notifyDataSetChanged();
        }
    }
}

