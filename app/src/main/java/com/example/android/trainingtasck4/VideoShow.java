package com.example.android.trainingtasck4;

import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;

public class VideoShow extends AppCompatActivity {
  Uri extraVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        Intent intent = getIntent();
        extraVideo = (Uri) intent.getParcelableExtra("videoUri");
        VideoView vidView = (VideoView) findViewById(R.id.video_centent);
        vidView.setVideoURI(extraVideo);
        final MediaController mediaController = new     MediaController(this);
        mediaController.setAnchorView(vidView);
        vidView.setMediaController(mediaController);
        vidView.start();


    }

}
