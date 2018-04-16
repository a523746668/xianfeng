package com.zhf;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.meeting.mvp.ui.adapter.PhotoAdapter;
import com.qingyii.hxtz.meeting.mvp.ui.adapter.RecyclerItemClickListener;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.zhf.Sqlitehelper.Drafitbean;
import com.zhf.Util.Global;
import com.zhf.Util.HintUtil;
import com.zhf.Util.SelectVideoUtil;
import com.zhf.adapter.PicandVideoAdapter;
import com.zhf.bean.SendTask;
import com.zhf.bean.TaskTag;

import com.zhf.present.Implpresent.IssPresenter;
import com.zhf.present.Implview.Isstaskview;
import com.zhf.wight.FullyGridLayoutManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.shihao.library.XRadioGroup;


//发布任务
public class IssusetaskActivity extends AppCompatActivity implements Isstaskview,View.OnClickListener{
    Dialog  dialog;
    Unbinder unbinder;
    private boolean iszk=false;
    private IssPresenter mPresenter;

    @BindView(R.id.test)
    ImageView  test;

    // 控制展开
    @BindView(R.id.isck)
    AutoLinearLayout kzzk;

    //展开的内容
    @BindView(R.id.zk)
    AutoLinearLayout zk;

     @BindView(R.id.sctp)
    TextView sctp;

    @BindView(R.id.scsp)
    TextView scsp;

    @BindView(R.id.toolbar_back)
    Button back;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.meeting_summary_content)
    EditText sbcontent;

   @BindView(R.id.meeting_summary_title)
    EditText sbtitle;

    @BindView(R.id.meeting_summary_type)
   TextView zhuangti;

    @BindView(R.id.meeting_task)
    TextView task;

    @BindView(R.id.meeting_time)
    EditText time;

    @BindView(R.id.meeting_qk)
    EditText cyqk;

    @BindView(R.id.meeting_summary_pic_recyclerView)
    RecyclerView  recyclerView;

    @BindView(R.id.summary_rg1)
    RadioGroup group1;

    @BindView(R.id.summary_rg2)
    RadioGroup group2;

    @BindView(R.id.summary_rg3)
    XRadioGroup group3;

    @BindView(R.id.summary_rg4)
    RadioGroup group4;

    @BindView(R.id.hegelv)
    EditText ziping;

    @BindView(R.id.zpcontent)
    EditText zpcontent;

    private PhotoAdapter photoAdapter;

    private PhotoAdapter photoAdapter2;

    private  ArrayList<String> tagsid=new ArrayList<>();
    //@BindView(R.id.scwj)
    //ImageView ivadd;

   @BindView(R.id.recyc2)
  RecyclerView recyclerView2;

    @BindView(R.id.taskbaocun)
    Button bc;

    @BindView(R.id.issscrow)
    NestedScrollView scrollView;

    //要上传的图片信息和视频信息
     private   ArrayList<SendTask> tasks=new ArrayList<>();

    //标签信息
    private ArrayList<TaskTag.DataBean> tag3=new ArrayList<>();

    //图片的本地地址
    private ArrayList<String> selectedPhotos = new ArrayList<>();
   //上床文件的本地地址
    private ArrayList<String> selectedPhotos2 = new ArrayList<>();

    //图片的url地址
    private ArrayList<String> imgurls=new ArrayList<>();

    //上传文件的url地址
    private ArrayList<String> imgurls2=new ArrayList<>();

    //专题标签
    private ArrayList<String > tags=new ArrayList<>();

    private ArrayList<String> tags2=new ArrayList<>();

    @BindView(R.id.toolbar_right_tv)
    TextView caogaoxiang;
       String[] str;
       boolean[] stag2;
    @BindView(R.id.taskfabu)
    Button fabu;

    @BindView(R.id.djsc)
    AutoRelativeLayout djsc;
     int select1=1;
    int select2=1;
    int select3=3;
    int select4=1;

    private PicandVideoAdapter adapter;

    //  使用照相机拍照保存的地址
     private String usecamerapath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_issusetask);
           unbinder= ButterKnife.bind(this);
           EventBus.getDefault().register(this);
           init();
           initrecyclerview();
           initrecyclerview2();
           initgroupbutton();
           fabu();
    }



    private void initrecyclerview2() {
        photoAdapter2=new PhotoAdapter(this,selectedPhotos2);
        recyclerView2.setLayoutManager(new FullyGridLayoutManager(this,4));
        recyclerView2.setAdapter(photoAdapter2);
        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (photoAdapter2.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                        PhotoPicker.builder()
                                .setPhotoCount(PhotoAdapter.MAX)
                                .setShowCamera(true)
                                .setPreviewEnabled(false)
                                .setSelected(selectedPhotos2)
                                .start(IssusetaskActivity.this,PhotoPicker.REQUEST_CODE);
                    } else {
                        PhotoPreview.builder()
                                .setPhotos(selectedPhotos2)
                                .setCurrentItem(position)
                                .start(IssusetaskActivity.this,PhotoPreview.REQUEST_CODE);
                    }
                }));
    }

    private void initgroupbutton() {
     group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
             RadioButton button= (RadioButton) findViewById(checkedId);
             select1= Integer.valueOf((String) button.getTag());
         }
     });
        group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton button= (RadioButton) findViewById(checkedId);
                select2= Integer.valueOf((String) button.getTag());
            }
        });
        group3.setOnCheckedChangeListener(new XRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(XRadioGroup xRadioGroup, @IdRes int i) {
                RadioButton button= (RadioButton) findViewById(i);
                select3= Integer.valueOf((String) button.getTag());
                Log.i("tmdselect",select3+"-----");
            }
        });
        group4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton button= (RadioButton) findViewById(checkedId);
                select4= Integer.valueOf((String) button.getTag());
            }
        });

    }

    //  发布任务请求
    private void fabu() {
      fabu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(TextUtils.isEmpty(sbtitle.getText())){
                  HintUtil.showtoast(IssusetaskActivity.this,"标题不能为空");
                return;
              }  else if (sbtitle.getText().toString().length()>50){
                  HintUtil.showtoast(IssusetaskActivity.this,"标题长度应在50个字以为");
                  return;
              }
              if(TextUtils.isEmpty(sbcontent.getText())){
                  HintUtil.showtoast(IssusetaskActivity.this,"上报内容不能为空");
                  return;
              }
              if(TextUtils.isEmpty(time.getText())){
                  HintUtil.showtoast(IssusetaskActivity.this,"时间不能为空");
                  return;
              }
              String cyq="";
              if(!TextUtils.isEmpty(cyqk.getText())){
                  cyq=cyqk.getText().toString();
              }
              String sbt=sbtitle.getText().toString();
              String times=time.getText().toString();
              String sbc=sbcontent.getText().toString();


              String zp="";
              if(!TextUtils.isEmpty(ziping.getText())){
                  zp=ziping.getText().toString();
              }

              String zpc="";
              if(!TextUtils.isEmpty(zpcontent.getText())){
                  zpc=zpcontent.getText().toString();
              }
              Log.i("tmdfabu",Global.taskdetailbean.getData().getTask().getIndustry_admin()+"");
              mPresenter.fabu(Global.taskdetailbean.getData().getTask().getIndustry_admin(),sbt,times,
                      sbc,zp,tagsid,tasks,imgurls2,zpc,select1,select2,
                      select3,select4,cyq,"湖南长沙");

          }
      });
    }

    //列表初始化
    private void initrecyclerview() {
        adapter=new PicandVideoAdapter(tasks,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setScrollView(scrollView);
      //  recyclerView.setNestedScrollingEnabled(false);
    }

    private void init() {
        mPresenter=new IssPresenter(this,this);
        mPresenter.gettags();
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

         kzzk.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!iszk){
                     zk.setVisibility(View.VISIBLE);
                     iszk=true;
                 }
                 else {
                    zk.setVisibility(View.GONE);
                     iszk=false;
                 }
             }
         });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("报送任务动态");
        zhuangti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(tags.size()>0){

                     Log.i("tmdtag",str[0]);
                     new AlertDialog.Builder(IssusetaskActivity.this)
                             .setTitle("选择标签")
                             .setMultiChoiceItems(str, stag2, new DialogInterface.OnMultiChoiceClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                      if(isChecked) {
                                         tags2.add(str[which]);
                                          stag2[which]=true;
                                          tagsid.add(String.valueOf(tag3.get(which).getId()));
                                       }else {
                                          tags2.remove(str[which]);
                                           stag2[which]=false;
                                          tagsid.remove(String.valueOf(tag3.get(which).getId()));
                                      }
                                 }
                             }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     if(tags2.size()>0){
                                         String str=mPresenter.chuli(tags2);
                                         zhuangti.setText(str);
                                     } else {
                                         zhuangti.setText("点击选择标签（选填）");
                                     }
                                     dialog.dismiss();
                                 }
                             }).show();

                 } else {
                     HintUtil.showtoast(IssusetaskActivity.this,"抱歉，没有拿到标签数据");
                 }
            }
        });

        if(Global.taskdetailbean.getData().getIndlibsyies()!=null&&Global.taskdetailbean.getData().getIndlibsyies().size()>0){
              StringBuffer str=new StringBuffer();
              for(Taskdetailbean.DataBean.IndlibsyiesBean bean :Global.taskdetailbean.getData().getIndlibsyies()){
                  str.append(bean.getTitle()+">>");
              }
              str.append(Global.taskdetailbean.getData().getTask().getTarget());
            task.setText(str.toString())
                                                                                                                                                    ;
        }
        time.setFocusable(false);
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerView.Builder(IssusetaskActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回
                       time.setText(gettime(date));
                    }
                }).setType(new boolean[]{true, true, true,false,false, false})// 默认全部显示
                        .build();
               // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
            }
        });
         caogaoxiang.setText("草稿箱");
        caogaoxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IssusetaskActivity.this,DraftActivity.class);
                startActivityForResult(intent,100);
            }
        });

        //保存到草稿箱
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               View view= LayoutInflater.from(IssusetaskActivity.this).inflate(R.layout.drafit,null);
                AlertDialog dialog=new AlertDialog.Builder(IssusetaskActivity.this)
                        .setView(view)
                        .show();
                Button qr= (Button) view.findViewById(R.id.draftqueren);
                Button qx= (Button) view.findViewById(R.id.draftquxiao);
                EditText name= (EditText) view.findViewById(R.id.draftname);
                qr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(name.getText())){
                            HintUtil.showtoast(IssusetaskActivity.this,"名字不能为空");
                           return;
                        }

                        HintUtil.showdialog(IssusetaskActivity.this);
                        Drafitbean bean=new Drafitbean();
                        bean.setTaskid(Global.taskdetailbean.getData().getTask().getId());
                        bean.setName(name.getText().toString()+","+gettime(new Date()));
                        Log.i("tmdtimename",name.getText().toString()+","+gettime(new Date()));
                        if(TextUtils.isEmpty(sbtitle.getText())){
                            bean.setSummary_title(null);
                        }else {
                            bean.setSummary_title(sbtitle.getText().toString());
                        }

                        if(TextUtils.isEmpty(sbcontent.getText())){
                            bean.setSummary(null);
                        } else {
                            bean.setSummary(sbcontent.getText().toString());
                        }

                        if(TextUtils.isEmpty(time.getText())){
                            bean.setA_time(null);
                        }else {
                            bean.setA_time(time.getText().toString());
                        }

                        if(TextUtils.isEmpty(cyqk.getText())) {
                            bean.setSituation(null);
                        }  else {
                            bean.setSituation(cyqk.getText().toString());
                        }

                        if(TextUtils.isEmpty(ziping.getText())){
                            bean.setMy_score(null);
                        } else{
                            bean.setMy_score(ziping.getText().toString());
                        }

                        if(TextUtils.isEmpty(zpcontent.getText())){
                            bean.setScore_text(null);
                        } else {
                            bean.setScore_text(zpcontent.getText().toString());
                        }
                        bean.setIs_show(select1);
                        bean.setIs_show_auditing(select2);
                        bean.setShow_range(select3);
                        bean.setIs_comment(select4);
                        bean.setTags(tags2);
                        bean.setImgs(imgurls);
                        bean.setFiles(imgurls2);

                        chulisctpandsp();
                        bean. setSelectedPhotos(selectedPhotos);

                        bean.setSelectedPhotos2(selectedPhotos2);
                        mPresenter.baocun(bean);
                        dialog.dismiss();

                        Toasty.success(IssusetaskActivity.this, "保存成功!", Toast.LENGTH_SHORT, true).show();
                    }
                });
                qx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          dialog.dismiss();
                    }
                });

            }
        });

        djsc.setOnClickListener(this);
    }

       //草稿箱处理上传图片和视频的数据
       private void   chulisctpandsp() {
            selectedPhotos.clear();
           for(SendTask task :tasks){
               String str;
             if(task.getType().equalsIgnoreCase(SendTask.IMG)){
                  str="img"+task.getUri()+"#"+task.getContent();
             }  else {
                 str="vid"+task.getUri()+"#"+task.getContent();
             }
               selectedPhotos.add(str);
           }
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //进入草稿箱后的点击某个item后的回调数据
        if(requestCode==100&&resultCode==100){

            }

         //从选择视频页面回传的数据
        if(requestCode== SelectVideoUtil.VideoS&&resultCode==RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    // 视频路径：MediaStore.Audio.Media.DATA
                    String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    // 缩略图ID:MediaStore.Audio.Media._ID
                    int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));

                    Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                    SendTask task=new SendTask();
                    task.setUri(videoPath);
                    task.setType(SendTask.VIDEO);
                    SelectVideoUtil.getsendtask(task,bitmap2,new File(getExternalCacheDir().getAbsolutePath()+"/"+ System.currentTimeMillis()+".jpg"));
                    mPresenter.uploadvideo(task);
                   // mPresenter.uploadpicture(task);
                    return;
                }

            }
        }
        //  recyclerview对应的回调,从选择相册回传过来的数据
        if (resultCode == RESULT_OK &&
                (requestCode == SelectVideoUtil.SPHOTO1)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }

            if (photos != null) {
                if (photos.size() + tasks.size() > 9) {
                    Toasty.info(this, "上传文件数量最大为9", 0).show();
                    return;
                } else {
                    List<File> parts = new ArrayList<>(photos.size());
                    for (String str : photos) {
                        SendTask task = new SendTask();
                        task.setType(SendTask.IMG);
                        task.setUri(str);
                        tasks.add(task);
                        File file = new File(task.getUri());
                        parts.add(file);
                    }
                    adapter.notifyDataSetChanged();

                    mPresenter.uploadPic
                            (parts, String.valueOf(MyApplication.userUtil.getId()));
                    return;
                }
            }
        }
        //打开相机拍摄之后的处理
         if(resultCode==RESULT_OK&&requestCode==SelectVideoUtil.Cmamera1) {
                     SendTask task=new SendTask();
                     task.setType(SendTask.IMG);
                     task.setUri(usecamerapath);
                     mPresenter.uploadpicture(task);
                  return;
         }

        //台账记录的处理
         if(resultCode==RESULT_OK&&requestCode==PhotoPicker.REQUEST_CODE||requestCode==PhotoPreview.REQUEST_CODE){
             List<String> photos = null;
             if (data != null) {
                 photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
             }
             selectedPhotos2.clear();

             if (photos != null) {

                 selectedPhotos2.addAll(photos);
             }
             photoAdapter2.notifyDataSetChanged();
             if (requestCode == PhotoPicker.REQUEST_CODE) {

                 List<File> parts = new ArrayList<>(selectedPhotos2.size());
                 for (String selectedPhoto : selectedPhotos2) {
                     File file = new File(selectedPhoto);
                     parts.add(file);
                 }

                 mPresenter.uploadPic1(parts, String.valueOf(MyApplication.userUtil.getId()));
             }
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.unbind();
        EventBus.getDefault().unregister(this);
      //  Global.taskdetailbean=null;
    }

    //接受草稿箱传过来的值
     @Subscriber(mode = ThreadMode.MAIN)
     public void getdraft(Drafitbean drafitbean){
            if(!TextUtils.isEmpty(drafitbean.getA_time())){
                time.setText(drafitbean.getA_time());
            }
         if(!TextUtils.isEmpty(drafitbean.getSummary_title())){
             sbtitle.setText(drafitbean.getSummary_title());
         }
         if(!TextUtils.isEmpty(drafitbean.getSummary())){
             sbcontent.setText(drafitbean.getSummary());
         }
         if(!TextUtils.isEmpty(drafitbean.getMy_score())){
             ziping.setText(drafitbean.getMy_score());
         }
         if(!TextUtils.isEmpty(drafitbean.getScore_text())){
             zpcontent.setText(drafitbean.getScore_text());
         }
         if(!TextUtils.isEmpty(drafitbean.getSituation())){
             cyqk.setText(drafitbean.getSituation());
         }

         selectedPhotos2.clear();
         selectedPhotos2.addAll(drafitbean.getSelectedPhotos2());
         photoAdapter2.notifyDataSetChanged();

         imgurls.clear();
         imgurls.addAll(drafitbean.getImgs());
         imgurls2.clear();
         imgurls2.addAll(drafitbean.getFiles());

         //处理上传文件的草稿记录
          selectedPhotos.clear();
          selectedPhotos.addAll(drafitbean.getSelectedPhotos());
          tasks.clear();
          for(String str:selectedPhotos){
              SendTask task=new SendTask();
              String str1=str.substring(3);
              Log.i("tmdcaogaoxiang",str1);
              String[] strings=str1.split("#");
              task.setUri(strings[0]);
              task.setContent(strings[1]);
              if(str.startsWith("img")){
                  task.setType(SendTask.IMG);
              } else {
                  task.setType(SendTask.VIDEO);
              }
              tasks.add(task);
          }
            adapter.notifyDataSetChanged();
     }



    @Override
    public void showerrow(Exception e) {

    }

    @Override
    public void getdatasuccess(ArrayList list) {

    }

    @Override
    public void getdatasuccess(Object o) {

    }


     //第一个上传文件对应上传的回调
    @Override
    public void uploadimagessuccess(ArrayList<String> list) {

            for(int i=0;i<list.size();i++){
                tasks.get(tasks.size()-list.size()+i).setWeburl(list.get(i));
                 imgurls.add(list.get(i));
            }

           HintUtil.showtoast(this,"上传成功");
    }

    @Override
    public void uploadfilesuccess(ArrayList<String> list) {
        imgurls2.clear();
        imgurls2.addAll(list);
        HintUtil.showtoast(this,"上传成功");
    }

    @Override
    public void gettagssuccess(ArrayList<TaskTag.DataBean> list) {
        tag3.clear();
        tag3.addAll(list);
        tags.clear();
        for(TaskTag.DataBean bean :list){
            tags.add(bean.getName());
        }
       str=tags.toArray(new String[0]);
        stag2=new boolean[str.length];
        for(int i=0;i<str.length;i++){
           stag2[i]=false;
        }

    }

    @Override
    public void fabusucccess() {
        HintUtil.showtoast(this,"任务发布成功");
        finish();
    }

    @Override
    public void uploadvideosuccess(SendTask task) {
          tasks.add(task);
          adapter.notifyDataSetChanged();
        Toasty.info(this,"视频上传成功",0).show();
       mPresenter.uploadpicture(task);
    }

    @Override
    public void uploadpicturesuccess(SendTask task) {
        if(task.getType().equalsIgnoreCase(SendTask.VIDEO)){
            Toasty.info(this,"视频缩略图上传成功",0).show();
        }else {
            tasks.add(task);
            adapter.notifyDataSetChanged();
            Toasty.info(this,"图片上传成功",0).show();
        }

    }


    private String gettime(Date date){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }


    private String getbiaoqian(String[] strings){
      StringBuffer sb=new StringBuffer();
      for(int i=0;i<strings.length;i++){
        if(i==strings.length-1){
            sb.append(strings[i]);
        } else {
           sb.append(strings[i]+",");
        }
      }
       return sb.toString();
   }


    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.djsc:
                  if(tasks.size()>=9){
                      Toasty.error(this,"文件上传数量最大为9",0).show();
                      return;
                  }
                 dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

                  dialog.getWindow().setGravity(Gravity.BOTTOM);
                  //获得屏幕看都，并传给dialog
                  dialog.getWindow().getAttributes().width = getWindowManager().getDefaultDisplay().getWidth();
                  dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
                 dialogview=View.inflate(this,R.layout.choose_avatar,null);
                  Button albumButton = (Button)dialogview.findViewById(R.id.choose_album);
                  Button camButton = (Button) dialogview.findViewById(R.id.choose_cam);
                  Button cancelButton = (Button) dialogview.findViewById(R.id.choose_cancel);
                  Button svideo= (Button) dialogview.findViewById(R.id.choose_video);
                  Button pvideo= (Button) dialogview.findViewById(R.id.choose_pvideo);
                  svideo.setVisibility(View.VISIBLE);
                  pvideo.setVisibility(View.VISIBLE);
                  svideo.setOnClickListener(this);
                  pvideo.setOnClickListener(this);
                  albumButton.setOnClickListener(this);
                  camButton.setOnClickListener(this);
                  cancelButton.setOnClickListener(this);
                  dialog.setContentView(dialogview);
                  dialog.show();
                  break;
              case R.id.choose_cancel:
                   dialog.dismiss();
                  break;
              case R.id.choose_cam:
                   dialog.dismiss();
                 usecamerapath= SelectVideoUtil. usecamera(this);
                  break;
              case R.id.choose_album://本地相册选择
                  if(tasks.size()<9) {
                      PhotoPicker.builder()
                              .setPhotoCount(PhotoAdapter.MAX)
                              .setShowCamera(true)
                              .setPreviewEnabled(false)
                              .start(IssusetaskActivity.this,SelectVideoUtil.SPHOTO1);
                  } else {
                      Toasty.error(this,"文件上传数量最大为9",0).show();
                  }

                  dialog.dismiss();
                  break;
               case R.id.choose_video://选择本地视频
                   if(mPresenter.nocanvideo(tasks)){
                       Toasty.error(this,"最多上传一个视频",0).show();
                       return;
                   }

                   if(tasks.size()<9){
                       SelectVideoUtil.VideoSlect(IssusetaskActivity.this);
                   } else {
                       Toasty.error(this,"文件上传数量最大为9",0).show();
                   }

                   dialog.dismiss();
                   break;
              case R.id.choose_pvideo://录制视频
                  dialog.dismiss();
                  if(mPresenter.nocanvideo(tasks)){
                      Toasty.error(this,"最多上传一个视频",0).show();
                      return;
                  }
                  Intent intent=new Intent(IssusetaskActivity.this,RecodingActivity.class);
                  startActivityForResult(intent,SelectVideoUtil.VideoP);
                  break;

          }
    }

    View  dialogview;

    //接受视频录制页面传回的视频本地录制路径
     @Subscriber(mode = ThreadMode.MAIN)
    public void getvideopath (String path){

         Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
         SendTask task=new SendTask();
            SelectVideoUtil.getsendtask(task,bitmap2,new File(getExternalCacheDir().getAbsolutePath()+"/"+ System.currentTimeMillis()+".jpg"));
            task.setUri(path);
            task.setType(SendTask.VIDEO);
            mPresenter.uploadvideo(task);
    }

    //接受Viewpager页面穿回来的删除信息
    @Subscriber(mode = ThreadMode.MAIN)
    public void getdelete(ArrayList<SendTask> list){
        tasks.clear();
        tasks.addAll(list);
        adapter.notifyDataSetChanged();

        Log.i("tmdgetdelete","--"+tasks.size());
    }

}
