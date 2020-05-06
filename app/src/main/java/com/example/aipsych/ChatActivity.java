package com.example.aipsych;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Date;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;import com.example.aipsych.Controller;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myDatabase = FirebaseDatabase.getInstance().getReference("Message");


        final TextView  myText = findViewById(R.id.textbox);
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            myText.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                myText.setText("CANCELLED");

            }
        });
    }

    public void sendMessage(View view){

        EditText myEditText = findViewById(R.id.editText);

        myDatabase.setValue(myEditText.getText().toString());
        myEditText.setText("");

    }

    private String messageText;
    private String messageBot;
    private long messageTime;

    public ChatActivity(String messageText, String messageBot){

        this.messageText = messageText;
        this.messageBot = messageBot;

        messageTime = new Date().getTime();

    }

    public ChatActivity(){

    }

    public String getMessageText(){

        return messageText;
    }

    public void setMessageText(String messageText){

        this.messageText = messageText;
    }

    public String getMessageBot(){

        return messageBot;
    }

    public void setMessageBot(){

        this.messageBot = messageBot;
    }

    public long getMessageTime(){

        return messageTime;
    }

    public void setMessageTime(long messageTime){

        this.messageTime = messageTime;
    }

}