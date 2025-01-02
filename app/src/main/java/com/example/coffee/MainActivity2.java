package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity2 extends AppCompatActivity {

    ImageView imageView,back;
    TextView name,pice,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        Intent intent = getIntent();
        String nam=intent.getStringExtra("name");
        Double price= intent.getDoubleExtra("price",0.0);
        String url=intent.getStringExtra("image");
        String descrip=intent.getStringExtra("description");


        name=findViewById(R.id.name);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        description.setText("Description:"+descrip);
        pice=findViewById(R.id.price);
        imageView=findViewById(R.id.iamges);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        name.setText(nam);
        pice.setText(String.valueOf(price));
        Glide.with(this)
                .load(url)  // The URL of the image
                .into(imageView);





    }
}