package com.qingyii.hxt.meeting.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.qingyii.hxt.R;
import com.qingyii.hxt.base.app.EventBusTags;
import com.qingyii.hxt.base.mvp.contract.CommonContract;
import com.qingyii.hxt.base.utils.RxUtils;
import com.qingyii.hxt.base.widget.AutoRadioGroup;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.meeting.di.component.DaggerMeetingSummaryComponent;
import com.qingyii.hxt.meeting.di.module.MeetingSummaryModule;
import com.qingyii.hxt.meeting.di.module.entity.MeetingList;
import com.qingyii.hxt.meeting.di.module.entity.MeetingSummary;
import com.qingyii.hxt.meeting.mvp.presenter.MeetingSummaryPresenter;
import com.qingyii.hxt.meeting.mvp.ui.adapter.PhotoAdapter;
import com.qingyii.hxt.meeting.mvp.ui.adapter.RecyclerItemClickListener;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.qingyii.hxt.meeting.mvp.ui.activity.MeetingDetailsActivity.PARAMS;

/**
 * create by xubo 2017/6/22
 * 会议小结
 */
public class MeetingSummaryActivity extends BaseActivity<MeetingSummaryPresenter> implements CommonContract.MeetingSummaryView {
    @BindView(R.id.summary_rg1)
    AutoRadioGroup summaryRg1;
    @BindView(R.id.summary_rg2)
    AutoRadioGroup summaryRg2;
    @BindView(R.id.summary_rg3)
    AutoRadioGroup summaryRg3;
    @BindView(R.id.summary_rg4)
    AutoRadioGroup summaryRg4;
    private MeetingList.DataBean data;
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.toolbar_back)
    Button toolbarBack;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.meeting_summary_title)
    EditText meetingSummaryTitle;
    @BindView(R.id.meeting_summary_content)
    EditText meetingSummaryContent;
    @BindView(R.id.meeting_summary_type)
    TextView meetingSummaryType;
    @BindView(R.id.meeting_summary_tag)
    TextView meetingSummaryTag;
    @BindView(R.id.meeting_summary_pic_recyclerView)
    RecyclerView recyclerView;
    private Dialog dialog;
    private PhotoAdapter photoAdapter;

    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private List<String> ImgUrls = new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMeetingSummaryComponent.builder()
                .appComponent(appComponent)
                .meetingSummaryModule(new MeetingSummaryModule(this))
                .build()
                .inject(this);
        if (getIntent().hasExtra(PARAMS)) {
            data = getIntent().getParcelableExtra(PARAMS);
        }
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_meeting_summary; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText(R.string.meeting_summary);
        if (data != null) {
            meetingSummaryType.setText(data.getMeetingtype());
            meetingSummaryTag.setText(data.getTag());
        }
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                        PhotoPicker.builder()
                                .setPhotoCount(PhotoAdapter.MAX)
                                .setShowCamera(true)
                                .setPreviewEnabled(false)
                                .setSelected(selectedPhotos)
                                .start(MeetingSummaryActivity.this);
                    } else {
                        PhotoPreview.builder()
                                .setPhotos(selectedPhotos)
                                .setCurrentItem(position)
                                .start(MeetingSummaryActivity.this);
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
            if (requestCode == PhotoPicker.REQUEST_CODE) {

                List<MultipartBody.Part> parts = new ArrayList<>(selectedPhotos.size());
                for (String selectedPhoto : selectedPhotos) {
                    File file = new File(selectedPhoto);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("summary", file.getName(), requestBody);
                    parts.add(part);
                }

                mPresenter.uploadPic(parts, String.valueOf(MyApplication.userUtil.getId()));
            }
        }
    }

    @Override
    public void uploadFinish(List<String> data) {
        this.ImgUrls.addAll(data);
    }

    @Override
    public void submitFinish() {
        Toast.makeText(this, "提交完成", Toast.LENGTH_SHORT).show();
        Message event = new Message();
        event.what = EventBusTags.UPDATE_MEETING_SUMMARY_FINISH;
        EventBus.getDefault().post(event, EventBusTags.MEETING);
        finish();
    }

    @Override
    public void showLoading() {
//        if(dialog.isShowing())return;
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    dialog = StyledDialog.buildLoading("正在提交，请稍等").show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                });
    }

    @Override
    public void hideLoading() {
//        mSwipeRefreshLayout.setRefreshing(false);
        dialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.toolbar_back_layout, R.id.toolbar_right_layout, R.id.summary_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_layout:
                killMyself();
                break;
            case R.id.toolbar_right_layout:
                break;
            case R.id.summary_submit:
                if (RxUtils.isShowEditTextEmptyError(meetingSummaryTitle, getString(R.string.title_can_not_null))
                        || RxUtils.isShowEditTextEmptyError(meetingSummaryContent, getString(R.string.content_can_not_null)))
                    return;
                MeetingSummary sub_data = new MeetingSummary();
                sub_data.setAllow_commentX(getRadioButtonTag(summaryRg1));
                sub_data.setAuditX(getRadioButtonTag(summaryRg2));
                sub_data.setVisibilityX(getRadioButtonTag(summaryRg3));
                sub_data.setShowX(getRadioButtonTag(summaryRg4));
                sub_data.setTitle(meetingSummaryTitle.getText().toString());
                sub_data.setContent(meetingSummaryContent.getText().toString());
                sub_data.setMeeting_id(String.valueOf(data.getId()));
                sub_data.setUser_id(String.valueOf(data.getUser_id()));
                sub_data.setPictureX(ImgUrls.toString());
                mPresenter.requestMeetingLists(sub_data);
                break;
        }
    }

    private Integer getRadioButtonTag(RadioGroup view) {
        RadioButton rb = (RadioButton) findViewById(view.getCheckedRadioButtonId());
        return Integer.parseInt(rb.getTag().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}