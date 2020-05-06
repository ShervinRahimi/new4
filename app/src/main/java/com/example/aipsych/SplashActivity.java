package com.example.aipsych;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name,email,gender,dob;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        gender = (EditText) findViewById(R.id.gender);
        dob = (EditText) findViewById(R.id.dateofbirth);
        textView = findViewById(R.id.textView_id);

        button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick (View v){

        String name1 = name.getText().toString();
        String email1 = email.getText().toString();
        String gender1 = gender.getText().toString();
        String dob1 = dob.getText().toString();

        if(v.getId() == R.id.button_id){

            textView.setText("Name: " + name1 + "\n" + "Email: " + email1 + "\n" + "Gender: " + gender1 + "\n" + "Date of Birth: " + dob1);


        }


        // Write a test message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World! " + LocalDate.now());

        //Write a test message to the dataflow API
        Controller con = new Controller();
        try {
            Volley.newRequestQueue(getApplicationContext()).add(con.getMessage("Hey", new VolleyCallback() {
                @Override
                public void onSuccess(String result){
                    try {
                        JSONObject json = new JSONObject(result);
                        Log.d("MESSAGE", json.getJSONObject("result").getJSONObject("fulfillment").getString("speech"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Next (View v){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

}