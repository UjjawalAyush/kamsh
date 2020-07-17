package com.ujjawalayush.example.kamsh.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.R;

import java.util.ArrayList;

public class AddPostAdapter extends RecyclerView.Adapter<AddPostAdapter.RecyclerItemViewHolder> {
    private ArrayList<AddpostData> myList;
    private OnItemClickListener mListener;
    public AddPostAdapter(ArrayList<AddpostData> myList1) {
        myList = myList1;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onViewClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener=Listener;
    }
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addpost, viewGroup, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddPostAdapter.RecyclerItemViewHolder holder, int pos) {
        holder.text.setText("Type: "+myList.get(pos).getId());
        holder.text_title.setText(myList.get(pos).getTitle());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        ImageButton edit,del;
        TextView text,text_title;
        Button view;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            edit=(ImageButton) parent.findViewById(R.id.edit);
            del=(ImageButton) parent.findViewById(R.id.del);
            view=(Button) parent.findViewById(R.id.view);
            text=(TextView)parent.findViewById(R.id.text);
            text_title=(TextView)parent.findViewById(R.id.text_title);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onViewClick(pos);
                        }
                    }
                }
            });
        }
    }
}
