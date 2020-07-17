package com.ujjawalayush.example.kamsh.EditBlog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.Adapter.ViewPostAdapter;
import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.R;

import java.util.ArrayList;
//Toast.makeText(Preview.this,,Toast.LENGTH_LONG).show();
public class Preview extends AppCompatActivity {
    ArrayList<AddpostData> myList=new ArrayList<>();
    String TITLE;
    ViewPostAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.preview);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Preview");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        myList.clear();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myList=getIntent().getBundleExtra("extras").getParcelableArrayList("Arraylist");
        TITLE=getIntent().getBundleExtra("extras").getString("Title");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        AddpostData mLog=new AddpostData();
        mLog.setId("TITLE");
        mLog.setContent(TITLE);
        myList.add(0,mLog);
        mAdapter = new ViewPostAdapter(myList);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
