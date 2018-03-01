package com.qingyii.hxt.circle;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.andbase.library.app.base.AbBaseActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qingyii.hxt.R;
import com.qingyii.hxt.bean.Comment;
import com.qingyii.hxt.bean.DynamicInfo;
import com.qingyii.hxt.http.CacheUtil;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.YzyHttpClient;
import com.qingyii.hxt.pojo.User;
import com.qingyii.hxt.util.EmptyUtil;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.qingyii.hxt.AbActivity;

//public class CircleMyActivity extends AbBaseActivity {
//    private ImageLoader mImageLoader;
//    private CircleAdapter mCircleAdapter;
//    private View mViewHeader;
//    private int page = 1;
//    private int pagesize = 10;
//
//    private ArrayList<DynamicInfo> mDatas = new ArrayList<DynamicInfo>();
//    private ListView mListView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_circle_my);
//        findView();
//        this.getData(0, 50, 0);
//    }
//
//    private void findView() {
//        this.mViewHeader = View.inflate(this, R.layout.item_circle_department_head, null);
//        this.mListView = (ListView) this.findViewById(R.id.circle_my_list);
//        mCircleAdapter = new CircleAdapter();
//        this.mListView.setAdapter(mCircleAdapter);
//        this.mListView.addHeaderView(this.mViewHeader);
//    }
//
//    public void getData(int mypage, int mypagesize, int type) {
//        JSONObject jsonobject = new JSONObject();
//        try {
//            jsonobject.put("page", mypage);
//            jsonobject.put("pagesize", mypagesize);
//            jsonobject.put("userid", CacheUtil.userid + "");
//            jsonobject.put("type", type);
//            // if(mytype>0){
//            // jsonobject.put("type", mytype);
//            // }
//            byte[] bytes = jsonobject.toString().getBytes("utf-8");
//            ByteArrayEntity entity = new ByteArrayEntity(bytes);
//            YzyHttpClient.post(this, HttpUrlConfig.reqDynamicInfoList, entity,
//                    new AsyncHttpResponseHandler() {
//
//                        @Override
//                        public void onSuccess(int statusCode, String content) {
//                            if (statusCode == 499) {
//
//                            } else if (statusCode == 200) {
//                                Gson gson = new Gson();
//                                try {
//                                    JSONObject js = new JSONObject(content);
//                                    String a = js.getString("rows");
//                                    System.out.println(a);
//                                    if (EmptyUtil.IsNotEmpty(js
//                                            .getString("rows"))) {
//
//                                        JSONArray lists = js
//                                                .getJSONArray("rows");
//
//                                        if (lists.length() <= 0) {
//                                            // handler.sendEmptyMessage(5);
//                                        } else {
//                                            for (int i = 0; i < lists.length(); i++) {
//                                                // Examination gg =
//                                                // gson.fromJson(lists.getString(i),Examination.class);
//                                                // myList.add(gg);
//                                            }
//                                            // .sendEmptyMessage(1);
//                                        }
//
//                                    } else {
//                                        // handler.sendEmptyMessage(5);
//                                    }
//
//                                } catch (JSONException e) {
//                                    // handler.sendEmptyMessage(0);
//                                    e.printStackTrace();
//                                }
//                            }
//                            super.onSuccess(statusCode, content);
//                        }
//
//                        @Override
//                        public void onFailure(Throwable error, String content) {
//                            super.onFailure(error, content);
//                            // System.out.println(content);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            super.onFinish();
//                            // System.out.println("finish");
//                        }
//
//                    });
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//    }
//
//    class CircleAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return CircleMyActivity.this.mDatas.size();
//        }
//
//        @Override
//        public Object getItem(int arg0) {
//            return CircleMyActivity.this.mDatas.get(arg0);
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        @Override
//        public NotifyListView getView(int arg0, NotifyListView arg1, ViewGroup arg2) {
//            ViewHolder holder;
//            if (arg1 == null) {
//                arg1 = View.inflate(CircleMyActivity.this,
//                        R.layout.item_circle_main, null);
//                holder = new ViewHolder();
//                holder.imgHead = (ImageView) arg1
//                        .findViewById(R.id.circle_main_header);
//                holder.textCategory = (TextView) arg1
//                        .findViewById(R.id.circle_main_category);
//                holder.textName = (TextView) arg1
//                        .findViewById(R.id.circle_main_name);
//                holder.textDepartment = (TextView) arg1
//                        .findViewById(R.id.circle_main_department);
//                holder.textContent = (TextView) arg1
//                        .findViewById(R.id.circle_main_content);
//                holder.textTime = (TextView) arg1
//                        .findViewById(R.id.circle_main_time);
//                holder.textLike = (TextView) arg1
//                        .findViewById(R.id.circle_main_like);
//                holder.textComment = (TextView) arg1
//                        .findViewById(R.id.circle_main_comment);
//                holder.textComment1 = (TextView) arg1
//                        .findViewById(R.id.circle_main_comment1);
//                holder.textComment2 = (TextView) arg1
//                        .findViewById(R.id.circle_main_comment2);
//                holder.textComment3 = (TextView) arg1
//                        .findViewById(R.id.circle_main_comment3);
//                holder.textMore = (TextView) arg1
//                        .findViewById(R.id.circle_main_more);
//                holder.gridPhoto = (NonScrollGridView) arg1
//                        .findViewById(R.id.circle_main_photo);
//                holder.photos = new ArrayList<String>();
//                holder.photoAdapter = new PhotoAdapter(holder.photos);
//                holder.gridPhoto.setAdapter(holder.photoAdapter);
//                arg1.setTag(holder);
//            } else {
//                holder = (ViewHolder) arg1.getTag();
//            }
//
//            holder.textMore.setVisibility(View.GONE);
//            holder.textComment1.setVisibility(View.GONE);
//            holder.textComment2.setVisibility(View.GONE);
//            holder.textComment3.setVisibility(View.GONE);
//            holder.textMore.setVisibility(View.GONE);
//
//            DynamicInfo info = mDatas.get(arg0);
//            User createUser = info.getCreateuser();
//            imageLoader().displayImage(createUser.getPicaddress(),
//                    holder.imgHead);
//            holder.textName.setText("" + createUser.getUsername());
//            holder.textDepartment.setText("" + createUser.getDepname());
//            holder.textContent.setText("" + info.getContenttxt());
//            holder.textTime.setText("" + info.getCreatetime());
//            holder.textLike.setText("" + info.getLovecount());
//            holder.textComment.setText("" + info.getCommentcount());
//            holder.textCategory.setText("" + info.getInfotypename());
//            Comment[] comments = info.getCommentlist();
//            if (comments != null && comments.length > 0) {
//                int commentLength = comments.length;
//                if (commentLength > 2) {
//                    if (commentLength > 3) {
//                        holder.textMore.setVisibility(View.VISIBLE);
//                    }
//                    holder.textComment1.setText(comments[0].getContent());
//                    holder.textComment2.setText(comments[1].getContent());
//                    holder.textComment3.setText(comments[2].getContent());
//                    holder.textComment1.setVisibility(View.VISIBLE);
//                    holder.textComment2.setVisibility(View.VISIBLE);
//                    holder.textComment3.setVisibility(View.VISIBLE);
//                } else if (commentLength > 1) {
//                    holder.textComment1.setText(comments[0].getContent());
//                    holder.textComment2.setText(comments[1].getContent());
//                    holder.textComment1.setVisibility(View.VISIBLE);
//                    holder.textComment2.setVisibility(View.VISIBLE);
//                } else {
//                    holder.textComment1.setVisibility(View.VISIBLE);
//                    holder.textComment1.setText(comments[0].getContent());
//                }
//            }
//            holder.photos.add("http://192.168.1.102:8080/img2.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img3.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img4.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img5.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img6.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img7.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img8.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img9.jpg");
//            holder.photos.add("http://192.168.1.102:8080/img10.jpg");
//            holder.photoAdapter.notifyDataSetChanged();
//            return arg1;
//        }
//
//        class ViewHolder {
//            ImageView imgHead;
//            TextView textName;
//            TextView textDepartment;
//            TextView textCategory;
//            TextView textContent;
//            NonScrollGridView gridPhoto;
//            BaseAdapter photoAdapter;
//            ArrayList<String> photos;
//            TextView textTime;
//            TextView textLike;
//            TextView textComment;
//            TextView textComment1;
//            TextView textComment2;
//            TextView textComment3;
//            TextView textMore;
//        }
//
//    }
//
//    private ImageLoader imageLoader() {
//        if (mImageLoader == null) {
//            return ImageLoader.getInstance();
//        }
//        return mImageLoader;
//    }
//
//    // 自定义适配器
//    class PhotoAdapter extends BaseAdapter {
//        private ArrayList<String> mImgs;
//
//        private PhotoAdapter(ArrayList<String> imgs) {
//            this.mImgs = imgs;
//        }
//
//        public int getCount() {
//            return mImgs.size();
//        }
//
//        public Object getItem(int item) {
//            return item;
//        }
//
//        public long getItemId(int id) {
//            return id;
//        }
//
//        // 创建View方法
//        public NotifyListView getView(int position, NotifyListView convertView, ViewGroup parent) {
//            SquareImage imageView;
//            if (convertView == null) {
//                imageView = new SquareImage(CircleMyActivity.this);
//                imageView.setLayoutParams(new LayoutParams(
//                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                imageView.setPadding(5, 5, 5, 5);
//            } else {
//                imageView = (SquareImage) convertView;
//            }
//            CircleMyActivity.this.imageLoader().displayImage(
//                    mImgs.get(position), imageView);
//            return imageView;
//        }
//    }
//}
