package com.ujjawalayush.example.kamsh.Fragments;

import android.content.Context;
import android.content.Intent;
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
import com.ujjawalayush.example.kamsh.Adapter.ContestAdapter;
import com.ujjawalayush.example.kamsh.ContestFeatures.Contest;
import com.ujjawalayush.example.kamsh.ContestFeatures.MyContest;
import com.ujjawalayush.example.kamsh.FirebaseData.ContestData;
import com.ujjawalayush.example.kamsh.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContestFragment extends Fragment {
    public ContestFragment() {
        // Required empty public constructor
    }
    View home;
    RecyclerView my,future,past,active;
    ArrayList<ContestData> myList=new ArrayList<>();
    ArrayList<ContestData> activeList=new ArrayList<>();
    ArrayList<ContestData> futureList=new ArrayList<>();
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    ArrayList<ContestData> pastList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager,linearLayoutManager1,linearLayoutManager2,linearLayoutManager3;
    ContestAdapter myContest,activeContest,pastContest,futureContest;
    Context context;
    Long current;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_contest, container, false);
        my=(RecyclerView)home.findViewById(R.id.recyclerView);
        active=(RecyclerView)home.findViewById(R.id.recyclerView1);
        future=(RecyclerView)home.findViewById(R.id.recyclerView2);
        past=(RecyclerView)home.findViewById(R.id.recyclerView3);
        context=home.getContext();
        linearLayoutManager=new LinearLayoutManager(context);
        my.setLayoutManager(linearLayoutManager);
        myContest = new ContestAdapter(myList);
        my.setAdapter(myContest);
        linearLayoutManager1=new LinearLayoutManager(context);
        active.setLayoutManager(linearLayoutManager1);
        activeContest = new ContestAdapter(activeList);
        active.setAdapter(activeContest);
        linearLayoutManager2=new LinearLayoutManager(context);
        future.setLayoutManager(linearLayoutManager2);
        futureContest = new ContestAdapter(futureList);
        future.setAdapter(futureContest);
        linearLayoutManager3=new LinearLayoutManager(context);
        past.setLayoutManager(linearLayoutManager3);
        pastContest = new ContestAdapter(pastList);
        past.setAdapter(pastContest);
        current=System.currentTimeMillis();
        DatabaseReference databaseReference1=databaseReference.child("Contests");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myList.clear();
                myContest.notifyDataSetChanged();
                pastList.clear();
                pastContest.notifyDataSetChanged();
                futureList.clear();
                futureContest.notifyDataSetChanged();
                activeList.clear();
                activeContest.notifyDataSetChanged();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ContestData mLog=insert(dataSnapshot1);
                    Long h = null;
                    try {
                        h = convert_to_milli(mLog.getStartdate(),mLog.getStarttime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long h1 = null;
                    try {
                        h1 = convert_to_milli(mLog.getEnddate(),mLog.getEndtime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(user!=null&&dataSnapshot1.child("user").getValue().toString().equals(user.getUid())){
                        int pos=myList.size();
                        myList.add(mLog);
                        myContest.notifyItemChanged(pos);
                    }
                    if(h>current){
                        int pos=futureList.size();
                        futureList.add(mLog);
                        futureContest.notifyItemChanged(pos);
                    }
                    else if(h1>current){
                        int pos=activeList.size();
                        activeList.add(mLog);
                        activeContest.notifyItemChanged(pos);
                    }
                    else{
                        int pos=pastList.size();
                        pastList.add(mLog);
                        pastContest.notifyItemChanged(pos);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        CallListener();
        return home;
    }

    private void CallListener() {
        myContest.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                Intent data =new Intent(context,MyContest.class);
                data.putExtra("Name",myList.get(position).getName());
                startActivity(data);
            }
        });
        pastContest.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                Intent data =new Intent(context,Contest.class);
                Bundle extras=new Bundle();
                extras.putString("Type","1");
                extras.putString("Name",pastList.get(position).getName());
                data.putExtra("extras",extras);
                startActivity(data);
            }
        });
        futureContest.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                Intent data =new Intent(context,Contest.class);
                Bundle extras=new Bundle();
                extras.putString("Type","3");
                extras.putString("Name",futureList.get(position).getName());
                data.putExtra("extras",extras);
                startActivity(data);
            }
        });
        activeContest.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {
                Intent data =new Intent(context,Contest.class);
                Bundle extras=new Bundle();
                extras.putString("Type","2");
                extras.putString("Name",activeList.get(position).getName());
                data.putExtra("extras",extras);
                startActivity(data);
            }
        });
    }

    private Long convert_to_milli(String startdate, String starttime) throws ParseException {
        Long ans=(long)0;
        starttime+=":00";
        startdate+=" "+starttime;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date date=sdf.parse(startdate);
        ans=date.getTime();
        return ans;
    }

    public ContestData insert(DataSnapshot dataSnapshot){
        ContestData mLog=new ContestData();
        mLog.setName(dataSnapshot.child("name").getValue().toString());
        mLog.setStartdate(dataSnapshot.child("startdate").getValue().toString());
        mLog.setEnddate(dataSnapshot.child("enddate").getValue().toString());
        mLog.setStarttime(dataSnapshot.child("starttime").getValue().toString());
        mLog.setEndtime(dataSnapshot.child("endtime").getValue().toString());
        return mLog;
    }
}
