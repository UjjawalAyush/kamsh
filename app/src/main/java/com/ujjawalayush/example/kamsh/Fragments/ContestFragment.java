package com.ujjawalayush.example.kamsh.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ujjawalayush.example.kamsh.R;

import java.util.ArrayList;

public class ContestFragment extends Fragment {
    public ContestFragment() {
        // Required empty public constructor
    }
    View home;
    RecyclerView my,future,past,active;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_contest, container, false);
        my=(RecyclerView)home.findViewById(R.id.recyclerView);
        active=(RecyclerView)home.findViewById(R.id.recyclerView1);
        future=(RecyclerView)home.findViewById(R.id.recyclerView2);
        past=(RecyclerView)home.findViewById(R.id.recyclerView3);
        return home;
    }
}
