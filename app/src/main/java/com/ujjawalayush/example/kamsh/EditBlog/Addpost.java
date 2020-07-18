package com.ujjawalayush.example.kamsh.EditBlog;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ujjawalayush.example.kamsh.Adapter.AddPostAdapter;
import com.ujjawalayush.example.kamsh.DBAdapter;
import com.ujjawalayush.example.kamsh.Data.AddpostData;
import com.ujjawalayush.example.kamsh.FirebaseData.PostData;
import com.ujjawalayush.example.kamsh.MainActivity;
import com.ujjawalayush.example.kamsh.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static android.widget.RelativeLayout.*;

public class Addpost extends AppCompatActivity {
    private  String TITLE_OF_KEY ="NO TITLE" ;
    private Toolbar toolbar;
    private ImageButton pic,text,cancel;
    int x=123;
    RelativeLayout relativeLayout;
    int x1=1234;
    int para=0;
    String PREVIOUS_TITLE="",header;
    RecyclerView recyclerView;
    ArrayList<AddpostData> myList,arrayList=new ArrayList<>();
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    FloatingActionButton red,black,green,blue,yellow,skyblue,pink,orange,grey,purple,darkpurple,darkred,darkblue,darkgreen,darkgrey;
    int pos=-1,texting =1235;
    EditText editText;
    float distance;
    String value="";
    LinearLayoutManager linearLayoutManager;
    AddPostAdapter addPostAdapter;
    ImageView imageView;
    Button preview,preview1;
    Bitmap bitmap;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        distance = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics()
        );
        initialize();
        callListener();
        Utility();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    private void text_box(){
        if(imageView.getVisibility()== View.INVISIBLE) {
            TransitionManager.beginDelayedTransition(relativeLayout);
            RelativeLayout.LayoutParams l=(RelativeLayout.LayoutParams)preview.getLayoutParams();
            l.bottomMargin+=distance;
            red.show();
            darkred.show();
            black.show();
            blue.show();
            green.show();
            darkblue.show();
            darkgreen.show();
            yellow.show();
            pink.show();
            orange.show();
            grey.show();
            darkgrey.show();
            skyblue.show();
            purple.show();
            darkpurple.show();
            preview.setLayoutParams(l);
            imageView.setVisibility(VISIBLE);
            cancel.setClickable(true);
            cancel.setVisibility(VISIBLE);
        }
    }
    private boolean visibility(){
        if(imageView.getVisibility()==VISIBLE){
            red.hide();
            black.hide();
            blue.hide();
            green.hide();
            yellow.hide();
            pink.hide();
            orange.hide();
            grey.hide();
            skyblue.hide();
            purple.hide();
            darkgreen.hide();
            darkred.hide();
            darkgrey.hide();
            darkblue.hide();
            darkpurple.hide();
            imageView.setVisibility(View.INVISIBLE);
            cancel.setClickable(false);
            cancel.setVisibility(GONE);
            TransitionManager.beginDelayedTransition(relativeLayout);
            RelativeLayout.LayoutParams l=(RelativeLayout.LayoutParams)preview.getLayoutParams();
            l.bottomMargin-=distance;
            preview.setLayoutParams(l);
            return true;
        }
        return false;
    }
    private void Utility() {
        pic = (ImageButton) findViewById(R.id.addpic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!visibility()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Addpost.this, R.style.Theme_AppCompat_DayNight_Dialog);
                    builder.setTitle("Do you want to add a picture to the post?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, x);
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
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibility())
                    return;
                text_box();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                visibility();
            }
        });
        preview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent(Addpost.this,Preview.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("Arraylist",myList);
                header=editText.getText().toString();
                if(header.equals(""))
                    header="NO TITLE";
                bundle.putString("Title",header);
                data.putExtra("extras",bundle);
                startActivity(data);
            }
        });
    }
    private void initialize() {
        myList=new ArrayList<>();
        red=(FloatingActionButton)findViewById(R.id.red);
        black=(FloatingActionButton)findViewById(R.id.black);
        blue=(FloatingActionButton)findViewById(R.id.blue);
        green=(FloatingActionButton)findViewById(R.id.green);
        yellow=(FloatingActionButton)findViewById(R.id.yellow);
        pink=(FloatingActionButton)findViewById(R.id.pink);
        orange=(FloatingActionButton)findViewById(R.id.orange);
        grey=(FloatingActionButton)findViewById(R.id.grey);
        skyblue=(FloatingActionButton)findViewById(R.id.skyblue);
        purple=(FloatingActionButton)findViewById(R.id.purple);
        darkgreen=(FloatingActionButton)findViewById(R.id.darkgreen);
        darkred=(FloatingActionButton)findViewById(R.id.darkred);
        darkgrey=(FloatingActionButton)findViewById(R.id.darkgrey);
        darkblue=(FloatingActionButton)findViewById(R.id.navyblue);
        darkpurple=(FloatingActionButton)findViewById(R.id.darkpurple);
        editText=(EditText)findViewById(R.id.editText);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        preview1=(Button)findViewById(R.id.preview1);
        cancel=(ImageButton)findViewById(R.id.cancel);
        text=(ImageButton)findViewById(R.id.addtext);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        preview=(Button)findViewById(R.id.preview);
        linearLayoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
        if(getIntent().hasExtra("extras")) {
            myList = getIntent().getBundleExtra("extras").getParcelableArrayList("ARRAYLIST");
            PREVIOUS_TITLE = getIntent().getBundleExtra("extras").getString("TITLE");
            editText.setText(PREVIOUS_TITLE);
            value=getIntent().getBundleExtra("extras").getString("VALUES");
        }
        addPostAdapter = new AddPostAdapter(myList);
        recyclerView.setAdapter(addPostAdapter);
        toolbar.setTitle("Create Post");
        imageView=(ImageView)findViewById(R.id.imageView);
        relativeLayout= (RelativeLayout)findViewById(R.id.relative_view);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
    }

    private void Back() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Addpost.this, R.style.Theme_AppCompat_DayNight_Dialog);
            builder.setTitle("Do you wish to exit without saving ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i=0;i<arrayList.size();i++){
                        if(arrayList.get(i).getId().equals("Image")){
                            File f=new File(arrayList.get(i).getContent());
                            if(f.exists()){
                                boolean s12=f.delete();
                            }
                        }
                    }
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

    private void callListener() {
        addPostAdapter.setOnItemClickListener(new AddPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                if(visibility())
                    return;
                AlertDialog.Builder builder = new AlertDialog.Builder(Addpost.this, R.style.Theme_AppCompat_DayNight_Dialog);
                if(myList.get(position).getId().equals("Image")) {
                    builder.setTitle("Are you sure that you wanna change the Picture ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            pos = position;
                            startActivityForResult(intent, x1);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setNeutralButton("Change Title", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pos = position;
                            DialogEdit();
                        }
                    });
                    builder.show();
                }
                else {
                    builder.setTitle("Are you sure that you wanna edit the text ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent data=new Intent(Addpost.this,Writing.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("ColorCode",getColor(myList.get(position).getContent()));
                            bundle.putString("TEXT",getContent(myList.get(position).getContent()));
                            bundle.putString("POSITION",Integer.toString(position));
                            data.putExtra("extras",bundle);
                            startActivityForResult(data,texting);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setNeutralButton("Change Title", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pos = position;
                            DialogEditText();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onDeleteClick(final int position) {
                if(visibility())
                    return;
                AlertDialog.Builder builder = new AlertDialog.Builder(Addpost.this, R.style.Theme_AppCompat_DayNight_Dialog);
                if(myList.get(position).getId().equals("Image")) {
                    builder.setTitle("Are you sure that you wanna delete the Picture?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myList.remove(position);
                            addPostAdapter.notifyItemRemoved(position);
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
                else {
                    builder.setTitle("Are you sure that you wanna remove the text ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myList.remove(position);
                            addPostAdapter.notifyItemRemoved(position);
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
            }

            @Override
            public void onViewClick(int position) {
                if(visibility())
                    return;
                if(myList.get(position).getId().equals("Text")){
                    Intent data =new Intent(Addpost.this,ViewText.class);
                    Bundle b=new Bundle();
                    b.putString("text",myList.get(position).getContent());
                    b.putString("name",TITLE_OF_KEY);
                    data.putExtra("bundle",b);
                    startActivity(data);
                }
                else {
                    Intent data = new Intent(Addpost.this, ViewImage.class);
                    Bundle b = new Bundle();
                    b.putString("bitmap", myList.get(position).getContent());
                    b.putString("name", TITLE_OF_KEY);
                    data.putExtra("bundle", b);
                    startActivity(data);
                }
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save){
            Insertion insertion=new Insertion();
            insertion.execute();
        }
        else if(item.getItemId()==R.id.Upload){
            AlertDialog.Builder builder = new AlertDialog.Builder(Addpost.this, R.style.Theme_AppCompat_DayNight_Dialog);
            builder.setTitle("Do you wanna go anonymous ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addAnonymously();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    int count=0;
    private void addAnonymously() {
        Insertion1 insertion1=new Insertion1();
        insertion1.execute();
        for(int i=0;i<myList.size();i++){
            if(myList.get(i).getId().equals("Image"))
                count++;
        }
        if(count==0){
            PostData mLog=new PostData();
            String title=editText.getText().toString();
            if(title.equals(""))
                title="NO TITLE";
            mLog.setTitle(title);
            mLog.setAuthor("Anonymous");
            mLog.setPhoto("");
            mLog.setMyList(myList);
            mLog.setTime(-System.currentTimeMillis());
            DatabaseReference databaseReference1=databaseReference.child("Posts").push();
            databaseReference1.setValue(mLog).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Addpost.this, "Upload Successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Addpost.this, "Upload Failed", Toast.LENGTH_LONG).show();
                }
            });

        }
        else {
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).getId().equals("Image")) {
                    File f = new File(myList.get(i).getContent());
                    Uri uri = Uri.fromFile(f);
                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Long.toString(System.currentTimeMillis()) + ".jpeg");
                    final int finalI = i;
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri1) {
                                    myList.get(finalI).setUri(uri1);
                                    count--;
                                    if (count == 0) {
                                        PostData mLog = new PostData();
                                        String title = editText.getText().toString();
                                        if (title.equals(""))
                                            title = "NO TITLE";
                                        mLog.setAuthor("Anonymous");
                                        mLog.setPhoto("");
                                        mLog.setTitle(title);
                                        mLog.setMyList(myList);
                                        mLog.setTime(-System.currentTimeMillis());
                                        DatabaseReference databaseReference1 = databaseReference.child("Posts").push();
                                        databaseReference1.setValue(mLog).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Addpost.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Addpost.this, "Upload Failed", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Addpost.this, "Sorry!! Failed to add Data", Toast.LENGTH_LONG).show();
                            count=-1;
                        }
                    });
                }
                if(count==-1)
                    break;
            }
        }
        Insertion insertion=new Insertion();
        insertion.execute();
        onBackPressed();
    }

    private class Insertion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            header=editText.getText().toString();
            if(header.equals(""))
                header="NO TITLE";
            DBAdapter db = new DBAdapter(Addpost.this);
            db.open();
            Cursor c=db.getAllTitles();
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(c.getString(1).equals(header)&&!c.getString(1).equals(PREVIOUS_TITLE)) {
                    para = 1;
                    return null;
                }
                c.moveToNext();
            }
            db.deleteContent(PREVIOUS_TITLE);
            db.deleteTitle(PREVIOUS_TITLE);
            db.insertTitle(header,value);
            for (int i = 0; i < myList.size(); i++)
                db.insertContent(header,myList.get(i).getId(),myList.get(i).getTitle(),myList.get(i).getContent());
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            if(para==1){
                para=0;
                Toast.makeText(Addpost.this, "Such a title already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(Addpost.this,"Successfully added to Drafts",Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }
    private class Insertion1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            header=editText.getText().toString();
            if(header.equals(""))
                header="NO TITLE";
            DBAdapter db = new DBAdapter(Addpost.this);
            db.open();
            Cursor c=db.getAllTitles();
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(c.getString(1).equals(header)&&!c.getString(1).equals(PREVIOUS_TITLE)) {
                    para = 1;
                    return null;
                }
                c.moveToNext();
            }
            db.deleteContent(PREVIOUS_TITLE);
            db.deleteTitle(PREVIOUS_TITLE);
            db.insertTitle(header,value);
            for (int i = 0; i < myList.size(); i++)
                db.insertContent(header,myList.get(i).getId(),myList.get(i).getTitle(),myList.get(i).getContent());
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            if(para==1){
                para=0;
                Toast.makeText(Addpost.this, "Cannot be added to Drafts", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(Addpost.this,"Successfully added to Drafts",Toast.LENGTH_LONG).show();
        }
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
    public String getFileExtension(Uri uri1){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri1));
    }
    private String InsertImage(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
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
    private void DialogEdit(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addpost.this);
        alertDialog.setTitle("Title");
        alertDialog.setMessage("Enter image title");

        final EditText input = new EditText(Addpost.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_edit_black_24dp);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        TITLE_OF_KEY=input.getText().toString().trim();
                        AddpostData mLog=myList.get(pos);
                        if(TITLE_OF_KEY.equals(""))
                                TITLE_OF_KEY+="NO TITLE";
                        mLog.setTitle(TITLE_OF_KEY);
                        myList.set(pos,mLog);
                        addPostAdapter.notifyItemChanged(pos);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    private void DialogEditText(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Addpost.this);
        alertDialog.setTitle("Title");
        alertDialog.setMessage("Enter text title");

        final EditText input = new EditText(Addpost.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_edit_black_24dp);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        TITLE_OF_KEY=input.getText().toString().trim();
                        AddpostData mLog=myList.get(pos);
                        if(TITLE_OF_KEY.equals(""))
                            TITLE_OF_KEY+="NO TITLE";
                        mLog.setTitle(TITLE_OF_KEY);
                        myList.set(pos,mLog);
                        addPostAdapter.notifyItemChanged(pos);
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==x&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                String s=InsertImage(bitmap);
                if(s!=null){
                    int size=myList.size();
                    pos=size;
                    DialogEdit();
                    Toast.makeText(Addpost.this,"Image Successfully Added",Toast.LENGTH_LONG).show();
                    AddpostData mLog=new AddpostData();
                    mLog.setId("Image");
                    mLog.setTitle(TITLE_OF_KEY);
                    mLog.setContent(s);
                    mLog.setHeight(bitmap.getHeight());
                    myList.add(mLog);
                    arrayList.add(mLog);
                    addPostAdapter.notifyItemInserted(size);
                    TITLE_OF_KEY="NO TITLE";
                }
                bitmap.recycle();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode==x1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null&&pos!=RecyclerView.NO_POSITION){
            uri=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                String s=InsertImage(bitmap);
                if(s!=null){
                   Toast.makeText(Addpost.this,"Image Successfully Changed",Toast.LENGTH_LONG).show();
                    AddpostData mLog=new AddpostData();
                    mLog.setId("Image");
                    mLog.setContent(s);
                    DialogEdit();
                    mLog.setTitle(TITLE_OF_KEY);
                    AddpostData mLog1;
                    mLog1=myList.get(pos);
                    File f=new File(mLog1.getContent());
                    if(f.exists()) {
                        boolean s1=f.delete();
                    }
                    arrayList.add(mLog);
                    myList.set(pos,mLog);
                    addPostAdapter.notifyItemChanged(pos);
                    TITLE_OF_KEY="NO TITLE";
                }
                bitmap.recycle();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode==texting&&resultCode==texting&&data!=null){
            AddTextView(data.getStringExtra("STRING"));
        }
        else if(requestCode==texting&&resultCode==RESULT_OK&&data!=null){
            EditTextView(data.getBundleExtra("extras").getString("STRING"),data.getBundleExtra("extras").getString("POSITION"));
        }
    }

    private void EditTextView(String s, String position) {
        pos=Integer.parseInt(position);
        Toast.makeText(Addpost.this,"Text Successfully Changed",Toast.LENGTH_LONG).show();
        AddpostData mLog=new AddpostData();
        mLog.setId("Text");
        mLog.setContent(s);
        mLog.setTitle(myList.get(pos).getTitle());
        myList.set(pos,mLog);
        addPostAdapter.notifyItemChanged(pos);
    }

    private void AddTextView(String s) {
        if(s.equals(""))
            return;
        Toast.makeText(Addpost.this,"Text Successfully Added",Toast.LENGTH_LONG).show();
        AddpostData mLog=new AddpostData();
        mLog.setId("Text");
        mLog.setContent(s);
        DialogEditText();
        mLog.setTitle(TITLE_OF_KEY);
        pos=myList.size();
        myList.add(mLog);
        addPostAdapter.notifyItemInserted(pos);
        TITLE_OF_KEY="NO TITLE";
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent data=new Intent(Addpost.this,MainActivity.class);
        startActivity(data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addpost_toolbar,menu);
        return true;
    }
    public void onClick(View view) {
        ColorStateList v=view.getBackgroundTintList();
        assert v != null;
        final int u=v.getDefaultColor();
        Intent data=new Intent(Addpost.this,Writing.class);
        Bundle bundle=new Bundle();
        bundle.putString("ColorCode",Integer.toString(u));
        data.putExtra("extras",bundle);
        startActivityForResult(data,texting);
    }
}