package com.ujjawalayush.example.kamsh.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ujjawalayush.example.kamsh.Data.Drafts;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.RecyclerItemViewHolder> {
    private ArrayList<Drafts> myList;
    private OnItemClickListener mListener;
    public DraftAdapter(ArrayList<Drafts> myList1) {
        myList = myList1;
    }
    public interface OnItemClickListener{
        void onEditClick(int position);
        void onViewClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener Listener){
        mListener=Listener;
    }
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drafts, viewGroup, false);
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
    public void onBindViewHolder(@NonNull DraftAdapter.RecyclerItemViewHolder holder, int pos) {
        holder.title.setText(create_visible(myList.get(pos).getTitle()));
        String s=myList.get(pos).getPostion();
        if(!s.equals("")) {
            holder.view.setBackground(null);
            holder.view.setImageBitmap(getImage(s));
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        ImageView view;
        TextView title;
        ImageButton edit;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            view=(ImageView) parent.findViewById(R.id.imageview);
            title=(TextView)parent.findViewById(R.id.header);
            edit=(ImageButton)parent.findViewById(R.id.editButton);
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
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            listener.onEditClick(pos);
                        }
                    }
                }
            });
        }
    }
}
