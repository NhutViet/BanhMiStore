package com.viethcn.duanandroid.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viethcn.duanandroid.R;

public class BillsFragment extends Fragment {

    public BillsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcvInf);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcv.setLayoutManager(layout);
        rcv.setHasFixedSize(true);
        return view;
    }
}