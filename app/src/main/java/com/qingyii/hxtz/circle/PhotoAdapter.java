package com.qingyii.hxtz.circle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

//自定义适配器
public class PhotoAdapter extends BaseAdapter {
    private ArrayList<String> mImgs;
    private AppCompatActivity abBaseActivity;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public PhotoAdapter(AppCompatActivity abBaseActivity, ArrayList<String> imgs) {
        this.mImgs = imgs;
        this.abBaseActivity = abBaseActivity;
    }

    public int getCount() {
        return mImgs.size();
    }

    public Object getItem(int item) {
        return item;
    }

    public long getItemId(int id) {
        return id;
    }

    // 创建View方法
    public View getView(final int position, View convertView, ViewGroup parent) {
        SquareImage imageView;
        if (convertView == null) {
            imageView = new SquareImage(this.abBaseActivity);
            imageView.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (SquareImage) convertView;
        }
//        imageView.setImageResource(R.drawable.image_loading);
//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + mImgs.get(position), imageView);
//        ImageLoader.getInstance().displayImage(mImgs.get(position), imageView);
        ImageLoader.getInstance().displayImage(mImgs.get(position), imageView, MyApplication.options, animateFirstListener);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigPhotoFragment frag = BigPhotoFragment.getInstance(mImgs, position);
                frag.show(abBaseActivity.getSupportFragmentManager(), "BigPhotoFragment");
            }
        });

        return imageView;
    }
}
