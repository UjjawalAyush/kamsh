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
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

import static android.view.View.GONE;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.RecyclerItemViewHolder> {
    private ArrayList<ContestData> myList;
    private OnItemClickListener mListener;
    public ContestAdapter(ArrayList<ContestData> myList1) {
        myList = myList1;
    }
    public interface OnItemClickListener{
        void onViewClick(int position) throws ParseException;
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener=Listener;
    }
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerItemViewHolder holder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contestpost, viewGroup, false);
        holder = new RecyclerItemViewHolder(view,mListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ContestAdapter.RecyclerItemViewHolder holder, int pos) {
        holder.starttime.setText(myList.get(pos).getStarttime());
        holder.startdate.setText(myList.get(pos).getStartdate());
        holder.endtime.setText(myList.get(pos).getEndtime());
        holder.enddate.setText(myList.get(pos).getEnddate());
        holder.name.setText(myList.get(pos).getName());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        TextView name,starttime,startdate,enddate,endtime;
        RelativeLayout relativeLayout;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            name=(TextView)parent.findViewById(R.id.name);
            startdate=(TextView)parent.findViewById(R.id.start_date);
            starttime=(TextView)parent.findViewById(R.id.start_time);
            enddate=(TextView)parent.findViewById(R.id.end_date);
            endtime=(TextView)parent.findViewById(R.id.end_time);
            relativeLayout=(RelativeLayout)parent.findViewById(R.id.relativeLayout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        listener.onViewClick(getAdapterPosition());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
