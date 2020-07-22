package com.ujjawalayush.example.kamsh.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.FirebaseData.ContestData;
import com.ujjawalayush.example.kamsh.FirebaseData.leaderData;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.view.View.GONE;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.RecyclerItemViewHolder> {
    private ArrayList<leaderData> myList;
    private OnItemClickListener mListener;
    public LeaderAdapter(ArrayList<leaderData> myList1) {
        myList = myList1;
    }
    public interface OnItemClickListener{
        void onViewClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener=Listener;
    }
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerItemViewHolder holder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leader, viewGroup, false);
        holder = new RecyclerItemViewHolder(view,mListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull LeaderAdapter.RecyclerItemViewHolder holder, int pos) {
        holder.username.setText(myList.get(pos).getUsername());
        holder.points.setText(Integer.toString(myList.get(pos).getPoint()));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        TextView username,points;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            username=(TextView)parent.findViewById(R.id.username);
            points=(TextView)parent.findViewById(R.id.point);
        }
    }
}
