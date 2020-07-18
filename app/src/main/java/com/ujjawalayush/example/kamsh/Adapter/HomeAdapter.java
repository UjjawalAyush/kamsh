package com.ujjawalayush.example.kamsh.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.FirebaseData.PostData;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.RecyclerItemViewHolder> {
    private ArrayList<PostData> myList;
    private OnItemClickListener mListener;
    public HomeAdapter(ArrayList<PostData> myList1) {
        myList = myList1;
    }
    public interface OnItemClickListener{
        void onViewClick(int position);
        void onUpClick(int position);
        void onDownClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener=Listener;
    }
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home, viewGroup, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view,mListener);
        return holder;
    }
    private Bitmap getImage(String s){
        Bitmap b=null;
        try {
            File f=new File(s);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
    String create_visible(String s){
        if(s.length()<40)
            return s;
        StringBuilder s1=new StringBuilder();
        for(int i=0;i<40;i++)
            s1.append(s.charAt(i));
        s1.append("...");
        return s1.toString();
    }
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.RecyclerItemViewHolder holder, int pos) {
        holder.title.setText(myList.get(pos).getAuthor());
        holder.arrayList.clear();
        holder.arrayList.addAll(myList.get(pos).getMyList());
        holder.recyclerView.setLayoutManager(holder.linearLayoutManager);
        AddpostData mLog=new AddpostData();
        mLog.setId("TITLE");
        mLog.setContent(myList.get(pos).getTitle());
        holder.arrayList.add(0,mLog);
        holder.mAdapter = new ViewHomePostAdapter(holder.arrayList);
        holder.recyclerView.setAdapter(holder.mAdapter);
        holder.rating.setText(Long.toString(myList.get(pos).getRating()));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        ImageView view;
        TextView title,rating;
        RecyclerView recyclerView;
        ArrayList<AddpostData> arrayList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager;
        ViewHomePostAdapter mAdapter;
        ImageButton up,down;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            title=(TextView)parent.findViewById(R.id.text);
            recyclerView=(RecyclerView)parent.findViewById(R.id.recyclerView);
            up=(ImageButton)parent.findViewById(R.id.up);
            down=(ImageButton)parent.findViewById(R.id.down);
            rating=(TextView)parent.findViewById(R.id.text1);
            linearLayoutManager=new LinearLayoutManager(parent.getContext());
            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUpClick(getAdapterPosition());
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDownClick(getAdapterPosition());
                }
            });
        }
    }
}
