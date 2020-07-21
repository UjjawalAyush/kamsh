package com.ujjawalayush.example.kamsh.ContestFeatures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ujjawalayush.example.kamsh.R;

public class LeaderboardFragment extends Fragment {
    public LeaderboardFragment() {
        // Required empty public constructor
    }
    View home;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home = inflater.inflate(R.layout.fragment_contestleaderboard, container, false);
        context = home.getContext();
        return home;
    }
}
