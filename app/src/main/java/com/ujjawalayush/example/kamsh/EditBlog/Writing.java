package com.ujjawalayush.example.kamsh.EditBlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.R;

public class Writing extends AppCompatActivity {
    int colorCode;
    ImageView imageView;
    EditText editText;
    int pos;
    Toolbar toolbar;
    String start;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);
        Intent data=getIntent();
        colorCode=Integer.parseInt(data.getBundleExtra("extras").getString("ColorCode"));
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Enter Text");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        start="{";
        start+=Integer.toString(colorCode);
        start+='}';
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editText=(EditText)findViewById(R.id.text);
        editText.setTextColor(colorCode);
        editText.setHintTextColor(colorCode);
        if(data.getBundleExtra("extras").getString("TEXT")!=null) {
            pos=Integer.parseInt(data.getBundleExtra("extras").getString("POSITION"));
            editText.setText(data.getBundleExtra("extras").getString("TEXT"));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.writing_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Upload){
            String s=editText.getText().toString();
            s = start + s;
            Intent intent=new Intent();
            if(getIntent().getBundleExtra("extras").getString("TEXT")==null) {
                setResult(1235, intent);
                intent.putExtra("STRING",s);
            }
            else {
                Bundle extras=new Bundle();
                extras.putString("STRING",s);
                extras.putString("POSITION", Integer.toString(pos));
                intent.putExtra("extras",extras);
                setResult(RESULT_OK, intent);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        if(getIntent().getBundleExtra("extras").getString("TEXT")==null) {
            setResult(1235, intent);
            intent.putExtra("STRING","");
        }
        else
            setResult(-11111, intent);
        finish();
        super.onBackPressed();
    }
}