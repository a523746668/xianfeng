package com.qingyii.hxtz.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.ReportListActivity;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.GLUpload;
import com.qingyii.hxtz.pojo.WaitAffirmList;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;

import java.util.List;

/**
 * Created by XRJ on 16/9/22.
 */

public class ReportListAdapter extends BaseAdapter {
    private ReportListActivity reportListActivity;
    private List<WaitAffirmList.DataBean> wDataBeanList;
    private Handler handler;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private GLUpload glUpload = GLUpload.getGLUpload();
    //弹窗
    private Dialog dialog;
    //确认弹窗
    private View affirmContentLayout;
    private TextView affirm_cancel;
    private TextView affirm_submit;

    public ReportListAdapter(ReportListActivity reportListActivity, List<WaitAffirmList.DataBean> wDataBeanList, Handler handler) {
        this.reportListActivity = reportListActivity;
        this.wDataBeanList = wDataBeanList;
        this.handler = handler;
        //弹窗设置
        dialog = new Dialog(reportListActivity, R.style.ActionSheetDialogStyle);
    }

    @Override
    public int getCount() {
        return wDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return wDataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = convertView.inflate(reportListActivity, R.layout.report_list_item, null);
            holder = new ViewHolder();
            holder.report_list_item_image = (ImageView) convertView.findViewById(R.id.report_list_item_image);
            holder.report_list_item_class = (TextView) convertView.findViewById(R.id.report_list_item_class);
            holder.report_list_item_time = (TextView) convertView.findViewById(R.id.report_list_item_time);
            holder.report_list_item_context = (TextView) convertView.findViewById(R.id.report_list_item_context);
            holder.report_list_item_integral = (TextView) convertView.findViewById(R.id.report_list_item_integral);
            holder.report_list_item_affirm = (TextView) convertView.findViewById(R.id.report_list_item_affirm);
            holder.report_list_item_appeal = (TextView) convertView.findViewById(R.id.report_list_item_appeal);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            final WaitAffirmList.DataBean wDataBean = wDataBeanList.get(position);

            if (wDataBeanList.get(position).getPicture().size() > 0)
                ImageLoader.getInstance().displayImage(wDataBeanList.get(position).getPicture().get(0).getUri(),
                        holder.report_list_item_image, MyApplication.options, animateFirstListener);

            holder.report_list_item_class.setText("类型：" + wDataBean.getDoctypename() + "    ");
            holder.report_list_item_time.setText("时间：" + wDataBean.getCreated_at());
            holder.report_list_item_context.setText("" + wDataBean.getContent());

            List<WaitAffirmList.DataBean.ChecklogsBean> wChecklogsBea = wDataBean.getChecklogs();

            //积分颜色判断
            double score;
            //审核记录可能为null
            if (wChecklogsBea.size() > 0)
                score = wChecklogsBea.get(wChecklogsBea.size() - 1).getScore();
            else
                score = 0;

            if (score < 0) {
                holder.report_list_item_integral.setText("积分 " + score);
                holder.report_list_item_integral.setTextColor(Color.GREEN);
            } else if (score > 0) {
                holder.report_list_item_integral.setText("积分 +" + score);
                holder.report_list_item_integral.setTextColor(Color.RED);
            } else {
                holder.report_list_item_integral.setText("积分  " + score);
                holder.report_list_item_integral.setTextColor(Color.BLACK);
            }

            switch (wDataBean.getStatus()) {
                case 0:
                    holder.report_list_item_affirm.setTextColor(Color.parseColor("#E66768"));
                    holder.report_list_item_affirm.setSelected(false);
                    holder.report_list_item_affirm.setText("待审核");
                    holder.report_list_item_affirm.setClickable(false);
                    break;
                case 1:
                    holder.report_list_item_affirm.setTextColor(Color.parseColor("#E66768"));
                    holder.report_list_item_affirm.setSelected(false);
                    holder.report_list_item_affirm.setText("申诉中");
                    holder.report_list_item_affirm.setClickable(false);
                    break;
                case 2:
                    holder.report_list_item_affirm.setTextColor(Color.parseColor("#E66768"));
                    holder.report_list_item_affirm.setSelected(false);
                    holder.report_list_item_affirm.setText("确认");
                    holder.report_list_item_affirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {  //列表确认
                            //Log.e("现在又是什么状态", position + "   " + wDataBean.getStatus() + "");
                            affirmContentLayout = View.inflate(reportListActivity, R.layout.user_context_affirm_menu, null);
                            affirm_cancel = (TextView) affirmContentLayout.findViewById(R.id.affirm_cancel);
                            affirm_submit = (TextView) affirmContentLayout.findViewById(R.id.affirm_submit);
                            affirm_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {//确认取消
                                    dialog.dismiss();
                                }
                            });
                            affirm_submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {//确认提交
                                    //submitUpload(dialog, wDataBean.getId());
                                    glUpload.submitListUpload(reportListActivity, ReportListAdapter.this, holder.report_list_item_affirm, dialog, wDataBean.getId(), handler);
                                }
                            });
                            //将布局设置给Dialog
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            } else {
                                dialog.setContentView(affirmContentLayout);
//                        dialog.getWindow().setGravity(Gravity.BOTTOM);
                                //获得屏幕看都，并传给dialog
                                dialog.getWindow().getAttributes().width = (int) (reportListActivity.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
                                dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                                dialog.show();
                            }
                        }
                    });
                    break;
                case 3:
                    holder.report_list_item_affirm.setTextColor(Color.parseColor("#24C280"));
                    holder.report_list_item_affirm.setSelected(true);
                    holder.report_list_item_affirm.setText("已确认");
                    holder.report_list_item_affirm.setClickable(false);
                    break;
            }
            holder.report_list_item_affirm.setVisibility(View.VISIBLE);

            if (wDataBean.getStatus() == 2)
                holder.report_list_item_appeal.setVisibility(View.VISIBLE);
            else
                holder.report_list_item_appeal.setVisibility(View.GONE);

        } catch (Exception e) {
            Log.e("ReportListAdapter", e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView report_list_item_image;
        TextView report_list_item_class;
        TextView report_list_item_time;
        TextView report_list_item_context;
        TextView report_list_item_integral;
        TextView report_list_item_appeal;
        TextView report_list_item_affirm;
    }

    /**
     * 确认审核
     */
//    public void submitUpload(final Dialog dialog, int docID) {
//
//        Log.e("Submit_docID", docID+"");
//
//        OkHttpUtils
//                .post()
//                .url(XrjHttpClient.getManageListUrl() + "/confirm")
//                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
//                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
//                .addParams("doc_id", docID + "")
//                .build()
//                .execute(new HandleParameterCallback() {
//                             @Override
//                             public void onError(Call call, Exception e, int id) {
//                                 Log.e("Submit_onError", e.toString());
//                                 Toast.makeText(reportListActivity, "网络异常,请检查网络", Toast.LENGTH_LONG).show();
//                             }
//
//                             @Override
//                             public void onResponse(HandleParameter response, int id) {
//                                 Log.e("SubmitCallback", response.getError_msg());
//                                 switch (response.getError_code()) {
//                                     case 0:
//                                         Toast.makeText(reportListActivity, "已确认，不可更改", Toast.LENGTH_LONG).show();
//                                         dialog.dismiss();
//                                         reportListActivity.getDateList();
//                                         break;
//                                     default:
//                                         break;
//                                 }
//                             }
//                         }
//                );
//    }
}