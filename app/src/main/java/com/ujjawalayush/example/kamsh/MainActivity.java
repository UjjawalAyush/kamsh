package com.ujjawalayush.example.kamsh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ujjawalayush.example.kamsh.ContestFeatures.Host;
import com.ujjawalayush.example.kamsh.EditBlog.Addpost;
import com.ujjawalayush.example.kamsh.Fragments.ContestFragment;
import com.ujjawalayush.example.kamsh.Fragments.DraftFragment;
import com.ujjawalayush.example.kamsh.Fragments.HomeFragment;
import com.ujjawalayush.example.kamsh.Fragments.NotificationFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment fragment_home;
    Fragment fragment_drafts,fragment_notification,fragment_contest;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("KAMSH");
        setSupportActionBar(toolbar);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        View header=navigationView.getHeaderView(0);
        final TextView textView=header.findViewById(R.id.name);
        final com.mikhaellopez.circularimageview.CircularImageView circularImageView=header.findViewById(R.id.circularImageView);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout= (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment_home=new HomeFragment();
        fragment_drafts=new DraftFragment();
        fragment_notification=new NotificationFragment();
        fragment_contest=new ContestFragment();
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame_container,fragment_drafts, "4").hide(fragment_drafts).commit();
        fm.beginTransaction().add(R.id.frame_container,fragment_notification, "3").hide(fragment_notification).commit();
        fm.beginTransaction().add(R.id.frame_container,fragment_contest, "2").hide(fragment_contest).commit();
        fm.beginTransaction().add(R.id.frame_container,fragment_home, "1").commit();
    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.home:
                    fm.beginTransaction().show(fragment_home).hide(fragment_drafts).hide(fragment_contest).hide(fragment_notification).commit();
                    return true;
                case R.id.posts:
                    Intent data=new Intent(MainActivity.this,Addpost.class);
                    startActivity(data);
                    return true;
                case R.id.notifications:
                    fm.beginTransaction().hide(fragment_home).hide(fragment_drafts).hide(fragment_contest).show(fragment_notification).commit();
                    return true;
                case R.id.drafts:
                    fragment_drafts=new DraftFragment();
                    fm.beginTransaction().add(R.id.frame_container,fragment_drafts, "4").hide(fragment_home).show(fragment_drafts).hide(fragment_contest).hide(fragment_notification).commit();
                    return true;
                case R.id.contest:
                    fm.beginTransaction().hide(fragment_home).hide(fragment_drafts).show(fragment_contest).hide(fragment_notification).commit();
                    return true;
            }
            return true;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){
        Intent data=new Intent(MainActivity.this,Host.class);
        startActivity(data);
    }
    public void onClick1(View v){

    }
}
