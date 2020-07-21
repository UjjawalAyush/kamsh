package com.ujjawalayush.example.kamsh.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ujjawalayush.example.kamsh.EditBlog.Addpost;
import com.ujjawalayush.example.kamsh.MainActivity;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;

public class LogIn extends AppCompatActivity {
    Toolbar toolbar;
    Button logIn;
    ProgressDialog progressDialog;
    EditText email,password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Log In");
        email=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        logIn=(Button)findViewById(R.id.login);
        Utility();
    }

    private void Utility() {
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetwork()){
                    Toast.makeText(LogIn.this,"Please Check Internet Connectivity",Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog=new ProgressDialog(LogIn.this);
                String e=email.getText().toString().trim();
                String p=password.getText().toString().trim();
                if(e.equals("")){
                    email.setError("Enter Valid Email Id");
                    email.requestFocus();
                    return;
                }
                if(p.equals("")){
                    password.setError("Enter Valid Password");
                    password.requestFocus();
                    return;
                }
                progressDialog.setMessage("Logging in!Please wait...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                FirebaseAuth.getInstance().signInWithEmailAndPassword(e,p).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            if (firebaseUser.isEmailVerified()) {
                                Toast.makeText(LogIn.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                Intent data = new Intent(LogIn.this,MainActivity.class);
                                progressDialog.dismiss();
                                startActivity(data);

                            } else {
                                Toast.makeText(LogIn.this, "First verify email and then try again", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(LogIn.this, "Invalid E-Mail Id or Password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

    }
    public boolean isNetwork(){
        try {
            NetworkInfo networkInfo = null;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }
        catch(NullPointerException e){
            return false;
        }
    }
    public void onClick(View v){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogIn.this);
        alertDialog.setTitle("Forgot Password");
        alertDialog.setMessage("Enter Email Id");

        final EditText input = new EditText(LogIn.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_email_black_24dp);
        alertDialog.setPositiveButton("Reset Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String s=input.getText().toString().trim();
                if(s.equals("")){
                    input.setError("Enter Your Email-Id");
                    input.requestFocus();
                    return;
                }
                progressDialog=new ProgressDialog(LogIn.this);
                progressDialog.setMessage("Sending Verification!Please wait...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                FirebaseAuth.getInstance().sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogIn.this,"Password reset email has been sent to "+s+" !",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(LogIn.this,"Failed to send reset email! Please try again!",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }
    public void onClick1(View v){
        Intent data=new Intent(LogIn.this,SignUp.class);
        startActivity(data);
    }
}
