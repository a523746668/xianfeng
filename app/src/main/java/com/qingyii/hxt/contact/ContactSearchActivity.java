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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
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

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.fragment.AbLoadDialogFragment;
//import com.ab.util.AbDialogUtil;
//import com.ab.view.pullview.AbPullToRefreshView;
//import com.qingyii.hxt.AbActivity;

/**
 * 联系人搜索界面
 * 
 * @author Lee
 *
 */
public class ContactSearchActivity extends AbBaseActivity {
	//private AbPullToRefreshView mRefreshOrg;
	private EditText mEditSearch;
	private Button mBtnSearch;
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
		setContentView(R.layout.activity_contact_search);
		findView();
		initData();
		setListener();
		mHandler = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				if (mDialogFragment != null) {
					mDialogFragment.dismiss();
				}
				switch (msg.what) {
				case Constant.MSG_ERROR:
					Toast.makeText(ContactSearchActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
							.show();
					break;
				case Constant.MSG_SUC:
					if (msg.arg1 == 0) {
						mOrgAdapter.notifyDataSetChanged();
					}
					break;
				case Constant.MSG_NODATA:
					Toast.makeText(ContactSearchActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				return false;
			}
		});
	}

	private void findView() {

		//mRefreshOrg = (AbPullToRefreshView) findViewById(R.id.contact_orgdetail_refreshorg);
		mListOrg = (ListView) findViewById(R.id.contact_search_listorg);
		mOrgAdapter = new ContactOrgAdapter(ContactSearchActivity.this,
				mDatasOrg);
		mListOrg.setAdapter(mOrgAdapter);

		mImgLeft = (ImageView) findViewById(R.id.contact_search_left);
		mImgRight = (ImageView) findViewById(R.id.contact_search_right);
		mEditSearch = (EditText) findViewById(R.id.contact_search_edit);
		mBtnSearch = (Button) findViewById(R.id.contact_search_search);
	}

	private void initData() {
	}

	private void setListener() {
		mBtnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/**
				 * Load失效修改为Progress
				 *
				 * 监听失效
				 */
//				mDialogFragment = AbDialogUtil.showLoadDialog(
//						ContactSearchActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");
//				mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//					@Override
//					public void onLoad() {
//						getDataSearch(mEditSearch.getText().toString());
//					}
//				});
				mDialogFragment = AbDialogUtil.showProgressDialog(
						ContactSearchActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");
			}
		});
		mListOrg.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ContactOrg mContactOrg = mDatasOrg.get(arg2);
				Intent intent = new Intent(ContactSearchActivity.this,ContactDetailActivity.class);
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
			}
		});
//		mRefreshOrg.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
//
//			@Override
//			public void onHeaderRefresh(AbPullToRefreshView view) {
//				mRefreshOrgType = Constant.TYPE_HEADER;
//				mPageOrg = 0;
//				getDataOrg(mPageOrg, mPagesize);
//			}
//		});
//		mRefreshOrg.setOnFooterLoadListener(new OnFooterLoadListener() {
//
//			@Override
//			public void onFooterLoad(AbPullToRefreshView view) {
//				mRefreshOrgType = Constant.TYPE_FOOTER;
//				getDataOrg(mPageOrg + 1, mPagesize);
//			}
//		});
	}

	public void getDataSearch(String keyword) {
		JSONObject jsonobject = new JSONObject();
		try {
			jsonobject.put("keyword", keyword);
			jsonobject.put("userid", CacheUtil.userid + "");
			byte[] bytes = jsonobject.toString().getBytes("utf-8");
			ByteArrayEntity entity = new ByteArrayEntity(bytes);
			YzyHttpClient.post(this, HttpUrlConfig.reqSearchUser, entity,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							if (statusCode == 499) {
								Toast.makeText(ContactSearchActivity.this,
										CacheUtil.logout, Toast.LENGTH_SHORT)
										.show();
								Intent intent = new Intent(
										ContactSearchActivity.this,
										LoginActivity.class);
								startActivity(intent);
								onFinish();
							} else if (statusCode == 200) {
								Gson gson = new Gson();
								try {
									JSONObject js = new JSONObject(content);
									String a = js.getString("rows");
									if (EmptyUtil.IsNotEmpty(js
											.getString("rows"))) {
										JSONArray lists = js
												.getJSONArray("rows");

										if (lists.length() <= 0) {
											mHandler.sendEmptyMessage(Constant.MSG_NODATA);
										} else {
//											if (mRefreshOrgType == TYPE_HEADER) {
//												// ContactMainActivity.this.mDatasOrg
//												// .clear();
//											} else {
//												mPageOrg++;
//											}
											for (int i = 0; i < lists.length(); i++) {
												ContactOrg info = gson.fromJson(
														lists.getString(i),
														ContactOrg.class);
												ContactSearchActivity.this.mDatasOrg.add(info);
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
