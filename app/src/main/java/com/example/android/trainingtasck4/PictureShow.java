package com.example.android.trainingtasck4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureShow extends AppCompatActivity {
    private Bitmap extraPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show);

        Intent intent = getIntent();
        extraPhoto = (Bitmap) intent.getParcelableExtra("PhotoBitmap");
        ImageView imageView = (ImageView) findViewById(R.id.photo_centent);
        imageView.setImageBitmap(extraPhoto);

    }


}