package com.qingyii.hxt.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.view.refresh.AbPullToRefreshView;
import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.ShiGuangZhouAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.XrjHttpClient;
import com.qingyii.hxt.pojo.Associates;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.view.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 时光轴主页
 */
public class ShiGuangZhou extends AppCompatActivity implements View.OnClickListener {

    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    //private int mRefreshOrgType;
    private int mRefreshDepartmentType;
    //private int mRefreshFollowType;

    //private int mPageOrg = 1;
    private int mPageDepartment = 1;
    //private int mPageFollow = 1;
    private int mPagesize = 10;

    private ImageView mTextLeft;
    private TextView mTextRight;
    //原有的三个分区（已删除）
//    private TextView mTextOrg;
//    private TextView mTextDepartment;
//    private TextView mTextFollow;
//
//    private AbPullToRefreshView mRefreshOrg;
//    private ArrayList<DynamicInfo> mDatasOrg = new ArrayList<DynamicInfo>();
//    private ArrayList<DynamicInfo> mDatasFollow = new ArrayList<DynamicInfo>();
//    private ListView mListView;
//    private ListView mListFollow;

    private List<Associates.DataBean.DocsBean> aDataDocsBeen = new ArrayList<Associates.DataBean.DocsBean>();
    //    private ArrayList<DynamicInfo> mDatasDepartment = new ArrayList<DynamicInfo>();
    private AbPullToRefreshView mRefreshDepartment;
    private ListView mListDepartment;

    //private CircleAdapter mCircleAdapter;
    private ShiGuangZhouAdapter mDepartmentAdapter;

    //NotifyView of department header
    private TextView mTextNum2, mTextNum3, mTextNum4, mTextNum5;
    private TextView mTextDmInfo;
    //private ImageView mImgDmBg;
    private LinearLayout circle_head_star_ll;
    private ImageView iv1, iv2, iv3, iv4, iv5;
    private TextView circle_star_no;
    private RoundedImageView mImgDmPhoto;

    private static int QiTaRen = -1; //其他人
    private static int Mine = 1; //我的

    private int userId;
    private String UserPrams;
    private int shiguangType;
    private List<ImageView> imageLists = new ArrayList<>();
    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog;

    private int skipParameter = 0;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private Handler mHandler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
//            if (msg.arg1 == 0) {
//                mRefreshOrg.onHeaderRefreshFinish();
//                mRefreshOrg.onFooterLoadFinish();
//            } else if (msg.arg1 == 1) {
            mRefreshDepartment.onHeaderRefreshFinish();
            mRefreshDepartment.onFooterLoadFinish();
            mDepartmentAdapter.notifyDataSetChanged();
//            }
            switch (msg.what) {
                case 0:
                    break;
                case 1:
//                    if (msg.arg1 == 0) {
//                        mCircleAdapter.notifyDataSetChanged();
//                    } else if (msg.arg1 == 1) {
//                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
//                    DepartmentInfo info = (DepartmentInfo) msg.obj;
//                    mTextNum1.setText("" + info.getDeppersoncount());
//                    mTextNum2.setText("" + info.getDynamicinfocount());
//                    mTextNum3.setText("" + info.getCommentcount());
//                    mTextNum4.setText("" + info.getReceivelovecount());
//                    mTextNum5.setText("" + info.getSendlovecount());
//                    String dmInfo = "负责人 : " + info.getDirector().getUsername()
//                            + " " + "职位 : " + info.getDirector().getJobname()
//                            + "\n" + "管理员 : " + info.getManager().getUsername()
//                            + " " + "职位 : " + info.getManager().getJobname();
//                    mTextDmInfo.setText(dmInfo);
//                    ImageLoader.getInstance().displayImage(
//                            HttpUrlConfig.photoDir + info.getLogourl(),
//                            mImgDmPhoto);
//                    ImageLoader.getInstance().displayImage(
//                            HttpUrlConfig.photoDir + info.getBackgroundurl(),
//                            mImgDmBg, ImageUtil.newDisplayOptions(R.mipmap.bg_user_default));
                case 7:
                    int errorcode = msg.arg2;
                    if (errorcode == 0) {
//                        if (msg.arg1 == 0) {
//                            mRefreshOrg.headerRefreshing();
//                        } else if (msg.arg1 == 1) {
                        mRefreshDepartment.headerRefreshing();
//                        } else {
//                        }
                    } else {
                        Toast.makeText(ShiGuangZhou.this, "删除失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    break;
            }

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_shiguangzhou);

        getExtras();

        findView();
        setListener();
//        getDepartmentCount();
        associatesList(0);
    }

    private void getExtras() {

        Bundle b = getIntent().getExtras();
        if (b != null) {

            this.userId = b.getInt("UserId");
            this.shiguangType = b.getInt("ShiGuangType"); //自己的还是其他的

            if (shiguangType == QiTaRen) {

                UserPrams = String.valueOf(userId);

            } else if (shiguangType == Mine) {

                UserPrams = "my";

            } else if (shiguangType == 0) {  //不知道什么情况
                try {
                    int selfid = MyApplication.getInstance().getUserUtil().getId();
                    if (selfid != userId) {
                        UserPrams = String.valueOf(userId);
                    } else {
                        UserPrams = "my";
                    }

                } catch (NullPointerException e) {

                    Toast.makeText(ShiGuangZhou.this, "用户参数获取失败", Toast.LENGTH_SHORT);

                }

            }

            System.out.println("shiguangType =" + shiguangType + " prames= " + UserPrams);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mRefreshOrg.getVisibility() == NotifyView.VISIBLE) {
//            mRefreshOrg.headerRefreshing();
//        } else if (mRefreshDepartment.getVisibility() == NotifyView.VISIBLE) {
        if (skipParameter == -1)
            mRefreshDepartment.headerRefreshing();
        else
            mDepartmentAdapter.notifyDataSetChanged();
//        } else {
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("同事圈详情返回值：", " " + requestCode + "  " + resultCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == -1) {
                //发布返回操作（接收不到）
                mRefreshDepartment.headerRefreshing();
            } else {
                //详情返回部分
                switch (data.getIntExtra("operation", -1)) {
                    case 1:
                        aDataDocsBeen.set(requestCode, (Associates.DataBean.DocsBean) data.getParcelableExtra("aDocsBeanResult"));
                        break;
                    case 2:
                        aDataDocsBeen.set(requestCode, (Associates.DataBean.DocsBean) data.getParcelableExtra("aDocsBeanResult"));
                        break;
                    case 3:
                        aDataDocsBeen.remove(requestCode);
                        break;
                    default:
                        break;
                }
                mDepartmentAdapter.notifyDataSetChanged();
            }
        }
    }

    private void findView() {
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        mTextLeft = (ImageView) this.findViewById(R.id.main_circle_left);
        mTextRight = (TextView) this.findViewById(R.id.main_circle_right);
        //原有的三个分区（已删除）
//        mTextOrg = (TextView) this.findViewById(R.id.main_circle_org);
//        mTextDepartment = (TextView) this.findViewById(R.id.main_circle_department);
//        mTextFollow = (TextView) this.findViewById(R.id.main_circle_follow);
        //无header页面（已删除）
//        mRefreshOrg = (AbPullToRefreshView) this.findViewById(R.id.main_circle_refreshorg);
//        this.mListView = (ListView) this.findViewById(R.id.main_circle_listorg);
//        mCircleAdapter = new CircleAdapter(this,mDialogFragment,this.mDatasOrg);
//        this.mListView.setAdapter(mCircleAdapter);

        mRefreshDepartment = (AbPullToRefreshView) this.findViewById(R.id.main_circle_refreshdepartment);
        mListDepartment = (ListView) this.findViewById(R.id.main_circle_listdepartment);
        mDepartmentAdapter = new ShiGuangZhouAdapter(this, shapeLoadingDialog, aDataDocsBeen, mHandler);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_circle_department_head, null);
        mTextNum2 = (TextView) headView.findViewById(R.id.circle_dm_head_num2);
        mTextNum3 = (TextView) headView.findViewById(R.id.circle_dm_head_num3);
        mTextNum4 = (TextView) headView.findViewById(R.id.circle_dm_head_num4);
        mTextNum5 = (TextView) headView.findViewById(R.id.circle_dm_head_num5);
        mTextDmInfo = (TextView) headView.findViewById(R.id.circle_dm_head_info);
        circle_star_no = (TextView) headView.findViewById(R.id.circle_head_star1_no);
        circle_head_star_ll = (LinearLayout) headView.findViewById(R.id.circle_head_star_ll);
        iv1 = (ImageView) headView.findViewById(R.id.circle_head_star1);
        iv2 = (ImageView) headView.findViewById(R.id.circle_head_star2);
        iv3 = (ImageView) headView.findViewById(R.id.circle_head_star3);
        iv4 = (ImageView) headView.findViewById(R.id.circle_head_star4);
        iv5 = (ImageView) headView.findViewById(R.id.circle_head_star5);
        imageLists.add(iv1);
        imageLists.add(iv2);
        imageLists.add(iv3);
        imageLists.add(iv4);
        imageLists.add(iv5);
        mImgDmPhoto = (RoundedImageView) headView.findViewById(R.id.circle_dm_head_photo);

        //原有的三个分区（已删除）
//        mImgDmBg = (ImageView) headView.findViewById(R.id.circle_dm_head_bg);
//        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
//                (int) (wm.getDefaultDisplay().getHeight() * 0.4f)));
        mListDepartment.addHeaderView(headView);

        mListDepartment.setAdapter(mDepartmentAdapter);
    }

    private void setListener() {
        mTextLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        mTextRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ShiGuangZhou.this, CircleCreateActivity.class);
//                intent.putExtra("aDocsBean", aDataDocsBeen.get(0));
                skipParameter = -1;
                startActivityForResult(intent, -1);
            }
        });
        mListDepartment.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // arg2 ==0 headview
//                int index = position - 1;
//                if (index >= 0 && index < aDataDocsBeen.size()) {
//                    Toast.makeText(ShiGuangZhou.this, "点击", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(ShiGuangZhou.this, CircleDetailActivity.class);
////                    Log.e("ListView 点击事件 确认ID：", " " + aDataDocsBeen.get(index).getUser_id() +"  " + UserParameterUtil.userUtil.getId());
//                    ArrayList<Associates.DataBean.DocsBean> aDataDocsBeen = new ArrayList<>();
//                    aDataDocsBeen.addAll(ShiGuangZhou.this.aDataDocsBeen);
//                    intent.putParcelableArrayListExtra("aDataDocsBeen", aDataDocsBeen);
//                    intent.putExtra("position", index);
//                    skipParameter = 0;
//                    ShiGuangZhou.this.startActivityForResult(intent, index);
//                }
                if (position > 0) {
                    Intent intent = new Intent(ShiGuangZhou.this, CircleDetailActivity.class);
                    ArrayList<Associates.DataBean.DocsBean> aDataDocsBeen = new ArrayList<Associates.DataBean.DocsBean>();
                    aDataDocsBeen.addAll(ShiGuangZhou.this.aDataDocsBeen);
                    intent.putParcelableArrayListExtra("aDataDocsBeen", aDataDocsBeen);
                    intent.putExtra("position", position - 1);
                    skipParameter = 0;

                    Associates.DataBean.DocsBean doc = aDataDocsBeen.get(position - 1);
                    intent.putExtra("aDocsBeen", doc);

                    startActivityForResult(intent, position - 1);
                }
            }
        });
        mRefreshDepartment.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(AbPullToRefreshView view) {
                mRefreshDepartmentType = TYPE_HEADER;
                mPageDepartment = 1;
//                getDataDepartment(mPageDepartment, mPagesize);
                associatesList(0);
            }
        });
        mRefreshDepartment.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(AbPullToRefreshView view) {
                mRefreshDepartmentType = TYPE_FOOTER;
                if (aDataDocsBeen.size() > 0)
                    associatesList(aDataDocsBeen.get(aDataDocsBeen.size() - 1).getId());
                else
                    associatesList(0);
//                getDataDepartment(mPageDepartment, mPagesize);
            }
        });
//        mListView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, NotifyView arg1, int arg2,
//                                    long arg3) {
//                Intent intent = new Intent(CircleMainActivity.this,
//                        CircleDetailActivity.class);
//                intent.putExtra("DynamicInfo", mDatasOrg.get(arg2));
//                startActivity(intent);
//            }
//        });
//        mTextOrg.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView arg0) {
//                mTextOrg.setTextColor(Color.RED);
//                mTextDepartment.setTextColor(Color.BLACK);
//                mRefreshOrg.setVisibility(NotifyView.VISIBLE);
//                mRefreshDepartment.setVisibility(NotifyView.GONE);
//            }
//        });
//        mTextDepartment.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView arg0) {
//                mTextOrg.setTextColor(Color.BLACK);
//                mTextDepartment.setTextColor(Color.RED);
//                mRefreshOrg.setVisibility(NotifyView.GONE);
//                mRefreshDepartment.setVisibility(NotifyView.VISIBLE);
//                if (mDatasDepartment.size() <= 0) {
//                    mRefreshDepartment.headerRefreshing();
//                }
//            }
//        });
//        mTextFollow.setOnClickListener(new NotifyView.OnClickListener() {
//            @Override
//            public void onClick(NotifyView arg0) {
//
//            }
//        });
//        mRefreshOrg.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
//
//            @Override
//            public void onHeaderRefresh(AbPullToRefreshView view) {
//                mRefreshOrgType = TYPE_HEADER;
//                mPageOrg = 1;
//                getDataOrg(mPageOrg, mPagesize);
//            }
//        });
//        mRefreshOrg.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
//
//            @Override
//            public void onFooterLoad(AbPullToRefreshView view) {
//                mRefreshOrgType = TYPE_FOOTER;
//                getDataOrg(mPageOrg, mPagesize);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    /**
     * 同事圈列表
     */
    public void associatesList(final int circleId) {

        System.out.println("url = " + XrjHttpClient.getShiguangzhouListUrl() + UserPrams);

        OkHttpUtils
                .post()
                .url(XrjHttpClient.getShiguangzhouListUrl() + UserPrams)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("id", circleId + "")
                .addParams("direction", "lt")
                //1. id为0或不传 ，返回最新的10条记录
                //2. id = 10, direction = gt 返回id大于10的 10条记录
                //3. id = 20, direction = lt 返回id小于20的10条记录
                .build()
                .execute(new AssociatesCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Log.e("Associates_onError", e.toString());
                                 Toast.makeText(ShiGuangZhou.this, "网络异常—同事圈列表", Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onResponse(Associates response, int id) {
                                 Log.e("AssociatesCallback", response.getError_msg());

                                 switch (response.getError_code()) {
                                     case 0:
                                         TextView main_circle_title = (TextView) findViewById(R.id.main_circle_title);
                                         if (userId == MyApplication.userUtil.getId() || userId == 0)
                                             main_circle_title.setText("我的时光轴");
                                         else
                                             main_circle_title.setText(response.getData().getUser().getTruename() + "的时光轴");

                                         int stars = response.getData().getStars();
                                         circle_head_star_ll.setVisibility(View.VISIBLE);
                                         if (stars > 0 && stars <= 5) {
                                             circle_star_no.setVisibility(View.GONE);
                                             for (int i = 0; i < stars; i++) {
                                                 imageLists.get(i).setVisibility(View.VISIBLE);
                                             }
                                             if (stars <= 5)
                                                 for (int i = stars; i < 5; i++) {
                                                     imageLists.get(i).setVisibility(View.GONE);
                                                 }
                                         } else {
                                             circle_star_no.setVisibility(View.VISIBLE);
                                         }
                                         mTextNum2.setText("" + response.getData().getDocumentary());
                                         mTextNum3.setText("" + response.getData().getComment());
                                         mTextNum4.setText("" + response.getData().getUpvote());
                                         mTextNum5.setText("" + response.getData().getDo_upvote());
//                                         String dmInfo = "负责人 : " + aDataBeen.getDirector().getUsername()
//                                                 + " " + "职位 : " + aDataBeen.getDirector().getJobname()
//                                                 + "\n" + "管理员 : " + aDataBeen.getManager().getUsername()
//                                                 + " " + "职位 : " + aDataBeen.getManager().getJobname();
                                         mTextDmInfo.setText(response.getData().getUser().getTruename() + "   "
                                                 + response.getData().getUser().getDepartment());
//                                         ImageLoader.getInstance().displayImage(aDataBeen.getUlr, mImgDmPhoto);:
                                         ImageLoader.getInstance().displayImage(response.getData().getUser().getAvatar(),
                                                 mImgDmPhoto, MyApplication.options, animateFirstListener);
                                         if (circleId == 0)
                                             aDataDocsBeen.clear();
                                         if (response.getData().getDocs().size() == 0)
                                             Toast.makeText(ShiGuangZhou.this, "没有更多了", Toast.LENGTH_SHORT).show();
                                         aDataDocsBeen.addAll(response.getData().getDocs());
                                         mDepartmentAdapter.notifyDataSetChanged();
                                         mHandler.sendEmptyMessage(1);
                                         break;
                                     default:
                                         break;
                                 }
                             }
                         }
                );
    }

    private abstract class AssociatesCallback extends com.zhy.http.okhttp.callback.Callback<Associates> {
        @Override
        public Associates parseNetworkResponse(Response response, int id) throws Exception {
            String result = response.body().string();
            Log.e("时光轴列表 JSON", result);
            Associates associates = new Gson().fromJson(result, Associates.class);
            return associates;
        }
    }
}
