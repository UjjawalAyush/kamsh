package com.ujjawalayush.example.kamsh.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ujjawalayush.example.kamsh.R;

public class ContestFragment extends Fragment {
    public ContestFragment() {
        // Required empty public constructor
    }
    View home;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_contest, container, false);
        return home;
    }
}
