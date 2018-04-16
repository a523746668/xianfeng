package com.qingyii.hxtz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.andbase.library.app.base.AbBaseActivity;

/**
 * 播放视频
 * @author Lee
 *
 */
public class PlayVideoActivity extends AbBaseActivity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener{
	
	private VideoView mVideoView;
	private String mUrl;
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mUrl = intent.getStringExtra("url");
		setContentView(R.layout.activity_playvideo);
		findView();
		MediaController media = new MediaController(this);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnPreparedListener(this);
		media.setMediaPlayer(mVideoView);
		mVideoView.setMediaController(media);
		mProgressDialog = ProgressDialog.show(this, "", "正在加载中");
	}
	
	private void findView() {
		mVideoView = (VideoView) findViewById(R.id.playvideo_video);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mVideoView.setVideoURI(Uri.parse(mUrl));
		mVideoView.start();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mVideoView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mVideoView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mVideoView.setVisibility(View.VISIBLE);
			}
		}, 1000);
		mVideoView.resume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		Toast.makeText(this, "视频出错", Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		mProgressDialog.dismiss();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
	}
	
}
