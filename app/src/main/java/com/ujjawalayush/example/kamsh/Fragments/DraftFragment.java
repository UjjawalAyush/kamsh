package com.ujjawalayush.example.kamsh.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.Adapter.DraftAdapter;
import com.ujjawalayush.example.kamsh.EditBlog.Addpost;
import com.ujjawalayush.example.kamsh.DBAdapter;
import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.Data.Drafts;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DraftFragment extends Fragment {
    public DraftFragment() {
        // Required empty public constructor
    }
    View home;
    Context context;
    ArrayList<Drafts> myList=new ArrayList<>();
    Bitmap bitmap;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Uri uri;
    int pos;
    com.ujjawalayush.example.kamsh.Adapter.DraftAdapter draftAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        home =inflater.inflate(R.layout.fragment_drafts, container, false);
        context=home.getContext();
        DBAdapter db=new DBAdapter(context);
        db.open();
        recyclerView=(RecyclerView)home.findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor c=db.getAllTitles();
        c.moveToFirst();
        while(!c.isAfterLast()){
            Drafts mLog=new Drafts();
            mLog.setTitle(c.getString(1));
            mLog.setPostion(c.getString(2));
            myList.add(mLog);
            c.moveToNext();
        }
        db.close();
        draftAdapter = new DraftAdapter(myList);
        recyclerView.setAdapter(draftAdapter);
        callListener();
        return home;
    }
    public String getFileExtension(Uri uri1){
        ContentResolver cR=context.getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    private String InsertImage(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String name=Long.toString(System.currentTimeMillis())+"."+getFileExtension(uri);
        File mypath=new File(directory,name);
        FileOutputStream fos = null;
        String h=getFileExtension(uri).toUpperCase();
        if(h.equals("JPG"))
            h= "JPEG";
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.valueOf(h), 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()+"/"+name;
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
    private void callListener() {
        draftAdapter.setOnItemClickListener(new DraftAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                ArrayList<AddpostData> arrayList=new ArrayList<>();
                DBAdapter db=new DBAdapter(context);
                db.open();
                Cursor c=db.getAllContents();
                c.moveToFirst();
                while(!c.isAfterLast()) {
                    if (c.getString(0).equals(myList.get(position).getTitle())) {
                        AddpostData mLog = new AddpostData();
                        mLog.setId(c.getString(1));
                        mLog.setTitle(c.getString(2));
                        mLog.setContent(c.getString(3));
                        arrayList.add(mLog);
                    }
                    c.moveToNext();
                }
                Intent data=new Intent(context,Addpost.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("ARRAYLIST",arrayList);
                bundle.putString("TITLE",myList.get(position).getTitle());
                bundle.putString("VALUES",myList.get(position).getPostion());
                data.putExtra("extras",bundle);
                startActivity(data);
                db.close();
            }

            @Override
            public void onViewClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Are you sure that you wanna change the Picture ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            pos = position;
                            startActivityForResult(intent,1);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(),uri);
                String s=InsertImage(bitmap);
                if(s!=null){
                    Toast.makeText(context,"Image Successfully Added",Toast.LENGTH_LONG).show();
                    Drafts mLog=myList.get(pos);
                    mLog.setPostion(s);
                    myList.set(pos,mLog);
                    draftAdapter.notifyItemChanged(pos);
                    DBAdapter db=new DBAdapter(context);
                    db.open();
                    db.UpdateTitle(mLog);
                    db.close();

                }
                bitmap.recycle();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
