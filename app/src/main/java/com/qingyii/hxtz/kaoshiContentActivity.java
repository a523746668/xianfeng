package com.qingyii.hxtz;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qingyii.hxtz.adapter.DaciViewflowAdapter;
import com.qingyii.hxtz.http.CacheUtil;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.http.YzyHttpClient;
import com.qingyii.hxtz.pojo.DitiChuangg;
import com.qingyii.hxtz.pojo.Examination;
import com.qingyii.hxtz.pojo.PagerQuestionsRela;
import com.qingyii.hxtz.util.CountDownTimerUtil;
import com.qingyii.hxtz.util.EmptyUtil;
import com.qingyii.hxtz.util.TextUtil;
import com.qingyii.hxtz.util.TimeUtil;
import com.zhf.android_viewflow.ViewFlow;
import com.zhf.android_viewflow.ViewFlow.ViewSwitchListener;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 单次命题考试
 * 
 * @author shelia
 *
 */
public class kaoshiContentActivity extends AbBaseActivity {

	//private int mNeedApart = 0;

	private Boolean needCheckError = false; //

	private ProgressDialog mDialogLoading = null;
	private AlertDialog mDialogFinish = null;
	private boolean mIsLoading = false;

	// private AbSlidingTabView absTabView;
	private ImageView iv_back, iv_rank;
	private TextView tv_title;
	// private List<Fragment> mFragmentList;

	/**
	 * 考试对像
	 */
	private Examination examination = null;
	private String password;
	// private ExamContentFragment fragment;
	private ViewFlow kaoshi_viewflow;
	// private KaoShiListAdapter myAdapter;
	// /**
	// * 当场考卷所有题目
	// */
	// private ArrayList<Question> list=new ArrayList<Question>();
	// /**
	// * viewflow加载题目数据源
	// */
	// private ArrayList<Question> dataList=new ArrayList<Question>();
	/**
	 * 每页加载题目数
	 */
	private int pagesize = 10;
	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 单次命题试卷对像
	 */
	private PagerQuestionsRela pagerQuestionsRela;
	private TextView question_time, question_page;
	public TimeCount time;
	/**
	 * 考试用时
	 */
	private int useTime = 0;
	/**
	 * 考试时间到结束标志
	 */
	private boolean overFlag = true;
	/**
	 * 答题总得分数
	 */
	float count = 0;
	/**
	 * 正确答题数
	 */
	int rightNum = 0;
	/**
	 * 错误答题数
	 */
	int errorNum = 0;

	// /**
	// * 未答题数
	// */
	// private ArrayList<Question> noAnserList=new ArrayList<Question>();
	// /**
	// * 当前题目索引
	// */
	// public int selectIndext=0;
	private TextView question_over;
	// private NotifyListView mDataView1;
	// private AbLoadDialogFragment mDialogFragment = null;
	private Handler handler;
	/**
	 * 交卷状态
	 */
	private boolean commitFlag = false;

	/**
	 * 适配器
	 */
	private DaciViewflowAdapter adapter;

	/**
	 * viewflow加载题目数据源
	 */
	private ArrayList<DitiChuangg> dlist = new ArrayList<DitiChuangg>();

	/**
	 * 未答题数
	 */
	private ArrayList<DitiChuangg> noAnserList = new ArrayList<DitiChuangg>();
	private ArrayList<DitiChuangg> errQuestionList = new ArrayList<DitiChuangg>();

	private PopupWindow pop;
	private View mDataView1;

	private Boolean isOver = false;// 考试时间

	private void setOverListener() {
		long starttime = 0;
		long endtime = 0;
		boolean iskk = false;
		try {
			Date startDate = null;
			Date endDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			if (EmptyUtil.IsNotEmpty(examination.getStarttimeStr())) {
				startDate = sdf.parse(examination.getStarttimeStr());

			}
			if (EmptyUtil.IsNotEmpty(examination.getEndtimeStr())) {
				endDate = sdf.parse(examination.getEndtimeStr());
			}
			long nowTime = System.currentTimeMillis() / 1000
					- CacheUtil.timeOffset;
			starttime = startDate.getTime() / 1000;
			endtime = endDate.getTime() / 1000;
			long offsetTime = (endtime - nowTime) * 1000;
			handler.removeMessages(6);
			Message msg = Message.obtain();
			msg.what = 6;
			// Toast.makeText(DatiChuangguanActivity.this, offsetTime+"毫秒后考试过期",
			// Toast.LENGTH_SHORT).show();
			handler.sendMessageAtTime(msg, SystemClock.uptimeMillis()
					+ offsetTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaoshicontent);
		pagerQuestionsRela = (PagerQuestionsRela) getIntent()
				.getSerializableExtra("pagerQuestionsRela");
		//mNeedApart = getIntent().getIntExtra("needApart", 0);
		if (pagerQuestionsRela != null) {
			dlist.clear();
			dlist.addAll(pagerQuestionsRela.getQuestions());
		}
		examination = (Examination) getIntent().getSerializableExtra(
				"examination");
		password = getIntent().getStringExtra("password");
		handler = new Handler(new Callback() {

			@SuppressLint("NewApi")
			@Override
			public boolean handleMessage(Message msg) {
				// if (mDialogFragment!=null) {
				// mDialogFragment.dismiss();
				// }
				if (msg.what == 1) {
					if (dlist.size() > 0) {
						adapter.notifyDataSetChanged();
						question_page.setText("1/"
								+ pagerQuestionsRela.getQuestioncount());
						// 数据加载完后计时开始
						time.start();
					} else {
						Toast.makeText(kaoshiContentActivity.this, "此考卷暂无题目",
								Toast.LENGTH_SHORT).show();
					}
				} else if (msg.what == 2) {

				} else if (msg.what == 0) {
					Toast.makeText(kaoshiContentActivity.this, "考卷提交失败！请重新提交！",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 5) {
					Toast.makeText(kaoshiContentActivity.this, "此考卷暂无题目",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == 6) {
					isOver = true;
					Toast.makeText(kaoshiContentActivity.this, "本次考试时间到，已自动交卷",
							Toast.LENGTH_SHORT).show();
					overFlag = false;
					TimuPanduan();
					jsdfqingkuang();
				}
				return true;
			}
		});
		initUI();
		handler.sendEmptyMessage(1);
		setOverListener();
		// initData();
	}

	// private void initData() {
	// // TODO Auto-generated method stub
	// /*for (int i = 0; i < 5; i++) {
	// Question q=new Question();
	// q.setQuestiondesc("问题"+i);
	// q.setAnswer1("a答案"+i);
	// q.setAnswer2("b答案"+i);
	// q.setAnswer3("c答案"+i);
	// q.setAnswer4("d答案"+i);
	// list.add(q);
	// }*/
	// //显示进度框
	// mDialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load,
	// "考题加载中，请稍等！");
	// mDialogFragment
	// .setAbDialogOnLoadListener(new AbDialogOnLoadListener() {
	//
	// @Override
	// public void onLoad() {
	// // 下载网络数据
	// if(pagerQuestionsRela!=null){
	// if(EmptyUtil.IsNotEmpty(pagerQuestionsRela.getPaperid())){
	// getData(Integer.parseInt(pagerQuestionsRela.getPaperid()));
	// }
	// }
	// }
	//
	// });
	//
	// }
	// private void getData(int paperid) {
	// JSONObject jsonObject = new JSONObject();
	// try {
	// jsonObject.put("paperid", paperid);
	//
	// byte[] bytes;
	// ByteArrayEntity entity = null;
	// try {
	// // stringEntity = new StringEntity(jsonObject.toString());
	// // 处理保存数据中文乱码问题
	// bytes = jsonObject.toString().getBytes("utf-8");
	// entity = new ByteArrayEntity(bytes);
	// YzyHttpClient.post(kaoshiContentActivity.this,
	// HttpUrlConfig.queryPagersQuestionRelaList, entity,
	// new AsyncHttpResponseHandler() {
	// @Override
	// public void onSuccess(int statecode, String arg0) {
	// // TODO Auto-generated method stub
	// if(statecode==499){
	// Toast.makeText(kaoshiContentActivity.this, CacheUtil.logout,
	// Toast.LENGTH_SHORT).show();
	// Intent intent=new Intent(kaoshiContentActivity.this,LoginActivity.class);
	// startActivity(intent);
	// onFinish();
	// }else if (statecode == 200) {
	// Gson gson = new Gson();
	// try {
	// // if (type == 1) {
	// // myList.clear();
	// // page = 2;
	// // }
	// JSONObject js = new JSONObject(arg0);
	// JSONArray lists = js
	// .getJSONArray("rows");
	// if (lists.length() <= 0) {
	// handler.sendEmptyMessage(5);
	// } else {
	// // 如果获取到更新数据，页码加1
	// // if (type == 2) {
	// // page += 1;
	// // }
	//
	// for (int i = 0; i < lists.length(); i++) {
	// Question b = gson.fromJson(
	// lists.getString(i),
	// Question.class);
	// //测试复选类似题目
	// // b.setQuestionType("2");
	// list.add(b);
	// }
	//
	// // 服务器正确获取数据更新数据源
	// handler.sendEmptyMessage(1);
	// }
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// handler.sendEmptyMessage(0);
	// e.printStackTrace();
	// }
	// }
	//
	// }
	//
	// @Override
	// public void onFailure(int statusCode,
	// Throwable error, String content) {
	// // TODO Auto-generated method stub
	// super.onFailure(statusCode, error, content);
	// handler.sendEmptyMessage(0);
	// System.out.println("失败");
	// }
	//
	// @Override
	// public void onFinish() {
	// // TODO Auto-generated method stub
	// super.onFinish();
	// System.out.println("结束方法");
	// }
	//
	// });
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// handler.sendEmptyMessage(0);
	// e1.printStackTrace();
	// }
	// } catch (JSONException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// handler.sendEmptyMessage(0);
	// }
	//
	// }
	@Override
	public void onBackPressed() {
		if (dlist.size() > 0) {
			if (commitFlag) {
				kaoshiContentActivity.this.finish();
			} else {
				new AlertDialog.Builder(kaoshiContentActivity.this)
						.setTitle("提醒")
						.setIcon(R.mipmap.ic_launcher)
						.setMessage("现在正在考试是否退出，退出则自动交卷！")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										TimuPanduan();
										jsdfqingkuang();
									}
								}).setNegativeButton("否", null).show();
			}
		} else {
			kaoshiContentActivity.this.finish();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		setOverListener();
	}

	@Override
	protected void onStop() {
		super.onStop();
		handler.removeMessages(6);
	}

	/**
	 * 例计时
	 * 
	 * @author shelia
	 *
	 */
	public class TimeCount extends CountDownTimerUtil {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			Toast.makeText(kaoshiContentActivity.this, "考试已结束！",
					Toast.LENGTH_SHORT).show();
			question_time.setText("考试已结束");
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			Log.d("test", "onTick");
			// 倒计时
			// question_time.setText(TimeUtil.secToTime(Integer.parseInt(millisUntilFinished/1000+"")));

			if (overFlag) {
				// 考试用时加1
				useTime++;
				// 计时
				question_time.setText(TimeUtil.secToTime(useTime));
				if ((pagerQuestionsRela != null) && (examination != null)) {

					if (EmptyUtil.IsNotEmpty(examination.getDuration())) {
						// 考试时长：分
						int duration = Integer.parseInt(examination
								.getDuration());
						if (useTime >= duration * 60) {
							Toast.makeText(kaoshiContentActivity.this,
									"考试时间到，交卷中...", Toast.LENGTH_SHORT).show();
							overFlag = false;
							TimuPanduan();
							jsdfqingkuang();
						}
					}
				}
			}

			// 考试用时加1
			// useTime++;
			// //计时
			// question_time.setText(TimeUtil.secToTime(useTime));
			// System.out.println("考试用时："+TimeUtil.secToTime(useTime));
			// question_time.setText(millisUntilFinished /1000+"秒");
		}
	}

	/**
	 * 显示考试成绩
	 */
	private void showScore() {
		// mDataView1 = mInflater.inflate(R.layout.kaoshi_sorce, null);

		if (mDataView1 == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			mDataView1 = mLayoutInflater.inflate(R.layout.kaoshi_sorce, null);
			pop = new PopupWindow(mDataView1,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

		}
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);

		// 设置点击窗口外边窗口不消失
		pop.setOutsideTouchable(false);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

		TextView pop_countmsg = (TextView) mDataView1
				.findViewById(R.id.pop_countmsg);
		TextView pop_time = (TextView) mDataView1.findViewById(R.id.pop_time);
		TextView pop_error = (TextView) mDataView1.findViewById(R.id.pop_error);
		TextView pop_right = (TextView) mDataView1.findViewById(R.id.pop_right);
		TextView pop_count = (TextView) mDataView1.findViewById(R.id.pop_count);

		LinearLayout pop_ll = (LinearLayout) mDataView1
				.findViewById(R.id.pop_ll);

		pop_right.setText("答对" + rightNum + "道题");
		pop_error.setText("答错" + errorNum + "道题");
		pop_time.setText("总共考试用时:" + TimeUtil.secToTime02(useTime));

		Button pop_ok = (Button) mDataView1.findViewById(R.id.pop_ok);
		Button pop_next = (Button) mDataView1.findViewById(R.id.pop_next);
		Button pop_again = (Button) mDataView1.findViewById(R.id.pop_again);

		Button check_error = (Button) mDataView1.findViewById(R.id.check_error);
		check_error.setText("查看错题");
		if (errQuestionList.size() > 0
				&& "1".equals(examination.getShowerrlist())) {
			check_error.setVisibility(View.VISIBLE);
			check_error.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 交卷成功后再显示错题
					// needCheckError = true;
					pop.dismiss();
					// onDissmes();
					// Toast.makeText(kaoshiContentActivity.this, "正在提交成绩...",
					// Toast.LENGTH_SHORT).show();
					// if(needCheckError == true)
					// {
					Intent intent = new Intent(kaoshiContentActivity.this,
							KaoshiCheckErrorActivity.class);
					intent.putExtra("examination",
							kaoshiContentActivity.this.examination);
					intent.putExtra("errQuestionList", errQuestionList);

					startActivity(intent);
					kaoshiContentActivity.this.finish();
					// }else{
					// kaoshiContentActivity.this.finish();
					// }
				}
			});
		} else {
			check_error.setVisibility(View.GONE);
		}

		// 可参加考试次数
		int counter = 0;
		counter = Integer.parseInt(examination.getAnsertime());
		// 参与过的考试次数
		int jionCount = 0;
		jionCount = Integer.parseInt(pagerQuestionsRela.getAnsercount());

		if (isOver) {
			pop_count.setText("您本次考试的得分为："
					+ TextUtil.getDefaultDecimalFormat().format(count) + "分");
			pop_countmsg.setText("本次考试时间到！");
			pop_ok.setVisibility(View.VISIBLE);
			pop_ll.setVisibility(View.GONE);
		} else if ((counter - jionCount - 1) > 0) {
			pop_count.setText("您本次考试的得分为："
					+ TextUtil.getDefaultDecimalFormat().format(count) + "分");
			pop_countmsg.setText("您还可以再做" + (counter - jionCount - 1)
					+ "次，请选择是否再做一次？");
			pop_ok.setVisibility(View.GONE);
			pop_next.setText("再做一次");
			pop_again.setText("下次再做");
			pop_ll.setVisibility(View.VISIBLE);
		} else {
			pop_count.setText("您本次考试的得分为："
					+ TextUtil.getDefaultDecimalFormat().format(count) + "分");
			pop_countmsg.setText("您的答题次数已经用完！");
			pop_ok.setVisibility(View.VISIBLE);
			pop_ll.setVisibility(View.GONE);
		}

		pop_again.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// AbDialogUtil.removeDialog(mDataView1);
				pop.dismiss();
				kaoshiContentActivity.this.finish();
			}
		});

		pop_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// AbDialogUtil.removeDialog(mDataView1);
				pop.dismiss();

				for (int i = 0; i < pagerQuestionsRela.getQuestions().size(); i++) {
					pagerQuestionsRela.getQuestions().get(i).getXxid().clear();
				}

				// pagerQuestionsRela.setAnsercount((Integer.parseInt(pagerQuestionsRela.getAnsercount())+1)+"");
				page = 1;
				dlist.clear();
				gerData(password, true, page);

				// Intent intent=new
				// Intent(kaoshiContentActivity.this,kaoshiContentActivity.class);
				// intent.putExtra("pagerQuestionsRela",
				// pagerQuestionsRela);
				// intent.putExtra("examination",
				// examination);
				// startActivity(intent);
				// kaoshiContentActivity.this.finish();

			}
		});

		pop_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// AbDialogUtil.removeDialog(mDataView1);
				pop.dismiss();
				kaoshiContentActivity.this.finish();
			}
		});

		pop.showAtLocation(this.findViewById(R.id.kaoshicontent_rl),
				Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
		// AbDialogUtil.showDialog(mDataView1,Gravity.CENTER);
		// AbDialogUtil.showAlertDialog(mDataView1);
		// AbDialogUtil.showFullScreenDialog(mDataView1);

	}

	/**
	 * 提交考试成绩
	 */
	public void addScore() {
		if (mDialogFinish != null) {
			mDialogFinish.cancel();
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userid", CacheUtil.userid);
			jsonObject.put("examinationid", examination.getExaminationid());
			jsonObject.put("paperid", pagerQuestionsRela.getPaperid());
			// 分数
			jsonObject.put("score", count);
			// 用时：秒
			jsonObject.put("duration", useTime);
			byte[] bytes;
			ByteArrayEntity entity = null;
			try {
				// stringEntity = new StringEntity(jsonObject.toString());
				// 处理保存数据中文乱码问题
				bytes = jsonObject.toString().getBytes("utf-8");
				entity = new ByteArrayEntity(bytes);
				YzyHttpClient.post(kaoshiContentActivity.this,
						HttpUrlConfig.addScore, entity,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statecode, String arg0) {
								// TODO Auto-generated method stub
								if (statecode == 499) {
									Toast.makeText(kaoshiContentActivity.this,
											CacheUtil.logout,
											Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(
											kaoshiContentActivity.this,
											LoginActivity.class);
									startActivity(intent);
									onFinish();
								} else if (statecode == 200) {
									JSONObject obj;
									try {
										obj = new JSONObject(arg0);
										if ("add_success".equals(obj
												.getString("message"))) {
											// 1、显示考试完成情况UI
											showScore();
											// 2、隐藏结束UI，防止重复提交服务器数据
											question_over
													.setVisibility(View.INVISIBLE);

										} else {
											count = 0;
											rightNum = 0;
											errorNum = 0;
											Toast.makeText(
													kaoshiContentActivity.this,
													"交卷失败，请重新交卷！",
													Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										count = 0;
										rightNum = 0;
										errorNum = 0;
										Toast.makeText(
												kaoshiContentActivity.this,
												"交卷失败，服务返回数据异常，请重新交卷！",
												Toast.LENGTH_SHORT).show();
										e.printStackTrace();
									}

								}

							}

							@Override
							public void onFailure(int statusCode,
									Throwable error, String content) {
								// TODO Auto-generated method stub
								super.onFailure(statusCode, error, content);
								Toast.makeText(kaoshiContentActivity.this,
										"交卷失败，请重新交卷！", Toast.LENGTH_SHORT)
										.show();
								if (statusCode == 499) {
									Toast.makeText(kaoshiContentActivity.this,
											CacheUtil.logout,
											Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(
											kaoshiContentActivity.this,
											LoginActivity.class);
									startActivity(intent);
									onFinish();
								}
							}

							@Override
							public void onFinish() {
								// TODO Auto-generated method stub
								super.onFinish();
								System.out.println("结束方法");
								// 3、设置交卷状态： true
								commitFlag = true;
							}

						});
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(0);
				e1.printStackTrace();
			}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			handler.sendEmptyMessage(0);
		}

	}

	@SuppressLint("NewApi")
	private void initUI() {
		question_over = (TextView) findViewById(R.id.question_over);

		question_over.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				count = 0;
				rightNum = 0;
				errorNum = 0;
				// new AlertDialog.Builder(kaoshiContentActivity.this)
				// .setTitle("提醒").setIcon(R.drawable.ic_launcher)
				// .setMessage("确定交卷吗？")
				// .setPositiveButton("是", new DialogInterface.OnClickListener()
				// {
				//
				// @Override
				// public void onClick(DialogInterface arg0, int arg1) {
				// if(list.size()>0){
				// jsdfqingkuang();
				// }else{
				// Toast.makeText(kaoshiContentActivity.this, "没有试题的考卷不需要交卷！",
				// Toast.LENGTH_SHORT).show();
				// }
				// }
				// })
				// .setNegativeButton("否", new DialogInterface.OnClickListener()
				// {
				//
				// @Override
				// public void onClick(DialogInterface arg0, int arg1) {
				//
				// }
				// })
				// .show();

				// for(int i=0;i<dlist.size();i++){
				// if(panduan(i)){
				// dlist.get(i).setTimuflag(true);
				// }else{
				// dlist.get(i).setTimuflag(false);
				// }
				//
				// if(dlist.get(i).getXxid().isEmpty()){
				// dlist.get(i).setNoAnderInext(0);
				// }else{
				// dlist.get(i).setNoAnderInext(1);
				// }
				// }
				//
				// //获取未答题数
				// noAnserList.clear();
				// for (int i = 0; i < dlist.size(); i++) {
				// DitiChuangg q=dlist.get(i);
				// if(q.getNoAnderInext()==0){
				//
				// noAnserList.add(q);
				// }
				// }

				TimuPanduan();
				mDialogFinish = new AlertDialog.Builder(
						kaoshiContentActivity.this).create();
				mDialogFinish.show();
				Window window = mDialogFinish.getWindow();
				// *** 主要就是在这里实现这种效果的.
				// 设置窗口的内容页面,exit_app_tip.xml文件中定义view内容
				window.setContentView(R.layout.kaoshi_over_tip);
				// 为确认按钮添加事件,执行退出应用操作
				TextView ok = (TextView) window.findViewById(R.id.ks_yes);
				TextView ks_content = (TextView) window
						.findViewById(R.id.ks_content);
				ok.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (mDialogFinish != null) {
							mDialogFinish.cancel();
						}
						if (dlist.size() > 0) {
							TimuPanduan();
							jsdfqingkuang();
						} else {
							Toast.makeText(kaoshiContentActivity.this,
									"没有试题的考卷不需要交卷！", Toast.LENGTH_SHORT).show();
						}
					}
				});
				TextView ks_no = (TextView) window.findViewById(R.id.ks_no);
				ks_no.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (mDialogFinish != null) {
							mDialogFinish.cancel();
						}
						// 切换最第一个未做题
						// if(noAnserList.size()>0){
						// kaoshi_viewflow.snapToScreen(noAnserList.get(0).getNoAnderInext());
						// }else{
						// kaoshi_viewflow.snapToScreen(0);
						// }

						// if(noAnserList.size()>0){
						// for(int i=0;i<dlist.size();i++){
						// if(dlist.get(i).getNoAnderInext()==0){
						// kaoshi_viewflow.snapToScreen(i);
						// }
						// }
						// }else{
						// kaoshi_viewflow.snapToScreen(0);
						// }
						for (int i = 0; i < dlist.size(); i++) {
							if (dlist.get(i).getNoAnderInext() == 0) {
								adapter = new DaciViewflowAdapter(
										kaoshiContentActivity.this, dlist);
								kaoshi_viewflow.setAdapter(adapter, i);
								break;
							}
						}

					}
				});
				// 关闭alert对话框架
				ImageView cancel = (ImageView) window
						.findViewById(R.id.ks_close);
				cancel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (mDialogFinish != null) {
							mDialogFinish.cancel();
						}
					}
				});
				if (noAnserList.size() > 0) {
					ks_content.setText("还有"
							+ (noAnserList.size() + (pagerQuestionsRela
									.getQuestioncount() - dlist.size()))
							+ "题未做，确认交卷？");
				} else {
					ks_content.setText("否检查一遍？");
					ks_no.setText("检查一遍");
				}
			}

		});

		question_page = (TextView) findViewById(R.id.question_page);
		question_page.setText("1/" + pagerQuestionsRela.getQuestioncount());

		kaoshi_viewflow = (ViewFlow) findViewById(R.id.kaoshi_viewflow);
		// myAdapter=new KaoShiListAdapter(this, list,kaoshi_viewflow);
		// if(list.size()>pagesize){
		// dataList.addAll(list.subList(0, pagesize));
		// myAdapter=new KaoShiListAdapter(this, dataList,kaoshi_viewflow);
		// }else{
		// dataList.addAll(list);
		// myAdapter=new KaoShiListAdapter(this, dataList,kaoshi_viewflow);
		// }

		adapter = new DaciViewflowAdapter(kaoshiContentActivity.this, dlist);
		kaoshi_viewflow.setAdapter(adapter, 0);

		kaoshi_viewflow.setOnViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void onSwitched(View view, int position) {
				// TODO Auto-generated method stub
				// selectIndext=position;
				// if(position<list.size()-1){
				// if(position==dataList.size()-2){
				// //当前滚倒数第二个考题时加载下一页
				// page++;
				// if(page*pagesize<list.size()){
				// dataList.addAll(list.subList(dataList.size(),
				// page*pagesize));
				// myAdapter.notifyDataSetChanged();
				// }else{
				// dataList.addAll(list.subList(dataList.size(), list.size()));
				// myAdapter.notifyDataSetChanged();
				// }
				// }
				// }
				// question_page.setText(position+1+"/"+list.size());
				int totalPage = pagerQuestionsRela.getQuestioncount();
				question_page.setText(position + 1 + "/" + totalPage);

				int nowPage = (dlist.size() - 1) / pagesize + 1;
				if (position <= dlist.size() - 1
						&& position >= dlist.size() - 4
						&& dlist.size() < totalPage) {
					kaoshiContentActivity.this.gerData(password, false,
							nowPage + 1);
				}
				Log.d("test", "nowPage:" + nowPage);

				if (mIsLoading
						&& position == dlist.size() - 1
						&& (dlist.size() != pagerQuestionsRela
								.getQuestioncount())) {
					Toast.makeText(kaoshiContentActivity.this, "正在加载下一页",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		question_time = (TextView) findViewById(R.id.question_time);
		time = new TimeCount(2 * 60 * 60 * 1000, 1000);

		tv_title = (TextView) findViewById(R.id.title_kaoshicontent);
		// 加粗中文字体
		// tv_title.getPaint().setFakeBoldText(true);
		iv_back = (ImageView) findViewById(R.id.iv_back_kaoshicontent);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
		iv_rank = (ImageView) findViewById(R.id.iv_rank_kaoshicontent);
		iv_rank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(kaoshiContentActivity.this,
						kaoshiRankActivity.class);
				it.putExtra("examination", examination);
				startActivity(it);
				// selectIndext+=1;
				// if(selectIndext<list.size()){
				// kaoshi_viewflow.snapToScreen(selectIndext);
				// }
			}
		});
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});

		if (examination != null) {
			tv_title.setText(examination.getExaminationname());
		}
	}

	/**
	 * 结束考试处理方法
	 */
	public synchronized void jsdfqingkuang() {
		time.cancel();
		count = 0;
		rightNum = 0;
		errorNum = 0;
		// 2、计算考生得分情况及正确，错误题数
		for (int i = 0; i < dlist.size(); i++) {
			DitiChuangg q = dlist.get(i);
			if (q.isTimuflag()) {
				rightNum++;
				int qType = Integer.parseInt(q.getRedios());
				switch (qType) {
				case 1:
					// 单选题
					if (EmptyUtil.IsNotEmpty(pagerQuestionsRela.getScore1())) {
						count += Float.parseFloat(pagerQuestionsRela
								.getScore1());
					}
					break;
				case 2:
					// 多选题
					if (EmptyUtil.IsNotEmpty(pagerQuestionsRela.getScore2())) {
						count += Float.parseFloat(pagerQuestionsRela
								.getScore2());
					}
					break;
				case 3:
					// 判断题
					if (EmptyUtil.IsNotEmpty(pagerQuestionsRela.getScore3())) {
						count += Float.parseFloat(pagerQuestionsRela
								.getScore3());
					}
					break;
				}
			} else {
				errorNum++;
			}
		}
		addScore();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 闭关计时器
		if (time != null) {
			time.cancel();
		}
	}

	/**
	 * 判断题目是否正确
	 * 
	 * @param position
	 * @return
	 */
	private boolean panduan(final int position) {

		boolean flag = true;
		// 相等个数
		int countge = 0;

		if (dlist.get(position).getQuestionOptionList().size() <= 0) {
			flag = false;
		} else {
			for (int i = 0; i < dlist.get(position).getQuestionOptionList()
					.size(); i++) {
				if ("1".equals(dlist.get(position).getQuestionOptionList()
						.get(i).getIsright())) {
					if (!dlist
							.get(position)
							.getXxid()
							.contains(
									dlist.get(position).getQuestionOptionList()
											.get(i).getOptionid())) {
						flag = false;
					}
					countge++;
				}
			}

		}
		if (dlist.get(position).getXxid().size() != countge) {
			flag = false;
		}

		return flag;
	}

	/**
	 * 单次命题
	 * 
	 * @param password
	 */
	private void gerData(final String password, final boolean requestReset,
			int requestPage) {
		if (mIsLoading) {
			return;
		}
		if (!requestReset && requestPage <= page) {
			return;
		}
		if (requestReset) {
			mDialogLoading = ProgressDialog.show(kaoshiContentActivity.this,
					"", "试题加载中...");
			mDialogLoading.setCancelable(true);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("passwordlock", password);
			jsonObject.put("userid", CacheUtil.userid);
			jsonObject.put("page", requestPage);
			jsonObject.put("pagesize", pagesize);
			jsonObject.put("type", 1);
			jsonObject.put("examinationid", examination.getExaminationid());
			byte[] bytes = jsonObject.toString().getBytes("utf-8");
			ByteArrayEntity entity = new ByteArrayEntity(bytes);
			mIsLoading = true;
			Log.d("test", "req json:" + jsonObject.toString());
			YzyHttpClient.post(kaoshiContentActivity.this,
					HttpUrlConfig.queryExaminationPaperPassword, entity,
					new AsyncHttpResponseHandler() {
						@SuppressLint("NewApi")
						@Override
						public void onSuccess(int statusCode, String content) {

							super.onSuccess(statusCode, content);
							mIsLoading = false;
							if (statusCode == 499) {
								Toast.makeText(kaoshiContentActivity.this,
										CacheUtil.logout, Toast.LENGTH_SHORT)
										.show();
								Intent intent = new Intent(
										kaoshiContentActivity.this,
										LoginActivity.class);
								startActivity(intent);
								onFinish();
							}

							try {
								page++;
								JSONObject obj = new JSONObject(content);
								String questionListPaStr = obj
										.getString("examinationPapersPa");
								if (EmptyUtil.IsNotEmpty(questionListPaStr)) {
									// 密码正确
									JSONArray mylistArray = obj
											.getJSONArray("examinationPapersPa");
									Gson gson = new Gson();
									if (mylistArray.length() > 0) {
										//String str = mylistArray.getString(0);
										//JSONObject objRoot = new JSONObject(str);
//										String typeApart = objRoot
//												.getString("type");
										PagerQuestionsRela pqr = gson.fromJson(
												mylistArray.getString(0),
												PagerQuestionsRela.class);
										// 可参加考试次数
										int count = 0;
										if (EmptyUtil.IsNotEmpty(examination
												.getAnsertime())) {
											count = Integer
													.parseInt(examination
															.getAnsertime());
										}
										// 参与过的考试次数
										int jionCount = 0;
										if (EmptyUtil.IsNotEmpty(pqr
												.getAnsercount())) {
											jionCount = Integer.parseInt(pqr
													.getAnsercount());
										}
										if (jionCount < count) {
											if (requestReset) {
												Intent intent = new Intent(
														kaoshiContentActivity.this,
														kaoshiContentActivity.class);
												intent.putExtra(
														"pagerQuestionsRela",
														pqr);
												intent.putExtra("examination",
														examination);
//												intent.putExtra(
//														"needApart",
//														Integer.parseInt(typeApart));
												intent.putExtra("password",
														password);
												startActivity(intent);
												finish();
											} else {
												dlist.addAll(pqr.getQuestions());
												adapter.notifyDataSetChanged();
											}
											Log.d("test", "reuslt size :"
													+ dlist.size());
										} else {
											Toast.makeText(
													kaoshiContentActivity.this,
													"你考试次数已过,不能再参加考试了！",
													Toast.LENGTH_LONG).show();
										}
									} else {
										Toast.makeText(
												kaoshiContentActivity.this,
												"此考试无题目", Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									// 密码错误
									Toast.makeText(kaoshiContentActivity.this,
											"密码错误", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							mIsLoading = false;
							gerData(password, requestReset, page);
							// System.out.println("content");
						}

						@Override
						public void onFinish() {
							super.onFinish();
							mIsLoading = false;
							if (mDialogLoading != null) {
								mDialogLoading.dismiss();
							}
						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 判断题目是否答对和未答题目已答题目的个数
	private void TimuPanduan() {
		errQuestionList.clear();
		for (int i = 0; i < dlist.size(); i++) {
			if (panduan(i)) {
				dlist.get(i).setTimuflag(true);
			} else {
				dlist.get(i).setTimuflag(false);
				errQuestionList.add(dlist.get(i));
			}

			if (dlist.get(i).getXxid().isEmpty()) {
				dlist.get(i).setNoAnderInext(0);
			} else {
				dlist.get(i).setNoAnderInext(1);
			}
		}

		// 获取未答题数
		noAnserList.clear();
		for (int i = 0; i < dlist.size(); i++) {
			DitiChuangg q = dlist.get(i);
			if (q.getNoAnderInext() == 0) {
				noAnserList.add(q);
			}
		}
	}

}
