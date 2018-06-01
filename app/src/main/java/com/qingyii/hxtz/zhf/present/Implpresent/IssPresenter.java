package com.qingyii.hxtz.zhf.present.Implpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import com.google.gson.Gson;

import com.qingyii.hxtz.http.MyApplication;
import com.qingyii.hxtz.http.XrjHttpClient;
import com.qingyii.hxtz.wmcj.mvp.model.bean.Taskdetailbean;
import com.qingyii.hxtz.zhf.Util.HintUtil;
import com.qingyii.hxtz.zhf.Sqlitehelper.Drafitbean;
import com.qingyii.hxtz.zhf.Sqlitehelper.DraftHelper;
import com.qingyii.hxtz.zhf.Util.Global;
import com.qingyii.hxtz.zhf.Util.PictureCompressUtil;
import com.qingyii.hxtz.zhf.base.Basepresenter;
import com.qingyii.hxtz.zhf.bean.Fabubean;
import com.qingyii.hxtz.zhf.bean.SendTask;
import com.qingyii.hxtz.zhf.bean.TaskTag;
import com.qingyii.hxtz.zhf.bean.UploadVideobean;
import com.qingyii.hxtz.zhf.bean.Uploadimagebean;
import com.qingyii.hxtz.zhf.http.Urlutil;
import com.qingyii.hxtz.zhf.present.Implview.Isstaskview;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;


import java.io.File;

import java.util.ArrayList;

import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhf on 2017/10/12.
 */

public class IssPresenter extends Basepresenter<Isstaskview> {
    private ArrayList<String> imgUrl = new ArrayList<>();
    public final static int SIZE_2MB = 1024* 1024*2;
    public final static int QUALITY_720P = 720;


    public IssPresenter(Context context, Isstaskview acvitivtyview) {
        super(context, acvitivtyview);
    }

    //保存草稿箱
      public void baocun(Drafitbean drafitbean){
         DraftHelper helper=new DraftHelper(context);
          helper.addBysql(drafitbean);
          HintUtil.stopdialog();
      }

     public void gettags(){
         String murl="http://web.seeo.cn/kh/check/getTag?token="+ MyApplication.hxt_setting_config.getString("token","");
         OkHttpUtils.get()
                 .url(murl)
                 .build()
                 .execute(new GetTagback() {
                     @Override
                     public void onError(Call call, Exception e, int id) {
                         Log.i("tmdgettag",e.getMessage());
                     }

                     @Override
                     public void onResponse(TaskTag response, int id) {
                           if(response.getData()!=null&&response.getData().size()>0){
                            Acvitivtyview.gettagssuccess((ArrayList<TaskTag.DataBean>) response.getData());}
                     }
                 });

     }


    //上传选中的图片
    public void uploadPic(List<File> parts, String id) {
        imgUrl.clear();
        singleUploadPic(parts, parts.get(0), id);
    }

    private void singleUploadPic(List<File> parts, File part,String id) {
        HintUtil.startupload(context);

       File savepath=compressbitmap(part);
        OkHttpUtils.post()
                .url("http://res.seeo.cn/xfyupload?datatype=json")
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", MyApplication.userUtil.getId() + "")
                .addParams("module","kh")
                .addFile("file",getFileName(savepath.getPath()),savepath)
                .build()
                .execute(new uploadpicback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                     Log.i("tmduploaderror",e.getMessage());
                        HintUtil.stopdialog();
                    }

                    @Override
                    public void onResponse(Uploadimagebean response, int id) {
                             if (response.getError_code()==0){
                                 imgUrl.add(response.getData().getUri());
                                 Log.i("tmduploadurl",response.getData().getUri());
                                 parts.remove(0);
                               if(parts.size()>0){
                                     singleUploadPic(parts,parts.get(0),MyApplication.userUtil.getId() + "");
                               }
                               else {
                                   HintUtil.stopdialog();
                                   Acvitivtyview.uploadimagessuccess(imgUrl);
                               }
                             }
                    }
                });
    }
    //上传选中的图片
    public void uploadPic1(List<File> parts, String id) {
        imgUrl.clear();
        singleUploadPic1(parts, parts.get(0), id);
    }

    private void singleUploadPic1(List<File> parts, File part,String id) {
        HintUtil.startupload(context);

       //上传图片之前进行压缩
        File save=compressbitmap(part);

        OkHttpUtils.post()
                .url(XrjHttpClient.WmcjUploadPicture)
                .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                .addParams("user_id", MyApplication.userUtil.getId() + "")
                .addParams("module","kh")
                .addFile("file",getFileName(save.getPath()),save)
                .build()
                .execute(new uploadpicback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmduploaderror",e.getMessage());
                        HintUtil.stopdialog();
                    }

                    @Override
                    public void onResponse(Uploadimagebean response, int id) {
                        if (response.getError_code()==0){
                            imgUrl.add(response.getData().getUri());
                            Log.i("tmduploadurl",response.getData().getUri()+"--------"+parts.size());

                            parts.remove(0);
                            if(parts.size()>0){
                                singleUploadPic1(parts,parts.get(0),MyApplication.userUtil.getId() + "");
                            }
                            else {
                                HintUtil.stopdialog();
                               Acvitivtyview.uploadfilesuccess(imgUrl);
                            }
                        }
                    }
                });
    }

      public void uploadpicture(SendTask task){
          HintUtil.startupload(context);
          File file;
          File save;
         if(task.getType().equalsIgnoreCase(SendTask.VIDEO)){
            file=new File(task.getPath());
             save=compressbitmap(file);
         }else {
              file =new File(task.getUri());
               save=compressbitmap(file);
         }

          OkHttpUtils.post()
                  .url(XrjHttpClient.WmcjUploadPicture)
                  .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                  .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                  .addParams("user_id", MyApplication.userUtil.getId() + "")
                  .addParams("module","kh")
                  .addFile("file",getFileName(save.getPath()),save)
                  .build()
                  .execute(new uploadpicback() {
                      @Override
                      public void onError(Call call, Exception e, int id) {
                          HintUtil.stopdialog();
                      }

                      @Override
                      public void onResponse(Uploadimagebean response, int id) {
                          HintUtil.stopdialog();
                         if(task.getType().equalsIgnoreCase(SendTask.VIDEO)){
                               task.setWeburlpath(response.getData().getUri());
                         }else {
                             task.setWeburl(response.getData().getUri());
                         }
                          Acvitivtyview.uploadpicturesuccess(task);
                      }
                  });
      }


  //发布
    public void fabu(int a_org_id, String summary_title, String a_time, String summary, String my_score, ArrayList<String > tags, ArrayList<SendTask> sendTasks
    , ArrayList<String> files, String score_text, int is_show, int is_show_auditing, int show_range, int is_comment, String situation,
                     String address, Taskdetailbean.DataBean.IndustryParentBean bean, int select5){
      String murl= Urlutil.baseurl+"/kh/check/activityAct/"+Global.taskdetailbean.getData().getTask().getId()+"?token="+MyApplication.hxt_setting_config.getString("token","");

        String cltags=chuli(tags);
        String tasks=chulitask(sendTasks);
        String clfile=chuli(files);
        HintUtil.showdialog(context);

        Log.i("tmdfabutupianurl",tasks);
        OkHttpUtils.post()
                .url(murl)
                .addParams("a_org_id",String.valueOf(a_org_id))
                .addParams("summary_title",summary_title)
                .addParams("a_time",a_time)
                .addParams("summary",summary)
                .addParams("my_score", my_score)
                .addParams("tags",cltags)
                .addParams("imgs",tasks)
                .addParams("files",clfile)
                .addParams("score_text",score_text)
                .addParams("is_show",String.valueOf(is_show))
                .addParams("is_show_auditing",String.valueOf(is_show_auditing))
                .addParams("show_range",String.valueOf(show_range))
                .addParams("is_comment",String.valueOf(is_comment))
                .addParams("situation",situation)
                .addParams("address",address)
                .addParams("wz_industryid",bean.getID())
                .addParams("is_article",String.valueOf(select5))
                .build()
                .execute(new Fabuback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("tmdfabuerror",e.getMessage().toString());
                        HintUtil.stopdialog();
                    }
                    @Override
                    public void onResponse(Fabubean response, int id) {
                        HintUtil.stopdialog();
                        if(response.getError_code()==0){
                            Acvitivtyview.fabusucccess();
                        }
                    }
                });
    }

   //上传视频
      public void uploadvideo(SendTask task){
       HintUtil.startupload(context);
           OkHttpUtils.post()
                   .url(XrjHttpClient.WmcjUploadPicture)
                   .addHeader("Accept", XrjHttpClient.ACCEPT_V2)
                   .addHeader("Authorization", MyApplication.hxt_setting_config.getString("credentials", ""))
                   .addParams("user_id", MyApplication.userUtil.getId() + "")
                   .addParams("module","khsp")
                   .addFile("file",getFileName(task.getUri()),new File(task.getUri()))
                   .build()
                   .execute(new UploadVideoBack() {
                       @Override
                       public void onError(Call call, Exception e, int id) {
                             Log.i("tmduploadvideo",e.getMessage().toString());
                             HintUtil.stopdialog();
                             Toasty.error(context,"视频上传失败："+e.getMessage().toString(),1).show();
                       }

                       @Override
                       public void onResponse(UploadVideobean response, int id) {
                           HintUtil.stopdialog();
                           task.setWeburl(response.getData().getUri());
                           Log.i("tmdupload",task.getWeburl());
                           Acvitivtyview.uploadvideosuccess(task);
                       }
                   });
      }

     //上传图片之前进行压缩
    private File compressbitmap(File part){
         Bitmap bitmap= BitmapFactory.decodeFile(part.getPath());
         String savetemppath=part.getParent()+"/ys"+part.getName();

         Log.i("tmdyasuo",savetemppath+"---"+bitmap.getByteCount()+"parent:"+part.getParent());
       //  NativeUtil.compressBitmap(bitmap,savetemppath,NativeUtil.SIZE_1MB,360);
          PictureCompressUtil.compressBitmap(bitmap,savetemppath,PictureCompressUtil.SIZE_1KB*100,1080);

       // Bitmap bitmap1=BitmapFactory.decodeFile(savetemppath);
         File savepath=new File(savetemppath);

     //  Log.i("tmdyasuo-",savetemppath+"----"+bitmap1.getByteCount()+"--------"+ Formatter.formatFileSize(context,bitmap1.getByteCount()));
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
     /*  if(bitmap1!=null&&!bitmap1.isRecycled()){
             bitmap1.recycle();
         }  */
         return  savepath;
      }



   public abstract  class uploadpicback extends Callback<Uploadimagebean>{
       @Override
       public Uploadimagebean parseNetworkResponse(Response response, int id) throws Exception {
           String result=response.body().string();
           Log.i("tmdfabutupian",result);
           Uploadimagebean  bean=new Gson().fromJson(result,Uploadimagebean.class);
           return  bean;

       }
   }

    public String getFileName(String pathandname) {
        Log.e("FilePath", pathandname);

        int start = pathandname.lastIndexOf("/");

        if (start != -1)
            return pathandname.substring(start + 1);
        else
            return null;
    }

   public String chuli(ArrayList<String> list){
       StringBuffer str=new StringBuffer();
       if(list.size()>0){
           for(int i=0;i<list.size();i++){
               if(i<list.size()-1){
               str.append(list.get(i)+",");}
                else {
                   str.append(list.get(i));
               }
           }
               return  str.toString();
       }
       return  "";
   }
   public String chulitask(ArrayList<SendTask> tasks){
       StringBuffer str=new StringBuffer();
       if(tasks.size()<=0){
          return "";
       }
       for(int i=0;i<=tasks.size()-1;i++){
              SendTask task=tasks.get(i);
             if(i!=tasks.size()-1){
                 if (task.getType().equalsIgnoreCase(SendTask.IMG)){
                     str.append("img|"+task.getWeburl()+"|"+task.getContent()+"#");
                 } else {
                     str.append("vido|"+task.getWeburl()+"-"+task.getWeburlpath()+"|"+task.getContent()+"#");
                 }
             } else {
                 if (task.getType().equalsIgnoreCase(SendTask.IMG)){
                     str.append("img|"+task.getWeburl()+"|"+task.getContent());
                 } else {
                     str.append("vido|"+task.getWeburl()+"-"+task.getWeburlpath()+"|"+task.getContent());
                 }
             }
       }
       return  str.toString();
   }



   public abstract  class  UploadVideoBack extends Callback<UploadVideobean> {
       @Override
       public UploadVideobean parseNetworkResponse(Response response, int id) throws Exception {
           String result=response.body().string();
           Log.i("tmduploadvideo",result);
           UploadVideobean bean=new Gson().fromJson(result,UploadVideobean.class);
           return bean;
       }
   }

   public abstract  class GetTagback extends Callback<TaskTag>{
       @Override
       public TaskTag parseNetworkResponse(Response response, int id) throws Exception {
           String result=response.body().string();
           Log.i("tmdtag",result);
           TaskTag tag=new Gson().fromJson(result,TaskTag.class);
           return tag;
       }
   }
   public abstract  class Fabuback extends Callback<Fabubean>{
       @Override
       public Fabubean parseNetworkResponse(Response response, int id) throws Exception {
          String result=response.body().string();
          Log.i("tmdfaburesult",result);
           Fabubean fabubean=new Gson().fromJson(result,Fabubean.class);
           return fabubean;
       }
   }

   //是否可以再上传视频
    public Boolean nocanvideo(ArrayList<SendTask> tasks){
        for(SendTask task :tasks) {
            if(task.getType().equalsIgnoreCase(SendTask.VIDEO)){
                return  true;
            }
        }
        return  false;
    }

}
