package com.qingyii.hxtz.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.view.dialog.AbProgressDialogFragment;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxtz.Constant;
import com.qingyii.hxtz.LoginActivity;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.bean.ContactOrg;
import com.qingyii.hxtz.circle.CirclePersonalActivity;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.User;
import com.qingyii.hxtz.util.ImageUtil;
import com.qingyii.hxtz.view.RoundedImageView;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import nl.siegmann.epublib.util.StringUtil;

//import com.ab.fragment.AbDialogFragment.AbDialogOnLoadListener;
//import com.ab.fragment.AbLoadDialogFragment;
//import com.ab.util.AbDialogUtil;
//import com.qingyii.hxt.AbActivity;

/**
 * 通讯录同事信息页
 *
 * @author Lee
 *
 */
public class ContactDetailActivity extends AbBaseActivity {

	private ImageView mImgBg;
	private RoundedImageView mImgHead;
	private ImageView mImgLeft;
	private TextView mTextInfo;
	private TextView mTextCircle;
	private TextView mTextDepartment;
	private TextView mTextJob;
	private TextView mTextPhone;
	private TextView mTextMobile;
	private ImageView mImgSendMsg, mImgMobileCall, mImgPhoneCall;
	private ImageView mImgMsg, mImgPhoto, mImgMobile;

	private Handler mHandler;
	/**
	 * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
	 */
//    private AbLoadDialogFragment mDialogFragment = null;
	private AbProgressDialogFragment mDialogFragment = null;
	private User mUser;
	private ContactOrg mContactOrg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent() != null) {
			mContactOrg = (ContactOrg) getIntent().getSerializableExtra(
					"ContactOrg");
		}
		setContentView(R.layout.activity_contact_personal);
		findView();
		setListener();
		/**
		 * Load失效修改为Progress
		 *
		 * 监听失效
		 */
//		mDialogFragment = AbDialogUtil.showLoadDialog(
//				ContactDetailActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");
//		mDialogFragment.setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
//			@Override
//			public void onLoad() {
//				reqUserInfo();
//			}
//		});
		mDialogFragment = AbDialogUtil.showProgressDialog(
				ContactDetailActivity.this, R.mipmap.ic_load, "获取数据中,请等一小会");

		mHandler = new Handler(new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				if (mDialogFragment != null) {
					mDialogFragment.dismiss();
				}
				switch (msg.what) {
					case Constant.MSG_ERROR:
						Toast.makeText(ContactDetailActivity.this, "获取数据异常", Toast.LENGTH_SHORT)
								.show();
						break;
					case Constant.MSG_SUC:
						mUser = (User) msg.obj;
						initUserData(mUser);
						break;
					case Constant.MSG_NODATA:
						Toast.makeText(ContactDetailActivity.this, "已是最新数据", Toast.LENGTH_SHORT)
								.show();
						break;
				}
				return false;
			}
		});
	}

	private void findView() {
		mImgBg = (ImageView) findViewById(R.id.contact_personal_bg);
		mImgHead = (RoundedImageView) findViewById(R.id.contact_personal_photo);
		mTextInfo = (TextView) findViewById(R.id.contact_personal_info);
		mTextCircle = (TextView) findViewById(R.id.contact_personal_circle);
		mTextDepartment = (TextView) findViewById(R.id.contact_personal_department);
		mTextJob = (TextView) findViewById(R.id.contact_personal_job);
		mTextMobile = (TextView) findViewById(R.id.contact_personal_mobile);
		mTextPhone = (TextView) findViewById(R.id.contact_personal_phone);
		mImgSendMsg = (ImageView) findViewById(R.id.contact_personal_sendmsg);
		mImgMobileCall = (ImageView) findViewById(R.id.contact_personal_mobilecall);
		mImgPhoneCall = (ImageView) findViewById(R.id.contact_personal_phonecall);
		mImgLeft = (ImageView) findViewById(R.id.contact_personal_left);
	}

	private void setListener() {
		mTextCircle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mUser == null) {
					return;
				}
				Intent intent = new Intent(ContactDetailActivity.this, CirclePersonalActivity.class);
				intent.putExtra("User", mUser);
				startActivity(intent);
			}
		});
		mImgSendMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mTextMobile.getText().toString()));
				startActivity(intent);
			}
		});
		mImgMobileCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTextMobile.getText().toString())));
			}
		});
		mImgPhoneCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (ActivityCompat.checkSelfPermission(ContactDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTextPhone.getText().toString())));
			}
		});
		mImgLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initUserData(User createUser) {
		if(mUser==null) {
			return;
		}
		ImageLoader.getInstance().displayImage(
				HttpUrlConfig.photoDir + createUser.getPicaddress(), mImgHead,ImageUtil.newDisplayOptions(R.mipmap.ic_head));
		mTextInfo.setText(createUser.getUsername()==null?"":("" + createUser.getUsername()));
		mTextDepartment.setText(createUser.getDepname()==null?"":("" + createUser.getDepname()));
		mTextJob.setText(createUser.getJobname()==null?"":("" + createUser.getJobname()));
		if(StringUtil.isEmpty(createUser.getPhone())) {
			mImgMobileCall.setVisibility(View.INVISIBLE);
			mImgSendMsg.setVisibility(View.INVISIBLE);
			mTextMobile.setText("");
		}else {
			mImgMobileCall.setVisibility(View.VISIBLE);
			mImgSendMsg.setVisibility(View.VISIBLE);
			mTextMobile.setText("" + createUser.getPhone());
		}
		mImgPhoneCall.setVisibility(View.INVISIBLE);
	}

	public void reqUserInfo() {
		JSONObject jsonobject = new JSONObject();
		try {
			jsonobject.put("userid", mContactOrg.getCurid());
			byte[] bytes = jsonobject.toString().getBytes("utf-8");
			ByteArrayEntity entity = new ByteArrayEntity(bytes);
			YzyHttpClient.post(this, HttpUrlConfig.reqUserInfo, entity,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, String content) {
							if (statusCode == 499) {
								Toast.makeText(ContactDetailActivity.this,
										CacheUtil.logout, Toast.LENGTH_SHORT)
										.show();
								Intent intent = new Intent(
										ContactDetailActivity.this,
										LoginActivity.class);
								startActivity(intent);
								onFinish();
							} else if (statusCode == 200) {
								Gson gson = new Gson();
								try {
									JSONObject js = new JSONObject(content);
									User user = gson.fromJson(js.toString(),
											User.class);
									Message msg = Message.obtain();
									msg.what = Constant.MSG_SUC;
									msg.arg1 = 0;
									msg.obj = user;
									mHandler.sendMessage(msg);

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
