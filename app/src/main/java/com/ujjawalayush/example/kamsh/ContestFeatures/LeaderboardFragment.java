package com.ujjawalayush.example.kamsh.ContestFeatures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ujjawalayush.example.kamsh.Adapter.LeaderAdapter;
import com.ujjawalayush.example.kamsh.FirebaseData.leaderData;
import com.ujjawalayush.example.kamsh.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class LeaderboardFragment extends Fragment {
    public LeaderboardFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    View home;
    ArrayList<leaderData> myList=new ArrayList<>();
    String name;
    LinearLayoutManager linearLayoutManager;
    LeaderAdapter mAdapter;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home = inflater.inflate(R.layout.fragment_contestleaderboard, container, false);
        context = home.getContext();
        name=getArguments().getString("Name");
        recyclerView=(RecyclerView)home.findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new LeaderAdapter(myList);
        recyclerView.setAdapter(mAdapter);
        DatabaseReference databaseReference1=databaseReference.child("Contests").child(name).child("leaderboard");
        databaseReference1.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myList.clear();
                mAdapter.notifyDataSetChanged();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    leaderData mLog=new leaderData();
                    mLog.setPoint(Integer.parseInt(dataSnapshot1.getValue().toString()));
                    mLog.setUsername(dataSnapshot1.getKey().toString());
                    myList.add(mLog);
                }
                Collections.reverse(myList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return home;
    }
}
