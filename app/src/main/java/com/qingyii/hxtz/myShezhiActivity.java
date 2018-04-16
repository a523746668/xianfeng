package com.qingyii.hxtz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxtz.database.BookDB;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.httpway.HandleParameterCallback;
import com.qingyii.hxtz.pojo.HandleParameter;
import com.qingyii.hxtz.util.GetFileSizeUtil;
import com.qingyii.hxtz.util.UpdateUtil;
import com.qingyii.hxtz.util.ZipUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;

/**
 * 设置界面
 *
 * @author Administrator
 */
public class myShezhiActivity extends BaseActivity {
    private TextView tv_checkUpdate, tv_about,
            tv_outLogin;
    private LinearLayout returns_arrow;
    private RelativeLayout setting_cache_rl, tv_fankui, rl_change_password;
    private TextView setting_cache_size;
    private String cache_size_Str = "";

    private TextView tv_share;
    //private FrontiaSocialShare mSocialShare;
    private Bitmap bm;
    //百度分享
//	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
    // 分享弹出框
    private PopupWindow popupWindow;
    private View parent;
    private View contentView;
    private Button fronita_share_cancel;
    private LinearLayout fronita_sina_weibo, fronita_qq_weibo, fronita_qzone, fronita_renren, fronita_baidu, fronita_save;
    private LinearLayout fronita_qq, fronita_weixin, fronita_sms;

    private TextView mTextAboutVersion;

    MyApplication myApplication = new MyApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysheshi);
        //tltle设置
        TextView tltle = (TextView) findViewById(R.id.activity_tltle_name);
        tltle.setText("设置");
        returns_arrow = (LinearLayout) findViewById(R.id.returns_arrow);
        returns_arrow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });

        myApplication.addActivity(this);

        initData();
        initUI();
    }

    private void initData() {
        // TODO Auto-generated method stub
        cache_size_Str = getCacheSize();
    }

    private String getVersionName() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "2.98";
        }
    }

    private void initUI() {
        initShareUI();
        mTextAboutVersion = (TextView) findViewById(R.id.tv_about_version);
        mTextAboutVersion.setText(getVersionName());
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                openPopWindow(parent);
            }
        });
        tv_outLogin = (TextView) findViewById(R.id.tv_outLogin);
        tv_outLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                logoutTip();
            }
        });
        rl_change_password = (RelativeLayout) findViewById(R.id.rl_change_password);
        setting_cache_rl = (RelativeLayout) findViewById(R.id.setting_cache_rl);
        setting_cache_rl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cleanCacheData();
            }
        });
        setting_cache_size = (TextView) findViewById(R.id.setting_cache_size);
        setting_cache_size.setText("当前共有缓存" + cache_size_Str);
        tv_checkUpdate = (TextView) findViewById(R.id.tv_checkUpdate);
        tv_checkUpdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                UpdateUtil util=new UpdateUtil();
                util.Update(myShezhiActivity.this);
            }
        });
        tv_fankui = (RelativeLayout) findViewById(R.id.tv_fankui);
        tv_fankui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(myShezhiActivity.this, UseInfoBackActivity.class);
                startActivity(intent);
            }
        });
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_about.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(myShezhiActivity.this,
                        AboutusActivity.class);
                startActivity(intent);
            }
        });

        rl_change_password.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(myShezhiActivity.this, AlterPwdActivity.class);
                it.putExtra("tltle", "修改密码");
                startActivity(it);
            }
        });
    }

    /**
     * 获取sd卡缓存目录文件大小
     *
     * @return
     */
    private String getCacheSize() {
        String sizeStr = "0Mb";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = new File(HttpUrlConfig.cacheDir);
            try {
                GetFileSizeUtil gu = GetFileSizeUtil.getInstance();
                sizeStr = gu.FormetFileSize(gu.getFileSize(file));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(myShezhiActivity.this, "手机SD卡未加载！",
                    Toast.LENGTH_SHORT).show();
        }
        return sizeStr;
    }

    /**
     * 清除缓存提示
     */
    private void cleanCacheData() {
        new AlertDialog.Builder(myShezhiActivity.this)
                .setTitle("提示")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 1、清除图片文件缓存根目录图片文件
                        ImageLoader.getInstance().clearDiscCache();
                        setting_cache_size.setText("当前共有缓存0M");
                        // 2、删除缓存目录下所有文件夹
                        ZipUtil.delFolder(HttpUrlConfig.cacheDir);
                        // 3、 清空sqlite缓存书籍相关表
                        if (BookDB.chearBook()) {
                            BookDB.VACUUMBook();
                        }
                        Toast.makeText(myShezhiActivity.this, "已清除所有已过期的缓存",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("确定要清除所有缓存,包括电子书籍缓存信息?").show();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        myApplication.finishActivity(myShezhiActivity.this);
    }

    private void logoutTip() {
        new AlertDialog.Builder(myShezhiActivity.this)
                .setTitle("提示")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  loginOut();
                        //onBackPressed();
                        // 1.清除用户个内存缓存信息
                        CacheUtil.userid = 0;
                        CacheUtil.userName = "";
                        CacheUtil.user = null;
                        //清空 推送ID 请求
                        //2、清除SharePreferences保存自动登录时用到的账号和密码，保留推送ID
                        //跳转入登陆界面
                        userDevice();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setMessage("确定要退出?").show();
    }

    private void loginOut() {
        try {
            JSONObject jsonObject = new JSONObject();
            byte[] bytes;
            ByteArrayEntity entity = null;
            //phone
            jsonObject.put("userid", CacheUtil.userid);
//			jsonObject.put("userFlag", 1);
            bytes = jsonObject.toString().getBytes("utf-8");
            entity = new ByteArrayEntity(bytes);
            YzyHttpClient.post(myShezhiActivity.this,
                    HttpUrlConfig.loginOut, entity,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String content) {
                            if (statusCode == 200) {
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Throwable error,
                                              String content) {
                        }

                        @Override
                        public void onFinish() {
                        }

                    });
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


    private void initShareUI() {

        // 百度分享
//		mSocialShare = Frontia.getSocialShare();
//		mSocialShare.setContext(this);
//		mSocialShare.setClientId(MediaType.SINAWEIBO.toString(), HttpUrlConfig.sinaAppKey);
//		mSocialShare.setClientId(MediaType.QZONE.toString(), "100358052");
//		mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "100358052");
//		mSocialShare.setClientName(MediaType.BAIDU.toString(), "百度");
//		mSocialShare.setClientId(MediaType.WEIXIN.toString(), HttpUrlConfig.weixinAppKey);
//		mImageContent.setTitle("红信通分享");
//		mImageContent.setContent("红信通分享");
//		mImageContent.setLinkUrl("http://www.baidu.com");
        // mImageContent.setImageUri(Uri.parse("http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"));
        // mImageContent.setImageUri(Uri.parse(HttpUrlConfig.BASE_URL+"images/icon1.png"));
        // bm =
        // ImageLoader.getInstance().loadImageSync(HttpUrlConfig.BASE_URL+"images/icon1.png");
        bm = ImageLoader.getInstance().loadImageSync(
                "drawable://" + R.mipmap.ic_launcher);
//		mImageContent.setImageData(bm);
        /** PopupWindow的界面 */
        contentView = getLayoutInflater().inflate(R.layout.baidu_share, null);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // popupWindow.setFocusable(true);// 取得焦点
        /** 设置PopupWindow弹出和退出时候的动画效果 */
        popupWindow.setAnimationStyle(R.style.animation);
        parent = contentView.findViewById(R.id.pop_baidu_share_layout);
        fronita_share_cancel = (Button) contentView.findViewById(R.id.fronita_share_cancel);
        fronita_share_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();// 关闭
                } else {
                    openPopWindow(parent);
                }
            }
        });

//		fronita_sina_weibo = (LinearLayout) contentView
//				.findViewById(R.id.fronita_sina_weibo);
//		fronita_sina_weibo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(NotifyListView v) {
//				// TODO Auto-generated method stub
//				sinaWeiboShare();
//			}
//		});
//		fronita_qq_weibo = (LinearLayout) contentView
//				.findViewById(R.id.fronita_qq_weibo);
//		fronita_qq_weibo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(NotifyListView v) {
//				// TODO Auto-generated method stub
//				qqWeiboShare();
//			}
//		});
//
//		fronita_renren = (LinearLayout) contentView
//				.findViewById(R.id.fronita_renren);
//		fronita_renren.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(NotifyListView v) {
//				// TODO Auto-generated method stub
//				renrenShare();
//			}
//		});
        /*
         * fronita_qzone=(LinearLayout)
		 * contentView.findViewById(R.id.fronita_qzone);
		 * fronita_qzone.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(NotifyListView v) { // TODO Auto-generated method
		 * stub qzoneShare(); } });
		 */
        /*
         * fronita_baidu=(LinearLayout)
		 * contentView.findViewById(R.id.fronita_baidu);
		 * fronita_baidu.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(NotifyListView v) { // TODO Auto-generated method
		 * stub baiduShare(); } });
		 */
        /*
         * fronita_qq=(LinearLayout) contentView.findViewById(R.id.fronita_qq);
		 * fronita_qq.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(NotifyListView arg0) { // TODO Auto-generated
		 * method stub qqShare(); } });
		 */
//		fronita_weixin = (LinearLayout) contentView
//				.findViewById(R.id.fronita_weixin);
//		fronita_weixin.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(NotifyListView v) {
//				// TODO Auto-generated method stub
//				weixinShare();
//			}
//		});
//		fronita_sms = (LinearLayout) contentView.findViewById(R.id.fronita_sms);
//		fronita_sms.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(NotifyListView v) {
//				// TODO Auto-generated method stub
//				smsShare();
//			}
//		});

    }

    //
//	/**
//	 * 新浪微博分享
//	 */
//	private void sinaWeiboShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.SINAWEIBO.toString(),
//				new ShareListener(), true);
//	}
//
//	private void qqWeiboShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.QQWEIBO.toString(),
//				new ShareListener(), true);
//	}
//
//	private void qzoneShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.QZONE.toString(),
//				new ShareListener(), true);
//	}
//
//	private void qqShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.QQFRIEND.toString(),
//				new ShareListener(), true);
//	}
//
//	/**
//	 * 微信好友分享
//	 */
//	private void weixinShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.WEIXIN_FRIEND.toString(),
//				new ShareListener(), true);
//	}
//
//	/**
//	 * 微信朋友圈分享
//	 */
//	private void smsShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.WEIXIN_TIMELINE.toString(),
//				new ShareListener(), true);
//		mImageContent.setTitle("豫头条分享");
//	}
//
//	private void renrenShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.RENREN.toString(),
//				new ShareListener(), true);
//	}
//
//	private void baiduShare() {
//		// TODO Auto-generated method stub
//		mSocialShare.share(mImageContent, MediaType.BAIDU.toString(),
//				new ShareListener(), true);
//	}
//	private class ShareListener implements FrontiaSocialShareListener {
//
//		@Override
//		public void onSuccess() {
//			Log.d("Test","share success");
//			if(popupWindow.isShowing()){
//				popupWindow.dismiss();
//			}
//			Toast.makeText(myShezhiActivity.this, "恭喜你分享成功！", Toast.LENGTH_SHORT).show();
//		}
//
//		@Override
//		public void onFailure(int errCode, String errMsg) {
//			Log.d("Test","share errCode "+errCode);
//			if(popupWindow.isShowing()){
//				popupWindow.dismiss();
//			}
//			Toast.makeText(myShezhiActivity.this, "分享失败，请重新分享！", Toast.LENGTH_SHORT).show();
//		}
//
//		@Override
//		public void onCancel() {
//			Log.d("Test","cancel ");
//			if(popupWindow.isShowing()){
//				popupWindow.dismiss();
//			}
//			Toast.makeText(myShezhiActivity.this, "分享取消！", Toast.LENGTH_SHORT).show();
//		}
//
//	}
    public void openPopWindow(View v) {
        /** 设置PopupWindow弹出后的位置 */
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public void userDevice() {

        Log.e("DeviceID", MyApplication.hxt_setting_config.getString("DeviceID", ""));

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getUserUpdateUrl())
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("device_id", "")
                .build()
                .execute(new HandleParameterCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("DeviceCallback_onError", e.toString());
//                                 Toast.makeText(myShezhiActivity.this, "网络异常--请检查网络", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(HandleParameter response, int id) {
                                 Log.e("DeviceCallback", response.getError_msg());
                                 switch (response.getError_code()) {
                                     case 0:
                                         String deviceID = MyApplication.hxt_setting_config.getString("DeviceID", "");
                                         MyApplication.hxt_setting_config.edit().clear().commit();
                                         MyApplication.hxt_setting_config.edit().putString("DeviceID", deviceID).commit();
                                         MyApplication.hxt_setting_config.edit().putInt("guideState", 1).commit();
//                                         MyApplication.hxt_setting_config.edit().remove("phone").commit();
//                                         MyApplication.hxt_setting_config.edit().remove("pwd").commit();
//                                         MyApplication.hxt_setting_config.edit().remove("credentials").commit();
                                         Toast.makeText(myShezhiActivity.this, "退出成功！", Toast.LENGTH_SHORT).show();

                                         //MyApplication.getInstance().AppExit();
                                         Intent intent = new Intent(myShezhiActivity.this, LoginActivity.class);
                                         intent.putExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, true);
                                         startActivity(intent);
                                         Message msg = Message.obtain();
                                         msg.what = AppManager.KILL_ALL;
                                         EventBus.getDefault().post(msg,AppManager.APPMANAGER_MESSAGE);
                                         myApplication.finishActivity(myShezhiActivity.this);
                                         break;
                                     default:
                                         Toast.makeText(myShezhiActivity.this, response.getError_msg(), Toast.LENGTH_SHORT).show();
                                         break;
                                 }
                             }
                         }
                );
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
