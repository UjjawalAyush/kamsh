package com.ujjawalayush.example.kamsh.EditBlog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ortiz.touchview.TouchImageView;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewImage extends AppCompatActivity {

    Toolbar toolbar;
    TouchImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent data=getIntent();
        Bundle bundle=data.getBundleExtra("bundle");
        String s=bundle.getString("bitmap");
        String title=bundle.getString("name");
        Bitmap bitmap=getImage(s);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.viewimage);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageView=(TouchImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
