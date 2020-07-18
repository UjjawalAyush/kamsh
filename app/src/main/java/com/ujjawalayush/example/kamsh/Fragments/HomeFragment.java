package com.ujjawalayush.example.kamsh.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ujjawalayush.example.kamsh.Adapter.DraftAdapter;
import com.ujjawalayush.example.kamsh.Adapter.HomeAdapter;
import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.FirebaseData.PostData;
import com.ujjawalayush.example.kamsh.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    View home;
    Context context;
    ArrayList<PostData> arrayList=new ArrayList<>();
    Bitmap bitmap;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Uri uri;
    HomeAdapter homeAdapter;
    int pos;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_home, container, false);
        context=home.getContext();
        recyclerView=(RecyclerView)home.findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(arrayList);
        recyclerView.setAdapter(homeAdapter);
        databaseReference.child("Posts").orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    pos=arrayList.size();
                    arrayList.add(create_postData(dataSnapshot1));
                    homeAdapter.notifyItemChanged(pos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return home;
    }

    private PostData create_postData(DataSnapshot dataSnapshot1) {
        PostData mLog= new PostData();
        if(dataSnapshot1.hasChild("title"))
            mLog.setTitle((String) dataSnapshot1.child("title").getValue());
        if(dataSnapshot1.hasChild("author"))
            mLog.setAuthor((String) dataSnapshot1.child("author").getValue());
        if(dataSnapshot1.hasChild("photo"))
            mLog.setPhoto((String) dataSnapshot1.child("photo").getValue());
        ArrayList<AddpostData> myList=new ArrayList<>();
        if(dataSnapshot1.hasChild("myList")){
            for(DataSnapshot snapshot:dataSnapshot1.child("myList").getChildren()){
                AddpostData data=new AddpostData();
                if(snapshot.hasChild("uri"))
                    data.setUri(Uri.parse((String) snapshot.child("uri").getValue()));
                data.setId((String) snapshot.child("id").getValue());
                if(snapshot.hasChild("height")) {
                    data.setHeight((Long) snapshot.child("height").getValue());
                }
                data.setTitle((String) snapshot.child("title").getValue());
                data.setContent((String)snapshot.child("content").getValue());
                myList.add(data);
            }
        }
        mLog.setMyList(myList);
        return mLog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
