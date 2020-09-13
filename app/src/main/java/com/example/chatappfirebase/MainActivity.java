package com.example.chatappfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final int SIGN_IN_REQUEST_CODE = 1000;
    EditText editText;
    FloatingActionButton fb;
    ListView listView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseListAdapter<ModelClass> myAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        validateUser();
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        setSupportActionBar(toolbar);
    }

    private void sendMessage() {
        mRef.child("Users").child(mAuth.getCurrentUser().getDisplayName()).push().
                setValue(new ModelClass(editText.getText().toString(),mAuth.getCurrentUser().getDisplayName()));


        FirebaseDatabase.getInstance().getReference().child("doctor").child("chat").child(mAuth.getCurrentUser().getDisplayName()).
                push().setValue(new ModelClass(editText.getText().toString(),mAuth.getCurrentUser().getDisplayName()));
        editText.setText("");
    }

    private void validateUser() {
        if(mAuth.getCurrentUser()==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else {
            displayChatMessage();
        }
    }
    private void displayChatMessage() {
        myAdapter=new FirebaseListAdapter<ModelClass>(this,ModelClass.class,R.layout.message,mRef.child("Users").child(mAuth.getCurrentUser().getDisplayName())) {
            @Override
            protected void populateView(View v, ModelClass model, int position) {
                TextView messageText=v.findViewById(R.id.message_text);
                TextView messanger=v.findViewById(R.id.message_user);
                TextView messageTime=v.findViewById(R.id.message_time);
                messageText.setText(model.getMessage());
                messanger.setText(model.getDisplayName());
                messageTime.setText(DateFormat.format("dd-mm-yyyy(HH:mm:ss)",model.getTime()));
            }
        };
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE && resultCode==RESULT_OK){
            Toast.makeText(getApplicationContext(),"Successfully sign In",Toast.LENGTH_SHORT).show();
            displayChatMessage();
        }else {
            Toast.makeText(getApplicationContext(),"signIn Failed",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initViews() {
        editText=findViewById(R.id.editText);
        fb=findViewById(R.id.floatingActionButton);
        listView=findViewById(R.id.list);
        database=FirebaseDatabase.getInstance();
        mRef=database.getReference();
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Successfully signOut",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"SignOut Failed",Toast.LENGTH_LONG).show();
                    }
                });
            case R.id.book:
                startActivity(new Intent(MainActivity.this,BookActivtiy.class));

        }
        return super.onOptionsItemSelected(item);
    }
}