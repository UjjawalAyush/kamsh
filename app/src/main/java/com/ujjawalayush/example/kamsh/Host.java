package com.ujjawalayush.example.kamsh;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.ujjawalayush.example.kamsh.FirebaseData.ContestData;

import java.io.IOException;
import java.util.ArrayList;

public class Host extends AppCompatActivity {
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    ArrayList<String> arrayList=new ArrayList<>();
    Spinner spinner;
    Uri uri=null;
    Bitmap bitmap;
    TextInputLayout inputLayout;
    com.mikhaellopez.circularimageview.CircularImageView circularImageView;
    EditText start_date,start_time,end_date,end_time,description,prizes;
    EditText name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host);
        initialise();
        Spinners();
    }

    private void Spinners() {
        arrayList.add("Pictures and Text");
        arrayList.add("Only Pictures");
        arrayList.add("Only Text");
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
    }

    private void initialise() {
        inputLayout=(TextInputLayout)findViewById(R.id.textInput1);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        name=(EditText)findViewById(R.id.name);
        circularImageView=(com.mikhaellopez.circularimageview.CircularImageView)findViewById(R.id.circularImageView);
        start_date=(EditText)findViewById(R.id.startdate);
        start_time=(EditText)findViewById(R.id.starttime);
        end_date=(EditText)findViewById(R.id.enddate);
        end_time=(EditText)findViewById(R.id.endtime);
        description=(EditText)findViewById(R.id.des);
        prizes=(EditText)findViewById(R.id.prizes);
        toolbar.setTitle("Add Contest");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
    }
    public void onClick(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    public boolean isNetwork() {
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            Toast.makeText(Host.this,"Please Check Network Connectivity",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Picasso.get().load(uri).centerCrop().fit().into(circularImageView);
        }
    }
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.host_toolbar,menu);
        return true;
    }
    private void Back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Host.this, R.style.Theme_AppCompat_DayNight_Dialog);
        builder.setTitle("Do you wish to exit without saving ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Upload){
            if(!isNetwork()){
                Toast.makeText(Host.this,"Please Check Network Connectivity",Toast.LENGTH_LONG).show();
            }
            else
                insert();
        }
        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        final String contest_name=name.getText().toString().trim();
        if(contest_name.equals("")){
            inputLayout.setError("Choose a Contest name first!!");
            inputLayout.requestFocus();
            return;
        }
        final String des=description.getText().toString().trim();
        if(des.equals("")){
            description.setError("Write Description");
            description.requestFocus();
            return;
        }
        final String p=prizes.getText().toString().trim();
        if(p.equals("")){
            prizes.setError("Write About Prizes");
            prizes.requestFocus();
            return;
        }
        final String startdate=start_date.getText().toString().trim();
        if(startdate.length()!=10||startdate.charAt(2)!='/'||startdate.charAt(5)!='/'){
            start_date.setError("Input Correct Start Date");
            start_date.requestFocus();
            return;
        }
        final String enddate=end_date.getText().toString().trim();
        if(enddate.length()!=10||enddate.charAt(2)!='/'||enddate.charAt(5)!='/'){
            end_date.setError("Input Correct End Date");
            end_date.requestFocus();
            return;
        }
        final String starttime=start_time.getText().toString().trim();
        if(starttime.length()!=5||starttime.charAt(2)!=':'){
            start_time.setError("Input Correct Start Time");
            start_time.requestFocus();
            return;
        }
        final String endtime=end_time.getText().toString().trim();
        if(endtime.length()!=5||endtime.charAt(2)!=':'){
            end_time.setError("Input Correct End Time");
            end_time.requestFocus();
            return;
        }
        final DatabaseReference databaseReference1=databaseReference.child("Contests").child(contest_name);
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0){
                    if(uri==null){
                        ContestData mLog=new ContestData();
                        mLog.setDescription(des);
                        mLog.setEnddate(enddate);
                        mLog.setEndtime(endtime);
                        mLog.setPrizes(p);
                        mLog.setStarttime(starttime);
                        mLog.setStartdate(startdate);
                        mLog.setName(contest_name);
                        mLog.setType(spinner.getSelectedItem().toString());
                        databaseReference1.setValue(mLog);
                        Toast.makeText(Host.this,"Contest Successfully Added",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else {
                        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Long.toString(System.currentTimeMillis()) + ".jpeg");
                        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri1) {
                                        uri = uri1;
                                        ContestData mLog=new ContestData();
                                        mLog.setDescription(des);
                                        mLog.setEnddate(enddate);
                                        mLog.setEndtime(endtime);
                                        mLog.setPrizes(p);
                                        mLog.setStarttime(starttime);
                                        mLog.setStartdate(startdate);
                                        mLog.setName(contest_name);
                                        mLog.setUri(uri.toString());
                                        mLog.setType(spinner.getSelectedItem().toString());
                                        databaseReference1.setValue(mLog);
                                        Toast.makeText(Host.this,"Contest Successfully Added",Toast.LENGTH_LONG).show();
                                        onBackPressed();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Host.this,"Failed to Add Contest!!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
                else{
                    inputLayout.setError("Choose a Different Contest name!!");
                    inputLayout.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
