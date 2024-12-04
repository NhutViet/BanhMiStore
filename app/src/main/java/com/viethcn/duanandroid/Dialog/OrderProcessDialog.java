package com.viethcn.duanandroid.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.duanandroid.Adapters.OrderProcessAdpater;
import com.viethcn.duanandroid.Models.MainModel;
import com.viethcn.duanandroid.R;

import java.util.List;

public class OrderProcessDialog extends Dialog {
    private List<MainModel> mList;;
    private OrderProcessAdpater adapter;

    public OrderProcessDialog(@NonNull Context context, List<MainModel> mList) {
        super(context);
        this.mList = mList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState != null
                ? savedInstanceState
                : new Bundle());

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order_process, null);

        // Set the dialog's content view
        // to the newly created View object
        setContentView(view);

        // Allow the dialog to be dismissed
        // by touching outside of it
        setCanceledOnTouchOutside(true);

        // Allow the dialog to be canceled
        // by pressing the back button
        setCancelable(true);

        // Set up the RecyclerView in the dialog
        setUpRecyclerView(view);
    }
    private void setUpRecyclerView(View view)
    {
        // Find the RecyclerView in the layout file and set
        // its layout manager to a LinearLayoutManager
        RecyclerView recyclerView = view.findViewById(R.id.rcvOPProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a new instance of the EmployeeAdapter
        // and set it as the RecyclerView's adapter
        adapter = new OrderProcessAdpater( mList ,getContext());
        recyclerView.setAdapter(adapter);
    }
}
