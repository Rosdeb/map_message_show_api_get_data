package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class SplashScreen extends AppCompatActivity {

    MaterialButton materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        materialButton=findViewById(R.id.start);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                SharedPreferences sharedPreferences = getSharedPreferences("Rosdeb",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstTime", false); // Mark as not first time
                editor.apply();
            }
        });




    }


}