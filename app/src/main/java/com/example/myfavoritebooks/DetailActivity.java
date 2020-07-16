package com.example.myfavoritebooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    Bundle bundle;
    ImageView imageView;
    TextView userMailText,bookNameText,authorText,aboutText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.imageView);
        userMailText = findViewById(R.id.userMailText);
        bookNameText = findViewById(R.id.bookNameText);
        authorText=findViewById(R.id.authorText);
        aboutText = findViewById(R.id.aboutText);
        bundle=getIntent().getExtras();
        String userMail=bundle.getString("userMail");
        String bookName=bundle.getString("bookName");
        String author=bundle.getString("author");
        String about=bundle.getString("about");
        String imageUrl=bundle.getString("imageUrl");
        userMailText.setText(userMail);
        bookNameText.setText(bookName);
        authorText.setText(author);
        aboutText.setText(about);
        Picasso.get().load(imageUrl).resize(400,400).centerCrop().rotate(90).into(imageView);


    }
}
