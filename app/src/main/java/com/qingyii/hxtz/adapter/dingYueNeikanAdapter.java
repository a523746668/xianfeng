package com.qingyii.hxtz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.httpway.NKUpload;
import com.qingyii.hxtz.pojo.SubscribedNK;
import com.qingyii.hxtz.util.AnimateFirstDisplayListener;
import com.qingyii.hxtz.util.EmptyUtil;

import java.util.List;

public class dingYueNeikanAdapter extends BaseAdapter {
    private Context context;
    private List<SubscribedNK.DataBean> list;
    private Handler handler;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private int positionId;

    private Dialog dialog;
    private View menuContentLayout;
    private TextView neikan_dialog_l;
    private TextView neikan_dialog_r;

    public dingYueNeikanAdapter(Context context, List<SubscribedNK.DataBean> list, Handler handler) {
        super();
        this.context = context;
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dyneikan_item, null);
            holder = new ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.content_dyneikan_item);
            holder.tv_title = (TextView) convertView.findViewById(R.id.name_dyneikan_item);
//		    holder.tv_isFree = (TextView) convertView.findViewById(R.id.tv_ismianfei);
            holder.tv_isTake = (ImageView) convertView.findViewById(R.id.dingyue_dyneikan_item);
            holder.iv_dynk = (ImageView) convertView.findViewById(R.id.iv_Dyneikan_Image);
            holder.tv_dyneikan_name = (TextView) convertView.findViewById(R.id.tv_dyneikan_name);
            holder.tv_ismianfei = (ImageView) convertView.findViewById(R.id.iv_ismianfei);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        final SubscribedNK.DataBean sDataBean = list.get(position);
        holder.tv_content.setText(sDataBean.getMagazinedesc());
        holder.tv_title.setText(sDataBean.getMagazinename());
//        if (EmptyUtil.IsNotEmpty(sDataBean.getSponsor())) {
//            tv_dyneikan_name.setText("主办 ：  " + list.get(position).getSponsor());
//        } else {
//            tv_dyneikan_name.setText("主办 ：  暂无信息");
//        }
        if (EmptyUtil.IsNotEmpty(sDataBean.getOrganizer())) {
            holder.tv_dyneikan_name.setText("主办: " + sDataBean.getOrganizer());
        } else {
            holder.tv_dyneikan_name.setText("主办: 暂无信息");
        }

        if (sDataBean.getIs_subscribe() == 1)
            holder.tv_isTake.setSelected(true);
        else
            holder.tv_isTake.setSelected(false);

//        if (EmptyUtil.IsNotEmpty(list.get(position).getReadflag())) {
//            if ("1".equals(list.get(position).getReadflag())) {
//                tv_ismianfei.setText("公开");
//            } else if ("2".equals(list.get(position).getReadflag())) {
//                tv_ismianfei.setText("专有");
//            }
//        }

        if (sDataBean.getPublishstatus().equals("public"))
            holder.tv_ismianfei.setBackgroundResource(R.mipmap.neikan_gongkai);
        else if (sDataBean.getPublishstatus().equals("share"))
            holder.tv_ismianfei.setBackgroundResource(R.mipmap.neikan_gongxiang);
        else if (sDataBean.getPublishstatus().equals("private"))
            holder.tv_ismianfei.setBackgroundResource(R.mipmap.neikan_zhuanyou);

        ImageLoader.getInstance().displayImage(sDataBean.getMagezinecover(), holder.iv_dynk, MyApplication.options, animateFirstListener);

        holder.tv_isTake.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sDataBean.getIs_subscribe() == 0) {
                    positionId = position;
                    dialogShow();
                }
            }
        });

//        ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + m.getPicaddress(), iv_dynk, MyApplication.options, animateFirstListener);
//        tv_isTake.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyView v) {
//                positionId = position;
//                dialogShow();
//            }
//        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
        TextView tv_title;
        TextView tv_isFree;
        ImageView tv_isTake;
        ImageView iv_dynk;
        TextView tv_dyneikan_name;
        ImageView tv_ismianfei;
    }

    private void dialogShow() {
        dialog = new Dialog(context, R.style.DialogStyle);

        menuContentLayout = View.inflate(context, R.layout.neikan_dialog, null);
        neikan_dialog_l = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_l);
        neikan_dialog_r = (TextView) menuContentLayout.findViewById(R.id.neikan_dialog_r);
        neikan_dialog_l.setText("取消");
        neikan_dialog_r.setText("订阅");
        neikan_dialog_l.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        neikan_dialog_r.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NKUpload nkUpload = NKUpload.getNKUpload();
                nkUpload.SubscribedCZ(context, 1, dialog, positionId, list, handler);
            }
        });

        dialog.setContentView(menuContentLayout);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

//        new AlertDialog.Builder(context).setTitle("提示").setIcon(R.mipmap.ic_launcher).setPositiveButton("确定",
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        Dyneikan();
//                        NKUpload nkUpload = NKUpload.getNKUpload();
//                        nkUpload.SubscribedCZ(context, 1, positionId, list, handler);
//                        handler.sendEmptyMessage(1);
//                        dingYueNeikanAdapter.this.notifyDataSetChanged();
//
//                    }
//                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        }).setMessage("确定订阅此杂志？").show();
    }

    /**
     * 订阅刊物接口
     */
//    private void Dyneikan() {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            jsonObj.put("magazineid", list.get(positionId).getMagazineid() + "");
//            jsonObj.put("userid", CacheUtil.userid + "");
//            byte[] bytes;
//
//            ByteArrayEntity entity = null;
//            // 处理保存数据中文乱码问题
//            try {
//                bytes = jsonObj.toString().getBytes("utf-8");
//                entity = new ByteArrayEntity(bytes);
//
//                Log.e("dingyue_订阅内刊_out", jsonObj.toString());
//
//                YzyHttpClient.post(context, HttpUrlConfig.dymagazine,
//                        entity, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, String content) {
//
//                                Log.e("dingyue_订阅内刊_in", content);
//
//                                super.onSuccess(statusCode, content);
//                                if (statusCode == 499) {
//                                    Toast.makeText(context, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(context, LoginActivity.class);
//                                    context.startActivity(intent);
//                                    onFinish();
//                                } else if (statusCode == 200) {
//                                    Gson gson = new Gson();
//                                    try {
//
//
//                                        JSONObject Obj = new JSONObject(content);
//
//                                        //广播通知 :刷新确认订单界面UI
//                                        Intent intent = new Intent();
//                                        intent.setAction("action.neikan");
//                                        context.sendBroadcast(intent);
//                                        Toast.makeText(context, "订阅成功", Toast.LENGTH_SHORT).show();
//                                        {
//
////									for (int i = 0; i < mlist.length(); i++) {
////                                        Magazine m = gson.fromJson(mlist.getString(i), Magazine.class);
////                                        list.add(m);}
//                                        }
//
//                                    } catch (JSONException e) {
//
//                                        e.printStackTrace();
//                                    }
//
//
//                                }
//                            }
//
//                        });
//            } catch (UnsupportedEncodingException e) {
////				 handler.sendEmptyMessage(0);
//                e.printStackTrace();
//            }
//
//
//        } catch (JSONException e) {
////		     handler.sendEmptyMessage(0);
//            e.printStackTrace();
//        }
//    }
}
