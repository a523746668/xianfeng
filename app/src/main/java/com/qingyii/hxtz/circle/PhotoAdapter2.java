package com.qingyii.hxtz.circle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;

import java.util.ArrayList;

//自定义适配器
public class PhotoAdapter2 extends BaseAdapter {
    private ArrayList<String> mImgs;
    private AppCompatActivity abBaseActivity;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;

    public PhotoAdapter2(AppCompatActivity abBaseActivity, ArrayList<String> imgs) {
        this.mImgs = imgs;
        this.abBaseActivity = abBaseActivity;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
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
        LinearLayout ll = new LinearLayout(abBaseActivity);
        ll.setPadding(0,0,0,40);
        ResizableImageView imageView = new ResizableImageView(this.abBaseActivity);
        ImageLoader.getInstance().displayImage(mImgs.get(position), imageView, MyApplication.options, animateFirstListener);
        imageView.setOnClickListener(v -> {
            BigPhotoFragment frag = BigPhotoFragment.getInstance(mImgs, position);
            frag.show(abBaseActivity.getSupportFragmentManager(), "BigPhotoFragment");
        });
        ll.addView(imageView);
        return ll;
    }
}
