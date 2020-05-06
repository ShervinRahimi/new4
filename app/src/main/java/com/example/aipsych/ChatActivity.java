package com.example.aipsych;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private Controller controller;
    private StringBuilder bodyText;
    private String uuid;
    private String user;
    private TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        bodyText = new StringBuilder();
        setContentView(R.layout.activity_chat);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        uuid = prefs.getString("UUID", null);
        user = prefs.getString("USER", "You");
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            prefs.edit().putString("UUID", uuid).apply();
        }
        Log.d("UUID", uuid);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        myText = findViewById(R.id.textbox);
        myText.setMovementMethod(new ScrollingMovementMethod());
        myDatabase.child(uuid).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("CONSOLE", dataSnapshot.getValue().toString());
                    String currentDatabaseText = dataSnapshot.getValue().toString();
                    myText.setText(currentDatabaseText);
                    if(bodyText.toString().equals("")){
                        bodyText.append(currentDatabaseText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                myText.setText("CANCELLED");

            }
        });

        myText.setText("YAYAYAYAYA COME COME COME");
    }

    public void sendMessage(View view) throws JSONException {

        final EditText myEditText = findViewById(R.id.editText);
        String userMessage = myEditText.getText().toString();
        bodyText.append(user + ": " + userMessage + "\n");
        try {
            Volley.newRequestQueue(getApplicationContext()).add(controller.getMessage(userMessage, new VolleyCallback() {
                @Override
                public void onSuccess(String result){
                    try {
                        JSONObject json = new JSONObject(result);
                        String botMessage = json.getJSONObject("result").getJSONObject("fulfillment").getString("speech");
                        bodyText.append("Bot: " + botMessage + "\n");
                        myDatabase.child(uuid).child("messages").setValue(bodyText.toString());
                        myEditText.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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