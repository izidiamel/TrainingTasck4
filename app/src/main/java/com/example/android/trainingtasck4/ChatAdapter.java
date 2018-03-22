package com.example.android.trainingtasck4;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by Home on 19/03/2018.
 */

public class ChatAdapter extends ArrayAdapter<MessageClass> {

    private final static int EMPTY=-1;
    private final static int HAS_IMG= -2;
    private final static int HAS_VID=-3;


    public ChatAdapter(Context context, ArrayList<MessageClass> msgs) {
        super(context, 0, msgs);
         }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
           listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        MessageClass currentMsg = getItem(position);
        LinearLayout contentLayout = (LinearLayout)listItemView.findViewById(R.id.layout_base);
        TextView message=(TextView) listItemView.findViewById(R.id.message);
        TextView duration=(TextView)listItemView.findViewById(R.id.duration);
        ImageView status=(ImageView)listItemView.findViewById(R.id.status);
        ImageView  photo=(ImageView)listItemView.findViewById(R.id.photo);
        ImageView  photo2=(ImageView)listItemView.findViewById(R.id.photo2);
        FrameLayout frame=(FrameLayout)listItemView.findViewById(R.id.frame);

        duration.setText(currentMsg.getDate());
        if(!currentMsg.getOutComState()){
            status.setVisibility(View.GONE);
        }else {
            status.setVisibility(View.VISIBLE);
            status.setImageResource(currentMsg.getImageState());
        }

        if(currentMsg.getState()==EMPTY) {

            message.setText(currentMsg.getMessage());
            message.setVisibility(View.VISIBLE);
            photo.setVisibility(View.GONE);
            photo2.setVisibility(View.GONE);
            frame.setVisibility(View.GONE);
        }
        else if(currentMsg.getState()== HAS_IMG) {

              photo.setVisibility(View.VISIBLE);
              photo.setImageBitmap(currentMsg.getPhoto());
              message.setVisibility(View.GONE);
              photo2.setVisibility(View.GONE);
              frame.setVisibility(View.GONE);
        }else if(currentMsg.getState()== HAS_VID) {

                frame.setVisibility(View.VISIBLE);
                frame.setBackgroundDrawable(new BitmapDrawable(currentMsg.getPhoto()));
                photo2.setVisibility(View.VISIBLE);
                photo2.setImageResource(R.drawable.mviewer_play);
                photo.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
        }



        if(currentMsg.isOuntCom()){
            contentLayout.setBackgroundResource(R.drawable.balloon_outgoing_normal);
            ((RelativeLayout) listItemView).setGravity(Gravity.RIGHT);


        }else {
            contentLayout.setBackgroundResource(R.drawable.balloon_incoming_normal);
            ((RelativeLayout) listItemView).setGravity(Gravity.LEFT);

        }


        return listItemView;
    }

}
