package com.viethcn.duanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.viethcn.duanandroid.Adapters.MainAdapter;
import com.viethcn.duanandroid.Models.MainModel;

public class TestListData extends Fragment {

    RecyclerView recyclerViewMain;
    MainAdapter mainAdapter;
    GridLayoutManager gridLayoutManager;
    FirebaseRecyclerOptions<MainModel> options;
    FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.activity_test_list_data, container, false);
        recyclerViewMain = view.findViewById(R.id.recyclerMain);
        fabAdd = view.findViewById(R.id.fabAdd);

        setMenuData();

        fabAdd.setOnClickListener(v -> {
            startActivity( new Intent(getActivity(), InsrtPrdctActivity.class));
        });

        return view;
    }

    private void setMenuData(){
        // Set layout manager
        gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 columns
        recyclerViewMain.setLayoutManager(gridLayoutManager);
        // Setup Firebase options
        options = new FirebaseRecyclerOptions.Builder<MainModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), MainModel.class)
                .build();
        // Initialize adapter
        mainAdapter = new MainAdapter(options);
        // Attach adapter to RecyclerView
        recyclerViewMain.setAdapter(mainAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Start listening for data changes when the fragment starts
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Stop listening for data changes when the fragment stops
        mainAdapter.stopListening();
    }
}
