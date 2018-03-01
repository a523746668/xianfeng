package com.qingyii.hxt.contact;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxt.Constant;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.ContactOrgAdapter;
import com.qingyii.hxt.bean.ContactOrg;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.ab.fragment.AbLoadDialogFragment;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
//import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
//import com.qingyii.hxt.AbActivity;

/**
 * 通讯录组织详情页
 * 
 * @author Lee
 *
 */
public class ContactOrgDetailActivity extends AbBaseActivity {

	private AbPullToRefreshView mRefreshOrg;
	private ListView mListOrg;
	private ArrayList<ContactOrg> mDatasOrg = new ArrayList<ContactOrg>();
	private ContactOrgAdapter mOrgAdapter;

	private ImageView mImgLeft;
	private ImageView mImgRight;

	private int mRefreshOrgType;

	private int mPageOrg = 1;
	private int mPagesize = 10;

	/**
	 * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
	 */
//    private AbLoadDialogFragment mDialogFragment = null;
	private AbProgressDialogFragment mDialogFragment = null;
	private Handler mHandler;
	
	private ContactOrg mDefaultOrg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent()!=null) {
			mDefaultOrg = (ContactOrg) getIntent().getSerializableExtra("ContactOrg");
		}
		setContentView(R.layout.activity_contact_orgdetail);
		findView();
		initData();
		setListener();
		mRefreshOrg.headerRefreshing();
		mHandler = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				if (mDialogFragment != null) {
					mDialogFragment.dismiss();
				}
				switch (msg.what) {
				case Constant.MSG_ERROR:
					Toast.makeText(ContactOrgDetailActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
							.show();
					break;
				case Constant.MSG_SUC:
					if (msg.arg1 == 0) {
						mOrgAdapter.notifyDataSetChanged();
					} else if (msg.arg1 == 1) {
						// mDepartmentAdapter.notifyDataSetChanged();
					}
					break;
				case Constant.MSG_NODATA:
					Toast.makeText(ContactOrgDetailActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				if (msg.arg1 == 0) {
					mRefreshOrg.onHeaderRefreshFinish();
					mRefreshOrg.onFooterLoadFinish();
				} else if (msg.arg1 == 1) {
					// mRefreshDepartment.onHeaderRefreshFinish();
					// mRefreshDepartment.onFooterLoadFinish();
				}
				return false;
			}
		});
	}

	private void findView() {

		mRefreshOrg = (AbPullToRefreshView) findViewById(R.id.contact_orgdetail_refreshorg);
		mListOrg = (ListView) findViewById(R.id.contact_orgdetail_listorg);
		mOrgAdapter = new ContactOrgAdapter(ContactOrgDetailActivity.this, mDatasOrg);
		mListOrg.setAdapter(mOrgAdapter);

		mImgLeft = (ImageView) findViewById(R.id.contact_orgdetail_left);
		mImgRight = (ImageView) findViewById(R.id.contact_orgdetail_right);
	}

	private void initData() {
	}

	private void setListener() {
		mListOrg.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ContactOrg mContactOrg = mDatasOrg.get(arg2);
				Intent intent = new Intent(ContactOrgDetailActivity.this,ContactDetailActivity.class);
				intent.putExtra("ContactOrg", mContactOrg);
				startActivity(intent);
			}
		});
		mImgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mImgRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ContactOrgDetailActivity.this,ContactSearchActivity.class);
				startActivity(intent);
			}
		});
		mRefreshOrg.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				mRefreshOrgType = Constant.TYPE_HEADER;
				mPageOrg = 1;
				getDataOrg(mPageOrg, mPagesize);
			}
		});
		mRefreshOrg.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				mRefreshOrgType = Constant.TYPE_FOOTER;
				getDataOrg(mPageOrg, mPagesize);
			}
		});
	}

	public void getDataOrg(int mypage, int mypagesize) {
		JSONObject jsonobject = new JSONObject();
		try {
			jsonobject.put("page", mPageOrg);
			jsonobject.put("pagesize", mPagesize);
			jsonobject.put("previd", mDefaultOrg.getCurid());
			jsonobject.put("prevclass", mDefaultOrg.getCurclass());
			jsonobject.put("userid", CacheUtil.userid + "");
			byte[] bytes = jsonobject.toString().getBytes("utf-8");
			ByteArrayEntity entity = new ByteArrayEntity(bytes);
			YzyHttpClient.post(this, HttpUrlConfig.reqAddressBook, entity,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							if (statusCode == 499) {
								Toast.makeText(ContactOrgDetailActivity.this,
										CacheUtil.logout, Toast.LENGTH_SHORT)
										.show();
								Intent intent = new Intent(
										ContactOrgDetailActivity.this,
										LoginActivity.class);
								startActivity(intent);
								onFinish();
							} else if (statusCode == 200) {
								Gson gson = new Gson();
								try {
									JSONObject js = new JSONObject(content);
									if (EmptyUtil.IsNotEmpty(js
											.getString("list"))) {
										JSONArray lists = js
												.getJSONArray("list");

										if (lists.length() <= 0) {
											mHandler.sendEmptyMessage(Constant.MSG_NODATA);
										} else {
											if (mRefreshOrgType == Constant.TYPE_HEADER) {
												ContactOrgDetailActivity.this.mDatasOrg
														.clear();
											} else {
												
											}
											mPageOrg++;
											for (int i = 0; i < lists.length(); i++) {
												ContactOrg info = gson.fromJson(
														lists.getString(i),
														ContactOrg.class);
												ContactOrgDetailActivity.this.mDatasOrg
														.add(info);
											}
											Message msg = Message.obtain();
											msg.what = Constant.MSG_SUC;
											msg.arg1 = 0;
											mHandler.sendMessage(msg);
										}

									} else {
										mHandler.sendEmptyMessage(Constant.MSG_NODATA);
									}

								} catch (JSONException e) {
									mHandler.sendEmptyMessage(Constant.MSG_ERROR);
									e.printStackTrace();
								}
							}
							super.onSuccess(statusCode, content);
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							System.out.println(content);
						}

						@Override
						public void onFinish() {
							super.onFinish();
							// System.out.println("finish");
						}

					});
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
