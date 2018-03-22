package com.example.android.trainingtasck4;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    private boolean state=true;
    private EditText editText;
    private TextView userName;
    private FrameLayout frameLayout;
    private ImageView cameraImage,returnImage,basculeImage,userImage,setReadImage,setReceivedImage;
    private String user="one";
    Context context;
    public ListView listView;
    public ChatAdapter adapter;
    ArrayList<MessageClass> messageList;

    private Bitmap mImageView;
    private Uri mVideoURI;



    private final static int HAS_IMG= -2;
    private final static int HAS_VID=-3;


    private int msgStatutSend=1;
    private int msgStatutRead=2;
    private int msgStatutReceived=3;

    private static final int VIDEO_CAPTURE = 101;
    private static final int TAKE_PICTURE = 1;
    private int nbrOfVideos=0;

    String mVideoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=this;
        cameraImage=(ImageView)findViewById(R.id.camera);
        userImage=(ImageView)findViewById(R.id.userimage);
        basculeImage=(ImageView)findViewById(R.id.bascule);
        returnImage=(ImageView)findViewById(R.id.returnimg);
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        userName=(TextView)findViewById(R.id.username);
        messageList = new ArrayList<MessageClass>();
        listView = (ListView) findViewById(R.id.list);
        setReadImage=(ImageView)findViewById(R.id.set_read);
        setReceivedImage=(ImageView)findViewById(R.id.set_received);

        updateAdapter();


        editText=(EditText)findViewById(R.id.edit_text) ;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cameraImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cameraImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.getText().toString().equals(""))cameraImage.setVisibility(View.VISIBLE);
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(user.equals("one"))state=true;
            if(user.equals("two"))state=false;
                Calendar instance = Calendar.getInstance();
                int currentHour = instance.get(Calendar.HOUR_OF_DAY);
                int minute = instance.get(Calendar.MINUTE);
                String time = getString(R.string.message_send_at)+ String.valueOf(currentHour) + ":" + String.valueOf(minute);
                messageList.add(new MessageClass(editText.getText().toString(),time,R.drawable.msg_status_gray_waiting,state,msgStatutSend));
            updateAdapter();
            editText.setText("");
            cameraImage.setVisibility(View.VISIBLE);
            }
        });

        returnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main2Activity.this, MainActivity.class);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

                }
            }
        });
        basculeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.equals("one")){
                    String d=getString(R.string.user_client_name);
                    userName.setText(d);
                    userImage.setImageResource(R.drawable.palestine);
                    user="two";
                }else{
                    String d=getString(R.string.user_server_name);
                    userName.setText(d);
                    userImage.setImageResource(R.drawable.algerie);
                    user="one";
                }

            }
        });

        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.custom_dialog, null);

                final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog_Alert);
                final TextView photo_text = (TextView) dialogLayout.findViewById(R.id.choice1);
                final TextView video_text = (TextView) dialogLayout.findViewById(R.id.choice2);
                photo_text.setText(getString(R.string.choice_photo));
                video_text.setText(getString(R.string.choice_video));
                dialog.setContentView(dialogLayout);

                photo_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, TAKE_PICTURE);
                            }

                    }
                });
                video_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        nbrOfVideos+=1;
                        mVideoPath=Environment.getExternalStorageDirectory().getAbsolutePath()+
                                "/myvideo"+nbrOfVideos+".mp4";
                        File mediaFile = new File(mVideoPath);
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        Uri videoUri = Uri.fromFile(mediaFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                        startActivityForResult(intent, VIDEO_CAPTURE);

                    }
                });
                dialog.show();

            }
        });

        setReadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageList.isEmpty()) {
                    for(int i=0 ; i< messageList.size() ; i++) {
                        if (messageList.get(i).getMessageStatut() == msgStatutSend || messageList.get(i).getMessageStatut() == msgStatutReceived)
                        {
                        messageList.get(i).setMessageStatut(msgStatutRead);
                        messageList.get(i).setImageState(R.drawable.msg_status_client_read);
                        }
                    }
                    updateAdapter();
                }
            }
        });
        setReceivedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageList.isEmpty()) {
                    for(int i=0 ; i< messageList.size() ; i++){
                        if(messageList.get(i).getMessageStatut()==msgStatutSend ) {
                            messageList.get(i).setMessageStatut(msgStatutReceived);
                            messageList.get(i).setImageState(R.drawable.msg_status_server_receive);
                        }
                    }
                    updateAdapter();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(user.equals("one"))state=true;
        if(user.equals("two"))state=false;
        Calendar instance = Calendar.getInstance();
        int currentHour = instance.get(Calendar.HOUR_OF_DAY);
        int minute = instance.get(Calendar.MINUTE);
        String time = getString(R.string.at)+ String.valueOf(currentHour) + ":" + String.valueOf(minute);

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            mImageView = (Bitmap) extras.get("data");
            messageList.add(new MessageClass(mImageView,time,R.drawable.msg_status_gray_waiting,state,msgStatutSend));
            updateAdapter();
        }

        if (requestCode == VIDEO_CAPTURE && resultCode == RESULT_OK) {

            mVideoURI = data.getData();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(mVideoPath);
            Bitmap image=mmr.getFrameAtTime();
            if(image!=null) {
                messageList.add(new MessageClass(mVideoURI, time, R.drawable.msg_status_gray_waiting, state, msgStatutSend, image));
                updateAdapter();
            }


        }

    }

    public void updateAdapter() {
        adapter = new ChatAdapter(Main2Activity.this,messageList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(messageList.get(i).getState() == HAS_IMG) {
                        Intent intent = new Intent(Main2Activity.this, PictureShow.class);
                        intent.putExtra("PhotoBitmap", messageList.get(i).getPhoto());
                        context.startActivity(intent);
                    }else if(messageList.get(i).getState() == HAS_VID) {
                        Intent intent = new Intent(Main2Activity.this, VideoShow.class);
                        intent.putExtra("videoUri", messageList.get(i).getVideo());
                        context.startActivity(intent);
                    }


            }
        });
    }


}


