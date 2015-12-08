package com.example.kiran.instagram;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by kiran on 12/6/15.
 */
public class PicturesAdapter  extends ArrayAdapter<Picture> {

    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(15)
            .oval(true)
            .build();

    private static class ViewHolder {
        TextView tvUserName;
        ImageView ivImage;
        TextView tvLikeCount;
        TextView tvCaption;
        ImageView ivProfilePic;
    }

    public PicturesAdapter(Context context, ArrayList<Picture> pics) {
        super(context, 0, pics);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Picture pic = getItem(position);
        Context context = getContext();
        ViewHolder viewHolder = new ViewHolder();
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false);
        }

        viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        viewHolder.tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
        viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        viewHolder.ivProfilePic = (ImageView)convertView.findViewById(R.id.ivProfilePic);

        convertView.setTag(viewHolder);

        viewHolder.tvUserName.setText(pic.userName);
        viewHolder.tvLikeCount.setText(pic.likeCount + " likes");
        viewHolder.tvCaption.setText(pic.caption);
        Log.d("User Profile pic", pic.userProfilePic);

        Picasso.with(context).load(pic.imageUrl).into(viewHolder.ivImage);

        //Picasso.with(context).load(pic.userProfilePic).into(viewHolder.ivProfilePic);

        Picasso.with(context)
                .load(pic.userProfilePic)
                .transform(transformation)
                .into(viewHolder.ivProfilePic);


        // Return the completed view to render on screen
        return convertView;
    }
}
