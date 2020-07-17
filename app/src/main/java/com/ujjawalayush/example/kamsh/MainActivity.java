package com.ujjawalayush.example.kamsh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        Fragment fragment=new HomeFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frame_container, fragment);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch(menuItem.getItemId()){
                case R.id.home:
                    fragment=new HomeFragment();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.frame_container, fragment);
                    transaction1.addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.posts:
                    Intent data=new Intent(MainActivity.this,Addpost.class);
                    startActivity(data);
                    return true;
                case R.id.notifications:
                    fragment=new NotificationFragment();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frame_container, fragment);
                    transaction3.addToBackStack(null);
                    transaction3.commit();
                    return true;
                case R.id.drafts:
                    fragment=new DraftFragment();
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                    transaction4.replace(R.id.frame_container, fragment);
                    transaction4.addToBackStack(null);
                    transaction4.commit();
                    return true;
                case R.id.contest:
                    fragment=new ContestFragment();
                    FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                    transaction5.replace(R.id.frame_container, fragment);
                    transaction5.addToBackStack(null);
                    transaction5.commit();
                    return true;
            }
            return false;
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
}
