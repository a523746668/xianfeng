package com.qingyii.hxtz.zhf.zfragment;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.zhf.bean.SendTask;
import com.qingyii.hxtz.zhf.wight.CustomerVideoView;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhf on 2017/12/13.
 */

@SuppressLint("ValidFragment")
public class ImgandVideoFragment extends Fragment {
     private SendTask task;



    private PhotoView photoView;

    private CustomerVideoView videoView;

    public ImgandVideoFragment(SendTask task) {
        this.task = task;
    }

    public SendTask getTask() {
        return task;
    }

    public void setTask(SendTask task) {
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.imgandvideo,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoView= (PhotoView) view.findViewById(R.id.img);
        videoView= (CustomerVideoView) view.findViewById(R.id.video);
        if(task.getType().equals(SendTask.IMG)){
            photoView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            photoView.setImageBitmap(BitmapFactory.decodeFile(task.getUri()));
        } else {
            photoView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            videoView.setVideoPath(task.getUri());
            videoView.setMediaController(new MediaController(getActivity()));
            //videoView.start();
        }



    }
}
