package com.qingyii.hxt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.LoginActivity;
import com.qingyii.hxt.R;
import com.qingyii.hxt.database.DianzanTubiao;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.Book;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.pojo.Comment;
import com.qingyii.hxt.pojo.Discuss;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.view.RoundedImageView;
import com.qingyii.hxt.xiepinglunActivity;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PingLunAdapter extends BaseAdapter {
    private Context context;
    private List<Comment.DataBean> list;
    //    int posionId;
    private BooksParameter.DataBean book;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public PingLunAdapter(Context context, List<Comment.DataBean> list, BooksParameter.DataBean book) {
        super();
        this.context = context;
        this.list = list;
        this.book = book;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
//		 posionId=position;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        OthershujiPingLunAdapter adapter;
//        ArrayList<Discuss> list2 = new ArrayList<Discuss>();
        final Comment.DataBean cDataBean = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.shujixq_pinglun_item, null);
        RoundedImageView iv_shujixq_pl_imageUrl = (RoundedImageView) convertView.findViewById(R.id.iv_shujixq_pl_imageUrl);
        TextView tv_shujixq_pl_name = (TextView) convertView.findViewById(R.id.tv_shujixq_pl_name);
        TextView tv_shujixq_pl_time = (TextView) convertView.findViewById(R.id.tv_shujixq_pl_time);
        TextView tv_shujixq_pl_content = (TextView) convertView.findViewById(R.id.tv_shujixq_pl_content);

        final TextView tv_shujixq_pl_dianzanshu = (TextView) convertView.findViewById(R.id.tv_shujixq_pl_dianzanshu);
        ImageView iv_shujixq_pl_pinglun = (ImageView) convertView.findViewById(R.id.iv_shujixq_pl_pinglun);
        ImageView iv_shujixqblack_pl_dianzanimage = (ImageView) convertView.findViewById(R.id.iv_shujixqblack_pl_dianzanimage);
        ImageView iv_shujixqliang_pl_dianzanimage = (ImageView) convertView.findViewById(R.id.iv_shujixqliang_pl_dianzanimage);

        tv_shujixq_pl_name.setText(cDataBean.getAuthor() + "");
        tv_shujixq_pl_time.setText(cDataBean.getCreated_at() + "");
        tv_shujixq_pl_content.setText(cDataBean.getContent() + "");
        ImageLoader.getInstance().displayImage(cDataBean.getAvatar(), iv_shujixq_pl_imageUrl, MyApplication.options, animateFirstListener);

//        ListView mlistview = (ListView) convertView.findViewById(R.id.list_shujixq_pl_mlistview);

//        tv_shujixq_pl_name.setText(cDataBean.getUsername());
//        tv_shujixq_pl_time.setText(cDataBean.getCreatetimeStr());
//
//
//        //头像
//        if (EmptyUtil.IsNotEmpty(cDataBean.getPicaddress())) {
//            ImageLoader.getInstance().displayImage(HttpUrlConfig.photoDir + cDataBean.getPicaddress(), iv_shujixq_pl_imageUrl, MyApplication.options);
//        } else {
//            iv_shujixq_pl_imageUrl.setBackgroundResource(R.mipmap.haimianbb);
//        }
//
//        if (EmptyUtil.IsNotEmpty(cDataBean.getPraisecount())) {
//            tv_shujixq_pl_dianzanshu.setText(cDataBean.getPraisecount());
//        } else {
//            tv_shujixq_pl_dianzanshu.setText("0");
//        }
//
//
//        if (DianzanTubiao.pinglunChaXun(Integer.parseInt(cDataBean.getDiscussid()))) {
//            cDataBean.setFlag(1);
//        } else {
//            cDataBean.setFlag(0);
//        }
//
//
//        if (cDataBean.getFlag() == 1) {
//            iv_shujixqblack_pl_dianzanimage.setVisibility(NotifyView.GONE);
//            iv_shujixqliang_pl_dianzanimage.setVisibility(NotifyView.VISIBLE);
//        } else {
//            iv_shujixqblack_pl_dianzanimage.setVisibility(NotifyView.VISIBLE);
//            iv_shujixqliang_pl_dianzanimage.setVisibility(NotifyView.GONE);
//        }
//
//
////        adapter = new OthershujiPingLunAdapter(context, list2);
////        mlistview.setAdapter(adapter);
//
//
//        iv_shujixq_pl_pinglun.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyView v) {
//                // TODO Auto-generated method stub
////				Intent intent = new Intent(context, xiepinglunActivity.class);
////				intent.putExtra("dinfo", list.get(position));
////				context.startActivity(intent);
//                Intent intent = new Intent(context, xiepinglunActivity.class);
//                intent.putExtra("comingType", 1);
//                intent.putExtra("book", book);
//                intent.putExtra("parentid", Integer.parseInt(cDataBean.getDiscussid()));
//                context.startActivity(intent);
//            }
//        });
//
//
////		ImageLoader.getInstance().displayImage(sinfo.getShujixqinfo_imageUrl(),iv_shujixq_pl_imageUrl);
//
//        /**
//         * 点赞灰色图标
//         */
//        iv_shujixqblack_pl_dianzanimage.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyView v) {
//
//                if (cDataBean.getFlag() == 0) {
//                    dianzanshu(position);
//                } else if (cDataBean.getFlag() == 1) {
//                    Toast.makeText(context, "您已经为该评论点赞过了！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        /**
//         * 点赞红色图标
//         */
//        iv_shujixqliang_pl_dianzanimage.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(NotifyView v) {
//
//                if (cDataBean.getFlag() == 0) {
//                    dianzanshu(position);
//                } else if (cDataBean.getFlag() == 1) {
//                    Toast.makeText(context, "您已经为该评论点赞过了！", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
        return convertView;
    }


    /**
     * 书籍评论点赞接口
     */
//    private void dianzanshu(final int position) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("discussid", list.get(position).getDiscussid());
//            jsonObject.put("userid", CacheUtil.userid);
//            jsonObject.put("praisecount", 1);
//
//            byte[] bytes;
//            ByteArrayEntity arrayEntity = null;
//            try {
//                bytes = jsonObject.toString().getBytes("utf-8");
//                arrayEntity = new ByteArrayEntity(bytes);
//                YzyHttpClient.post(context, HttpUrlConfig.pinglundianzan, arrayEntity, new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onSuccess(int statusCode, String content) {
//                        // TODO Auto-generated method stub
//                        super.onSuccess(statusCode, content);
//                        if (statusCode == 499) {
//                            Toast.makeText(context, CacheUtil.logout, Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            context.startActivity(intent);
//                            onFinish();
//                        } else if (statusCode == 200) {
//                            try {
//                                JSONObject object = new JSONObject(content);
//                                String msg = object.getString("msg");
//                                String message = object.getString("message");
//                                if (("prais_success").equals(message)) {
//                                    DianzanTubiao.pinglunadd(list.get(position).getDiscussid());
//                                    int a = Integer.parseInt(list.get(position).getPraisecount()) + 1;
//                                    list.get(position).setPraisecount(a + "");
//                                    notifyDataSetChanged();
//                                } else {
//                                    if (("您已经为该评论点赞过了！").equals(msg)) {
//                                        list.get(position).setFlag(1);
//                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                                        DianzanTubiao.pinglunadd(list.get(position).getDiscussid());
//                                        notifyDataSetChanged();
//                                    } else {
//                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                                    }
//                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (JSONException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }
//
//                });
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }


//    private void dianzanshu() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("praisecount", 1);
//            jsonObject.put("discussid", list.get(posionId).getDiscussid());
//            byte[] bytes;
//            ByteArrayEntity byteArrayEntity = null;
//            try {
//                bytes = jsonObject.toString().getBytes("utf-8");
//                byteArrayEntity = new ByteArrayEntity(bytes);
//                YzyHttpClient.post(context, HttpUrlConfig.addXianhua, byteArrayEntity, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String content) {
//                        // TODO Auto-generated method stub
//                        super.onSuccess(statusCode, content);
//                        if (statusCode == 200) {
//                            Gson gson = new Gson();
//
//                            try {
//                                JSONObject jsonObject = new JSONObject(content);
//
//                            } catch (JSONException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable error, String content) {
//                        super.onFailure(error, content);
//                        System.out.println(content);
//                    }
//                });
//
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
