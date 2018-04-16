package com.qingyii.hxtz;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.qingyii.hxtz.adapter.CommentListAdapter;
import com.qingyii.hxtz.bean.CommentInfo;
import com.qingyii.hxtz.bean.huodongInfo;

import java.util.ArrayList;

//import com.ab.view.pullview.AbPullToRefreshView;

public class SuggestionCollectionActivity extends AbBaseActivity {
	private TextView tv_content, tv_time,title_suggestion;
	private Button tv_enter;
	private ImageView back_btn;
	private huodongInfo huodong;
	private ListView lv_comment;
	private EditText et_reply;
	private CommentListAdapter mAdapter;
	private ArrayList<CommentInfo> list=new ArrayList<CommentInfo>();
	private String replyTxt;
	private Handler handler;
	private AbPullToRefreshView mAbPullToRefreshView;
	  private huodongInfo huodonginfo;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);
		huodonginfo=(huodongInfo) getIntent().getSerializableExtra("huodongInfo");
		handler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message arg0) {
				switch (arg0.what) {
				case 0:
					
					break;

				default:
					break;
				}
				return true;
			}
		});
		initUI();
//		refreshTask();
	}

	/**
	 * 获取评论数据
	 */

	private void initUI() {
		title_suggestion=(TextView) findViewById(R.id.title_suggestion);
		tv_content = (TextView) findViewById(R.id.tv_content_suggestion);
		tv_time = (TextView) findViewById(R.id.tv_end_time_suggestion);
		back_btn = (ImageView) findViewById(R.id.iv_back_suggestion);
		tv_enter = (Button) findViewById(R.id.enter_suggestion);
		et_reply = (EditText) findViewById(R.id.et_suggestion);
		lv_comment = (ListView) findViewById(R.id.lv_comment_suggestion);
		title_suggestion.setText(huodonginfo.getTitle());
	    tv_time.setText(huodonginfo.getEndTime());
	    CommentInfo info1=new CommentInfo();
	    info1.setYijian_ImageUrl("drawable://"+ R.mipmap.haimianbb);
	    info1.setTime("七天前");
	    info1.setYijian_name("Eason");
	    info1.setCommentContent("这个主意不错");
	    CommentInfo info2=new CommentInfo();
	    info2.setYijian_ImageUrl("drawable://"+ R.mipmap.dashitouxiang);
	    info2.setTime("一小时前");
	    info2.setYijian_name("Jarvan");
	    info2.setCommentContent("今天星期五");
	    CommentInfo info3=new CommentInfo();
	    info3.setYijian_ImageUrl("drawable://"+ R.mipmap.dashitouxiang);
	    info3.setTime("半小时前");
	    info3.setYijian_name("Jarvan");
	    info3.setCommentContent("今天星期6");
	    list.add(info1);
	    list.add(info2);
	    list.add(info3);
		mAdapter = new CommentListAdapter(this, list);
		lv_comment.setAdapter(mAdapter);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		tv_enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				replyTxt = et_reply.getText().toString();
				if(replyTxt.equals("")){
					Toast.makeText(SuggestionCollectionActivity.this, "评论不能为空！", Toast.LENGTH_SHORT).show();
				} else {
					//提交评论
					sendComment();
					//提交完刷新列表
					handler.sendEmptyMessage(1);
				}
			}
		});

	}
	


	/**
	 * 提交评论
	 */
	protected void sendComment() {
 
		
	}

}
