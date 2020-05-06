package com.example.aipsych;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.example.aipsych.R;

public class MainActive extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void Begin (View v){
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

}
