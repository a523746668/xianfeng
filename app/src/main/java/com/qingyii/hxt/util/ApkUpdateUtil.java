package com.qingyii.hxt.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.qingyii.hxt.R;

import java.io.File;

/**
 * apk�����¹�����
 * 
 * @author shelia
 * 
 */
public class ApkUpdateUtil {
	private Context context;
	private DownloadManager dowanloadmanager = null;
	private DownloadChangeObserver downloadObserver;
	private long lastDownloadId = 0;
	private static final String downloadPath = "content://downloads/my_downloads";
	public static final Uri CONTENT_URI = Uri.parse(downloadPath);
	// ����APK����
	private String apkName = System.currentTimeMillis()+"";
	// ����APk����
	private String apkUpdateinfo = "�Ƿ�ȷ������?";
	// APK������·��
	private String apkUrl = "";
	// ��ȡ��ǰAPK�汾��
	private String apkVersion = "";
	private ProgressDialog pd=null;
	public ApkUpdateUtil(Context context) {
		this.context = context;
		this.apkVersion=getVersionName();
//		getNewVersion();
	}
	/**
	 * ��ȡ�°汾
	 */
	/*public void getNewVersion() {
		// pd=ProgressDialog.show(this.context, "", "��������...");
		// pd.setCancelable(true);
		RequestParams params=new RequestParams();
		params.put("name", "android_user_version");
		YzyHttpClient.get(context, HttpUrlConfig.cfg, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if(statusCode==200){
					JSONObject obj;
					try {
						obj = new JSONObject(content);
						if(obj.getInt("status")==1){
							String dataStr=obj.getString("data");
							if(EmptyUtil.IsNotEmpty(dataStr)){
								if(dataStr.contains("~")){
									String[] dataArray=dataStr.split("~");
									HttpUrlConfig.vsersionCode=dataArray[0];
									HttpUrlConfig.apkUrl=dataArray[1];
									String serverVersionCode=dataArray[0];
									apkUrl=dataArray[1];
								}
								//�жϰ汾��
								if(EmptyUtil.IsNotEmpty(HttpUrlConfig.vsersionCode)&&EmptyUtil.IsNotEmpty(apkVersion)){
									String[] s_v_c=HttpUrlConfig.vsersionCode.split("\\.");
									String[] c_v_c=apkVersion.split("\\.");
									if(s_v_c.length>=3&&c_v_c.length>=3){
										if(Integer.parseInt(s_v_c[0])>Integer.parseInt(c_v_c[0])){
											doDown();
										}else if(Integer.parseInt(s_v_c[0])==Integer.parseInt(c_v_c[0])){
											if(Integer.parseInt(s_v_c[1])>Integer.parseInt(c_v_c[1])){
												doDown();
											}else if(Integer.parseInt(s_v_c[1])==Integer.parseInt(c_v_c[1])){
												if(Integer.parseInt(s_v_c[2])>Integer.parseInt(c_v_c[2])){
													doDown();
												}else{
													Toast.makeText(context,"�������°汾��",Toast.LENGTH_SHORT).show();
												}
											}else{
												Toast.makeText(context,"�������°汾��",Toast.LENGTH_SHORT).show();
											}
										}
									}else{
										Toast.makeText(context,"APK�汾�Ÿ�ʽ�Ƿ���",Toast.LENGTH_SHORT).show();
									}
								}
								
							}
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			*//**
			 * ����
			 *//*
			private void doDown() {
				//�и��°汾��ʾ�Ƿ����ظ���
				if(NetworkUtils.isWifiConnected(context)){
					dowloadShow();
				}else{
					wifeShow();
				}
			}
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, error, content);
				if(pd!=null){
					pd.dismiss();
				}
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if(pd!=null){
					pd.dismiss();
				}
			}
		});
	}*/
	// ��ȡ��ǰӦ�õİ汾�ţ�
	private String getVersionName() {
		String version = "1.0.0";
		int versioncode = 1;
		// ��ȡpackagemanager��ʵ��
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()���㵱ǰ��İ���0����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;
			versioncode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return version;
	}

	// ������APK�Զ���װ
	private void installAPK(String fileName) {
		File file = new File(fileName);
		// System.out.println("��װAPk·����"+fileName);
		if (!file.exists()) {
			return;
		}
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		// intent.setDataAndType(Uri.parse(fileName),
		// "application/vnd.android.package-archive");
		context.startActivity(intent);
		// SubscriptionActivity.this.finish();
	}

	// �汾��������
	@SuppressLint("NewApi")
	public void downloadApk() {
		String serviceString = Context.DOWNLOAD_SERVICE;
		dowanloadmanager = (DownloadManager) context
				.getSystemService(context.DOWNLOAD_SERVICE);

		Uri uri = Uri.parse(apkUrl);
		Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS).mkdir();

		lastDownloadId = dowanloadmanager.enqueue(new DownloadManager.Request(
				uri)
				.setAllowedNetworkTypes(
						DownloadManager.Request.NETWORK_MOBILE
								| DownloadManager.Request.NETWORK_WIFI)
				.setAllowedOverRoaming(false)
				.setDestinationInExternalPublicDir(
						Environment.DIRECTORY_DOWNLOADS, apkName)
				.setTitle(apkName));
		context.registerReceiver(receiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		downloadObserver = new DownloadChangeObserver(null);
		context.getContentResolver().registerContentObserver(CONTENT_URI, true,
				downloadObserver);
	}

	class DownloadChangeObserver extends ContentObserver {

		public DownloadChangeObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onChange(boolean selfChange) {
			queryDownloadStatus();
		}

	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// �������ȡ�����ص�id������Ϳ���֪���ĸ��ļ���������ˡ�����������������ļ���
			Log.v("tag",
					""
							+ intent.getLongExtra(
									DownloadManager.EXTRA_DOWNLOAD_ID, 0));
			queryDownloadStatus();
		}
	};

	@SuppressLint("NewApi")
	private void queryDownloadStatus() {
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(lastDownloadId);
		Cursor c = dowanloadmanager.query(query);
		if (c != null && c.moveToFirst()) {
			int status = c.getInt(c
					.getColumnIndex(DownloadManager.COLUMN_STATUS));

			int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
			int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
			int fileSizeIdx = c
					.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
			int bytesDLIdx = c
					.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
			String title = c.getString(titleIdx);
			int fileSize = c.getInt(fileSizeIdx);
			int bytesDL = c.getInt(bytesDLIdx);

			// Translate the pause reason to friendly text.
			int reason = c.getInt(reasonIdx);
			StringBuilder sb = new StringBuilder();
			sb.append(title).append("\n");
			sb.append("Downloaded ").append(bytesDL).append(" / ")
					.append(fileSize);

			// Display the status
			Log.d("tag", sb.toString());
			switch (status) {
			case DownloadManager.STATUS_PAUSED:
				Log.v("tag", "STATUS_PAUSED");
			case DownloadManager.STATUS_PENDING:
				Log.v("tag", "STATUS_PENDING");
			case DownloadManager.STATUS_RUNNING:
				// �������أ������κ�����
				Log.v("tag", "STATUS_RUNNING");

				break;
			case DownloadManager.STATUS_SUCCESSFUL:
				// ���
				Log.v("tag", "�������");
				// dowanloadmanager.remove(lastDownloadId);
				/*
				 * //1������������󣬼�¼APK������Ϣ // AsyncTask�첽����ʼ mTask = new MyTask();
				 * mTask.execute(Config.imei,apk_id,apk_name);
				 */
				// 2����װ�������APK
				installAPK(Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
						+ "/" + apkName);
				break;
			case DownloadManager.STATUS_FAILED:
				// ��������ص����ݣ���������
				Log.v("tag", "STATUS_FAILED");
				dowanloadmanager.remove(lastDownloadId);
				break;
			}
		}
	}

	private void dowloadShow() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(context).setTitle("���°汾").setIcon(R.mipmap.ic_launcher)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �����°汾APK
						downloadApk();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setMessage(apkUpdateinfo).show();

	}

	private void wifeShow() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(context).setTitle("����").setIcon(R.mipmap.ic_launcher)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �����°汾APK
						downloadApk();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setMessage("��ǰ����wifi���磬�Ƿ����أ�").show();
	}
}
