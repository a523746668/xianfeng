package com.qingyii.hxt.circle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andbase.library.app.base.AbBaseActivity;
import com.mingle.widget.ShapeLoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.CircleReportWebActivity;
import com.qingyii.hxt.Constant;
import com.qingyii.hxt.R;
import com.qingyii.hxt.adapter.CircleDetailAdapter;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.httpway.TSQUpload;
import com.qingyii.hxt.pojo.Associates;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 动态圈详情页
 */
public class CircleDetailActivity extends AbBaseActivity implements View.OnClickListener {
    Intent intent = null;

    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_FOOTER = 0x0002;

    private ImageView mImgLeft;

    private ImageView imgHead;
    private TextView textName;
    private TextView textDepartment;
    private TextView textCategory;
    private TextView textContent;
    private TextView textDelete;
    private TextView textReport;
    private TextView textLikenum;
    private ListView gridPhoto;
    private PhotoAdapter2 photoAdapter;
    private ArrayList<String> photos;
    private TextView textTime;
    private TextView textLike;

    private TextView mTextSend;
    private EditText mEditContent;
    ;

//    private int mRefreshType;
//    private int mPageComment = 1;
//    private int mPageSize = 10;

    //    private DynamicInfo mInfo;
    private Associates.DataBean.DocsBean aDocsBean;
    private List<Associates.DataBean.DocsBean> aDataDocsBeen;
    // 传递质位置参数
    private int aDocsBeanPosition;

    //    private AbPullToRefreshView mRefreshView;
    private ListView mListView;
    //    private ArrayList<Comment> mListComment;
    private CircleDetailAdapter mDetailAdapter;
    private int contentPosition = -1;

    /**
     * AbLoadDialogFragment 失效修改为 AbProgressDialogFragment
     */
//    private AbLoadDialogFragment mDialogFragment = null;
//    private AbProgressDialogFragment mDialogFragment = null;
    private ShapeLoadingDialog shapeLoadingDialog;

    private TSQUpload tsqUpload = TSQUpload.getTSQUpload();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private Handler mHandler = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (shapeLoadingDialog != null) {
                shapeLoadingDialog.dismiss();
            }
//                if (mDialogFragment != null) {
//                    mDialogFragment.dismiss();
//                }
            mDetailAdapter.notifyDataSetChanged();
            photoAdapter.notifyDataSetChanged();

            // 回传参数
            intent = new Intent();
            intent.putExtra("aDocsBeanResult", aDocsBean);

            switch (msg.what) {
                case 0:
                    //回传注入
                    CircleDetailActivity.this.setResult(RESULT_OK, intent);
                    break;
                case 1:
                    intent.putExtra("operation", 1);
                    //回传注入
                    CircleDetailActivity.this.setResult(RESULT_OK, intent);
                    mEditContent.clearFocus();
                    mEditContent.setText("");
                    break;
                case 2:
                    intent.putExtra("operation", 2);
                    //回传注入
                    CircleDetailActivity.this.setResult(RESULT_OK, intent);
                    // 点赞
//                    textLike.setText(aDocsBean.getUpvote() + "人点赞");
//                    Drawable drawable = getResources().getDrawable(
//                            R.mipmap.ic_circle_biglove_selected);
//                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
//                            drawable.getMinimumHeight());
//                    textLike.setCompoundDrawables(null, drawable, null, null);
                    break;
                case 3:
                    intent.putExtra("operation", 3);
                    //回传注入
                    CircleDetailActivity.this.setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 4:
                    //回传注入
                    CircleDetailActivity.this.setResult(RESULT_OK, intent);
                    break;
                case 5:
                    // Toast.makeText(CircleDetailActivity.this, "已是最新数据", 0)
                    // .show();
                    break;
                case Constant.MSG_DELCOMMENT:
                    if (msg.arg2 == 0) {
                        delCommentLocal(mDeleteCommentId);
                    } else {
                        Toast.makeText(CircleDetailActivity.this, "删除失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constant.MSG_DELDYNAMIC:
                    if (msg.arg2 == 0) {
                        Toast.makeText(CircleDetailActivity.this, "删除成功",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CircleDetailActivity.this, "删除失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            initData();
//            mRefreshView.onHeaderRefreshFinish();
//            mRefreshView.onFooterLoadFinish();
            mDeleteCommentId = 0;
            return false;
        }
    });

    //    private Comment mSelectComment = null;
    private int mDeleteCommentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        aDataDocsBeen = intent.getParcelableArrayListExtra("aDataDocsBeen");
        //aDocsBeanPosition = intent.getIntExtra("position", 0);
        //aDocsBean = aDataDocsBeen.get(aDocsBeanPosition);

        aDocsBean = intent.getParcelableExtra("aDocsBeen");

//        Log.e("确认ID是否跟我的账号一直：", " " + aDocsBean.getUser_id() +"  " + UserParameterUtil.userUtil.getId());
        if (aDocsBean == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_circle_detail);
        findView();
        initData();
//        setListener();
    }

    private void delCommentLocal(int deleteCommentId) {
        for (int i = 0; i < aDocsBean.getComments().size(); i++) {
            if (aDocsBean.getComments().get(i).getId() == deleteCommentId) {
                aDocsBean.getComments().remove(i);
                mDetailAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void findView() {
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        View viewHeader = View.inflate(CircleDetailActivity.this, R.layout.item_circle_detail_head, null);
        viewHeader.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
//                  (int) (wm
//                  .getDefaultDisplay
//                  ().getWidth() * 0.5f))
                AbsListView.LayoutParams.MATCH_PARENT));
        mImgLeft = (ImageView) findViewById(R.id.circle_detail_left);

        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        imgHead = (ImageView) viewHeader.findViewById(R.id.circle_detail_header);
        textCategory = (TextView) viewHeader.findViewById(R.id.circle_detail_category);
        textName = (TextView) viewHeader.findViewById(R.id.circle_detail_name);
        textDepartment = (TextView) viewHeader.findViewById(R.id.circle_detail_department);
        textContent = (TextView) viewHeader.findViewById(R.id.circle_detail_content);
        textDelete = (TextView) viewHeader.findViewById(R.id.circle_detail_delete);
        textReport = (TextView) viewHeader.findViewById(R.id.circle_detail_report);

        textTime = (TextView) viewHeader.findViewById(R.id.circle_detail_time);
        textLike = (TextView) viewHeader.findViewById(R.id.circle_detail_like);
        textLikenum = (TextView) viewHeader.findViewById(R.id.circle_detail_like_num);
        gridPhoto = (ListView) viewHeader.findViewById(R.id.circle_detail_photo);
        photos = new ArrayList<String>();
        photoAdapter = new PhotoAdapter2(CircleDetailActivity.this, photos);
        photoAdapter.setScaleType(ImageView.ScaleType.FIT_CENTER);
        gridPhoto.setAdapter(photoAdapter);

//        mRefreshView = (AbPullToRefreshView) findViewById(R.id.circle_detail_refresh);
        mListView = (ListView) findViewById(R.id.circle_detail_list);
        mListView.addHeaderView(viewHeader);

        mTextSend = (TextView) findViewById(R.id.circle_detail_send);
        mEditContent = (EditText) findViewById(R.id.circle_detail_edit);

        //图片只需要加载一次
        for (int i = 0; i < aDocsBean.getPicture().size(); i++)
            photos.add(aDocsBean.getPicture().get(i).getUri());
    }

    //年月日选择器
    private Calendar calendar = Calendar.getInstance();
    //时间
    final int YEAR = calendar.get(Calendar.YEAR), MONTH = calendar.get(Calendar.MONTH) + 1;

    private void initData() {
        mTextSend.setOnClickListener(this);
        /**
         * 用户信息
         */
//        User createUser = mInfo.getCreateuser();
        if (aDocsBean.getUser_id() == MyApplication.userUtil.getId() && (DateUtils.getStringToLong2Date(aDocsBean.getCreated_at()) > DateUtils.getStringToShortDate(YEAR + "-" + MONTH))) {
            textDelete.setVisibility(View.VISIBLE);
            textReport.setVisibility(View.GONE);
            textDelete.setOnClickListener(this);
        } else {
            textDelete.setVisibility(View.GONE);
            textReport.setVisibility(View.VISIBLE);
            textReport.setOnClickListener(this);
        }

//        Drawable drawable = null;
        /**
         * 判断是否点过赞
         */
//        if (aCommentsBean.getIsloved() == 0) {
//        drawable = getResources().getDrawable(
//                R.mipmap.ic_circle_biglove_normal);
//        } else {
//            drawable = getResources().getDrawable(
//                    R.mipmap.ic_circle_biglove_selected);
//        }
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
//                drawable.getMinimumHeight());
        if (aDocsBean.getIs_upvote() == 1)
            textLike.setSelected(true);
        else
            textLike.setOnClickListener(this);
        textLikenum.setText(aDocsBean.getUpvote() + " 人点赞");
        textName.setText("" + aDocsBean.getUsername());
        textDepartment.setText(aDocsBean.getDepartment());
        textContent.setText("" + aDocsBean.getContent());
        textTime.setText(aDocsBean.getCreated_at());
        textCategory.setText("类型：" + aDocsBean.getDoctypename());

        ImageLoader.getInstance().displayImage(aDocsBean.getAvatar(), imgHead, MyApplication.options, animateFirstListener);
//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + createUser.getPicaddress(), imgHead);
//        textDepartment.setText(createUser.getDepname() == null ? "" : ("" + createUser.getDepname()));
//        textTime.setText("" + AbDateUtil.getStringByFormat(mInfo.getCreatetime(),
//                AbDateUtil.dateFormatMD + " "
//                        + AbDateUtil.dateFormatHMS));

//        String[] images = mInfo.getContentimageurllist();
//        if (images != null) {
//            for (String str : images) {
//                photos.add(str);
//            }
//            photoAdapter.notifyDataSetChanged();
//        }
//        mListComment = new ArrayList<Comment>();
        List<Associates.DataBean.DocsBean.CommentsBean> comments = aDocsBean.getComments();
        mDetailAdapter = new CircleDetailAdapter(this, comments);
        mListView.setAdapter(mDetailAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mEditContent.setHint("回复 " + aDocsBean.getComments().get(position - 1).getAuthor() + "：");
                    contentPosition = position - 1;
                } else {
                    mEditContent.setHint("发表评论");
                    contentPosition = -1;
                }
            }
        });
//        if (comments != null && comments.size() > 0) {
//            for (int i = 0; i < comments.size(); i++) {
//                mListComment.add(comments.get(i));
//            }
//            mDetailAdapter.notifyDataSetChanged();
//        }
//        textLike.setText(aDocsBean.getUpvote() + "人点赞");
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);

        View menuContentLayout = View.inflate(this, R.layout.neikan_dialog, null);
        TextView neikan_dialog_context = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_context);
        TextView neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
        TextView neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);

        switch (v.getId()) {
            case R.id.circle_detail_send:
                if (!mEditContent.getText().toString().equals("")) {
                    tsqUpload.associatesComment(CircleDetailActivity.this, aDocsBean,
                            contentPosition, mEditContent.getText().toString(), null, mHandler);
                } else
                    Toast.makeText(CircleDetailActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
                break;
            case R.id.circle_detail_like:
                if (aDocsBean.getIs_upvote() == 0) {
                    neikan_dialog_context.setVisibility(View.VISIBLE);
                    neikan_dialog_context.setText("确定要点赞吗");
                    neikan_dialog_l.setText("取消");
                    neikan_dialog_r.setText("点赞");
                    neikan_dialog_l.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    neikan_dialog_r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tsqUpload.associatesLike(CircleDetailActivity.this, aDocsBean, dialog, textLike, mHandler);
                            shapeLoadingDialog.setLoadingText("正在戳对方,请等一小会");
                            shapeLoadingDialog.show();
                        }
                    });

                    dialog.setContentView(menuContentLayout);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();
                }
                break;
            case R.id.circle_detail_delete:
                neikan_dialog_context.setVisibility(View.VISIBLE);
                neikan_dialog_context.setText("确定要删除吗");
                neikan_dialog_l.setText("取消");
                neikan_dialog_r.setText("删除");
                neikan_dialog_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                neikan_dialog_r.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tsqUpload.associatesDelete(CircleDetailActivity.this, aDataDocsBeen, aDocsBeanPosition, dialog, mHandler);
                        shapeLoadingDialog.setLoadingText("上传中,请等一小会");
                        shapeLoadingDialog.show();
                    }
                });

                dialog.setContentView(menuContentLayout);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();

                break;
            case R.id.circle_detail_report:
                intent = new Intent(CircleDetailActivity.this, CircleReportWebActivity.class);
                intent.putExtra("DynamicInfo", aDocsBean);
                CircleDetailActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
