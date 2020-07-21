package com.ujjawalayush.example.kamsh.ContestFeatures;

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
import android.widget.Toast;

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

public class ContestHomeFragment extends Fragment {
    public ContestHomeFragment() {
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
    String name;
    HomeAdapter homeAdapter;
    int pos;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_home, container, false);
        context=home.getContext();
        name=getArguments().getString("Name");
        recyclerView=(RecyclerView)home.findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(arrayList);
        recyclerView.setAdapter(homeAdapter);
        databaseReference.child("Contests").child(name).child("Posts").orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                homeAdapter.notifyDataSetChanged();
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
        CallListener();
        return home;
    }
    void CallListener(){
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(int position) {

            }

            @Override
            public void onUpClick(final int position) {
                if(user==null){
                    Toast.makeText(context,"Sign In First!!",Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference databaseReference1=databaseReference.child("Contests").child(name).child("Rating").child(arrayList.get(position).getKey());
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int t=0;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(dataSnapshot1.getValue().toString().equals( user.getUid()))
                                t=1;
                        }
                        if(t==0){
                            final Long rating=arrayList.get(position).getRating();
                            DatabaseReference databaseReference2=databaseReference.child("Contests").child(name).child("Posts").child(arrayList.get(position).getKey());
                            databaseReference2.child("rating").setValue(rating+1);
                            databaseReference2=databaseReference.child("Contests").child(name).child("Rating").child(arrayList.get(position).getKey());
                            databaseReference2.push().setValue(user.getUid());
                            databaseReference2=databaseReference.child("Contests").child(name).child("Posts").child(arrayList.get(position).getKey());
                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String author=dataSnapshot.child("author").getValue().toString();
                                    final DatabaseReference databaseReference3=databaseReference.child("Contests").child(name).child("leaderboard").child(author);
                                    databaseReference3.setValue(rating+1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(context,"Sorry You have already voted!!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onDownClick(final int position) {
                if(user==null){
                    Toast.makeText(context,"Sign In First!!",Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference databaseReference1=databaseReference.child("Contests").child(name).child("Posts").child("Rating").child(arrayList.get(position).getKey());
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int t=0;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            if(dataSnapshot1.getValue().toString().equals( user.getUid()))
                                t=1;
                        }
                        if(t==0){
                            final Long rating=arrayList.get(position).getRating();
                            DatabaseReference databaseReference2=databaseReference.child("Contests").child(name).child("Posts").child(arrayList.get(position).getKey());
                            databaseReference2.child("rating").setValue(rating-1);
                            databaseReference2=databaseReference.child("Contests").child(name).child("Rating").child(arrayList.get(position).getKey());
                            databaseReference2.push().setValue(user.getUid());
                            databaseReference2=databaseReference.child("Contests").child(name).child("Posts").child(arrayList.get(position).getKey());
                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String author=dataSnapshot.child("author").getValue().toString();
                                    final DatabaseReference databaseReference3=databaseReference.child("Contests").child(name).child("leaderboard").child(author);
                                    databaseReference3.setValue(rating-1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(context,"Sorry You have already voted!!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private PostData create_postData(DataSnapshot dataSnapshot1) {
        PostData mLog= new PostData();
        if(dataSnapshot1.hasChild("title"))
            mLog.setTitle((String) dataSnapshot1.child("title").getValue());
        if(dataSnapshot1.hasChild("author"))
            mLog.setAuthor((String) dataSnapshot1.child("author").getValue());
        if(dataSnapshot1.hasChild("photo"))
            mLog.setPhoto((String) dataSnapshot1.child("photo").getValue());
        if(dataSnapshot1.hasChild("rating"))
            mLog.setRating((Long)dataSnapshot1.child("rating").getValue());
        mLog.setKey(dataSnapshot1.getKey());
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
