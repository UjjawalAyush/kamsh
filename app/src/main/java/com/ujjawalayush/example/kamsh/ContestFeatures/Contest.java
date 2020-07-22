package com.ujjawalayush.example.kamsh.ContestFeatures;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.EditBlog.Addpost;
import com.ujjawalayush.example.kamsh.MainActivity;
import com.ujjawalayush.example.kamsh.R;

public class Contest extends AppCompatActivity {
    Toolbar toolbar;
    Bundle extras;
    Fragment fragment_home;
    Long current;
    Fragment fragment;
    Fragment fragment_contest;
    String name;
    FragmentManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contests);
        extras=getIntent().getBundleExtra("extras");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("Name"));
        name=extras.getString("Name");
        current=extras.getLong("Time");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fm = getSupportFragmentManager();
        Bundle data1=new Bundle();
        data1.putString("Name",name);
        fragment_contest=new LeaderboardFragment();
        fragment_home=new ContestHomeFragment();
        fragment_home.setArguments(data1);
        fragment_contest.setArguments(data1);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.frame_container,fragment_contest, "2").hide(fragment_contest).commit();
        fm.beginTransaction().add(R.id.frame_container,fragment_home, "1").commit();

    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.home:
                    fm.beginTransaction().hide(fragment_contest).show(fragment_home).commit();
                    return true;
                case R.id.posts:
                    if(extras.getString("Type").equals("2")) {
                        Intent data = new Intent(Contest.this, AddNewpost.class);
                        Bundle extras=new Bundle();
                        extras.putString("Name", name);
                        extras.putLong("Time", current);
                        data.putExtra("extras",extras);
                        startActivity(data);
                    }
                    else if(extras.getString("Type").equals("1"))
                        Toast.makeText(Contest.this,"Sorry!! Contest Over !!",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Contest.this,"Contest is yet to start!!",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.leaderboard:
                    fm.beginTransaction().hide(fragment_home).show(fragment_contest).commit();
                    return true;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contest_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.info){

        }
        return super.onOptionsItemSelected(item);
    }
}
