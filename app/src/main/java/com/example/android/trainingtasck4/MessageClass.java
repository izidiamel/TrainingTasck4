package com.example.android.trainingtasck4;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

/**
 * Created by Home on 19/03/2018.
 */

public class MessageClass {
    private String mMessage;
    private String mDate;
    private int mImageState;
    private Bitmap mImage;
    private int mImageVideo;
    private int mMsgStatut;
    private boolean mOutcom;
    private Uri mVideoUri;
    private final static int EMPTY=-1;
    private final static int HAS_IMG= -2;
    private final static int HAS_VID=-3;

    public MessageClass(String msg, String dte, int imageid,boolean state,int msgstate){
        mMessage=msg;
        mDate=dte;
        mImageState=imageid;
        mOutcom=state;
        mMsgStatut=msgstate;
        mImageVideo=EMPTY;
    }

    public MessageClass(Uri videoURI, String dte, int imageid,boolean state,int msgstate,Bitmap frame){
        mVideoUri=videoURI;
        mDate=dte;
        mImageState=imageid;
        mOutcom=state;
        mImageVideo=HAS_VID;
        mMsgStatut=msgstate;
        mImage=frame;

    }
    public MessageClass(Bitmap imageId,String dte,int imageid,boolean state,int msgstate){

        mImage =imageId;
        mDate=dte;
        mImageState=imageid;
        mOutcom=state;
        mMsgStatut=msgstate;
        mImageVideo=HAS_IMG;
    }




    public String getMessage(){
        return mMessage;
    }


    public String getDate()    {
        return mDate;
    }


    public int getImageState(){
        return mImageState;
    }

    public void setImageState(int imageid){
        mImageState=imageid;
    }

    public int getState(){
        return mImageVideo;
    }


    public void setMessageStatut(int msgstatut){
        mMsgStatut=msgstatut;
    }
    public int getMessageStatut(){
        return mMsgStatut;
    }

    public boolean getOutComState(){
        return mOutcom;
    }

    public Bitmap getPhoto(){
        return  mImage;
    }

    public Uri getVideo(){
        return  mVideoUri;
    }

    public boolean isOuntCom()
    {
        return mOutcom;
    }

}
