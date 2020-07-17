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
import android.widget.TextView;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.view.View.GONE;

public class ViewPostAdapter extends RecyclerView.Adapter<ViewPostAdapter.RecyclerItemViewHolder> {
    private ArrayList<AddpostData> myList;
    private OnItemClickListener mListener;
    public ViewPostAdapter(ArrayList<AddpostData> myList1) {
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
    private String getContent(String s) {
        StringBuilder ans =new StringBuilder();
        if(s.length()==0)
            return null;
        int t=0;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)=='}') {
                t=1;
                continue;
            }
            if(t==1)
                ans.append(s.charAt(i));
        }
        return ans.toString();
    }
    private String getColor(String s){
        StringBuilder ans =new StringBuilder();
        if(s.length()==0)
            return null;
        for(int i=1;i<s.length();i++){
            if(s.charAt(i)=='}') {
                return ans.toString();
            }
            ans.append(s.charAt(i));
        }
        return ans.toString();
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
    @NonNull
    @Override
    public RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerItemViewHolder holder;
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewpost, viewGroup, false);
            holder = new RecyclerItemViewHolder(view,mListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewPostAdapter.RecyclerItemViewHolder holder, int pos) {
        if(myList.get(pos).getId().equals("Text")){
            holder.imageView.setVisibility(GONE);
            String col=getColor(myList.get(pos).getContent());
            holder.title.setVisibility(GONE);
            String content=getContent(myList.get(pos).getContent());
            assert col != null;
            holder.textView.setTextColor(Integer.parseInt(col));
            holder.textView.setText(content);
        }
        else if(myList.get(pos).getId().equals("TITLE")){
            holder.title.setText(myList.get(pos).getContent());
            holder.imageView.setVisibility(GONE);
            holder.textView.setVisibility(GONE);
            holder.title.setPaintFlags(holder.title.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);;
        }
        else{
            holder.textView.setVisibility(GONE);
            holder.title.setVisibility(GONE);
            holder.imageView.setImageBitmap(getImage(myList.get(pos).getContent()));
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView,title;
        ImageView imageView;
        public RecyclerItemViewHolder(final View parent, final OnItemClickListener listener) {
            super(parent);
            textView=(TextView)parent.findViewById(R.id.text11);
            title=(TextView)parent.findViewById(R.id.title11);
            imageView=(ImageView)parent.findViewById(R.id.image11);
        }
    }
}
