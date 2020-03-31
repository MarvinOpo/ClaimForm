package com.mvopo.claimform.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mvopo.claimform.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> photos = new ArrayList<>();
//
//    public ImageAdapter(Context c, ArrayList<String> photos) {
//        mContext = c;
//        this.photos = photos;
//
//    }
    public ImageAdapter(Context context){

        mContext = context;

        photos.add(R.drawable.result1);
        photos.add(R.drawable.result2);
        photos.add(R.drawable.result3);
        photos.add(R.drawable.result4);
    }

    public int getCount() {
        return photos.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(5, 5, 5, 0);
        imageView.setId(photos.get(position));

//        if (photos.get(position).equals("No Photo")) {
//            imageView.setImageResource(R.drawable.no_photo);
//            return imageView;
//        }

        Glide.with(mContext)
                .load(photos.get(position))
                .placeholder(R.drawable.no_photo)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        return imageView;
    }
}