package com.qingyii.hxt.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.qingyii.hxt.download.DownloadUtil;
import com.qingyii.hxt.download.DownloadUtil.OnDownloadListener;
import com.qingyii.hxt.R;
import com.qingyii.hxt.database.BookDB;
import com.qingyii.hxt.http.HttpUrlConfig;
import com.qingyii.hxt.http.MyApplication;
import com.qingyii.hxt.pojo.BooksParameter;
import com.qingyii.hxt.util.AnimateFirstDisplayListener;
import com.qingyii.hxt.util.EmptyUtil;
import com.qingyii.hxt.util.ZipUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class shujiaAdapter extends BaseAdapter {
    private ArrayList<BooksParameter.DataBean> list = new ArrayList<>();
    private Context context;
    private int max;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public shujiaAdapter(ArrayList<BooksParameter.DataBean> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {

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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BooksParameter.DataBean info = list.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.shujia_item, null);
        TextView tv_shujia_title = (TextView) convertView.findViewById(R.id.tv_shujia_title);
        TextView tv_shujia_time = (TextView) convertView.findViewById(R.id.tv_shujia_time);
        TextView tv_shujia_name = (TextView) convertView.findViewById(R.id.tv_shujia_name);
        TextView tv_shujia_content = (TextView) convertView.findViewById(R.id.tv_shujia_content);
        TextView tv_shujia_read = (TextView) convertView.findViewById(R.id.tv_shujia_read);
        final Button xiazai = (Button) convertView.findViewById(R.id.xiazai);
        final TextView numble = (TextView) convertView.findViewById(R.id.numble);
        final TextView yixiazai = (TextView) convertView.findViewById(R.id.yixiazai);
        final ProgressBar progressbar = (ProgressBar) convertView.findViewById(R.id.progressbar);

        ImageView iv_shujia_Image = (ImageView) convertView.findViewById(R.id.iv_shujia_Image);
        ImageView tv_shujia_jurisdic = (ImageView) convertView.findViewById(R.id.tv_shujia_jurisdic);
        tv_shujia_title.setText(info.getBookname());

        if (EmptyUtil.IsNotEmpty(info.getReadnums() + ""))
            tv_shujia_time.setText("阅读次数：  " + info.getReadnums());
        String readStr = info.getReadnums() + "";
        if (EmptyUtil.IsNotEmpty(readStr)) {
            float percent = Float.parseFloat(readStr);
            DecimalFormat decimalFormat = new DecimalFormat("#.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String percentStr = decimalFormat.format(percent * 100);//format 返回的是字符串
            readStr = "已阅读:" + percentStr + "%";
        } else {
            readStr = "暂未阅读";
        }

        if (EmptyUtil.IsNotEmpty(info.getSdCardUrl())) {
            xiazai.setVisibility(View.GONE);
            yixiazai.setVisibility(View.VISIBLE);
        } else {
            xiazai.setVisibility(View.VISIBLE);
            yixiazai.setVisibility(View.GONE);
        }

        if (EmptyUtil.IsNotEmpty(info.getPublishstatus())) {
            if ("public".equals(info.getPublishstatus())) {
                tv_shujia_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongkai);
            } else if ("private".equals(info.getPublishstatus())) {
                tv_shujia_jurisdic.setBackgroundResource(R.mipmap.shuwu_zhuanyou);
            } else if ("share".equals(info.getPublishstatus())) {
                tv_shujia_jurisdic.setBackgroundResource(R.mipmap.shuwu_gongxiang);
            }
        }

        String urlString = info.getBookurl();  //HttpUrlConfig.photoDir  +  info.getBookaddress();
        final String localPath = HttpUrlConfig.cacheDir;
        final String filename = info.getBookurl().substring(
                info.getBookurl().lastIndexOf("/") + 1,
                info.getBookurl().length());

        final DownloadUtil mDownloadUtil1 = new DownloadUtil(2, localPath, filename, urlString, context);
        if (info != null && EmptyUtil.IsNotEmpty(info.getBookurl())) {
            // 文件名：类似（1431311588750.epub）

            mDownloadUtil1.setOnDownloadListener(new OnDownloadListener() {

                @Override
                public void downloadStart(int fileSize) {
                    // TODO Auto-generated method stub
                    max = fileSize;
                    progressbar.setMax(fileSize);
                }

                @Override
                public void downloadProgress(int downloadedSize) {
                    // TODO Auto-generated method stub
                    progressbar.setProgress(downloadedSize);
                    numble.setText((int) downloadedSize * 100 / max + "%");
                }

                @Override
                public void downloadEnd() {
                    // TODO Auto-generated method stub
                    xiazai.setVisibility(View.GONE);
                    yixiazai.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    numble.setVisibility(View.GONE);

                    BookDB.updateFilename(info.getId() + "", filename);

                    info.setSdCardUrl(filename);
//                    info.setBookaddress(filename);
                    notifyDataSetChanged();
                    // 广播通知 :刷新前面已下载列表书籍
//                    Intent intent = new Intent();
//                    intent.setAction("action.shujiDownload");
//                    context.sendBroadcast(intent);


                    // 复制epub文件
                    if (ZipUtil.copyFile(HttpUrlConfig.cacheDir + "/"
                            + filename, HttpUrlConfig.cacheDir + "/temp.zip")) {
                        // 解压书籍
                        if (ZipUtil.unZip(
                                HttpUrlConfig.cacheDir + "/temp.zip",
                                HttpUrlConfig.cacheDir
                                        + "/"
                                        + filename.substring(0,
                                        filename.lastIndexOf(".")))) {
                            // 删除临时zip文件
                            ZipUtil.deleteFile(HttpUrlConfig.cacheDir
                                    + "/temp.zip");
                        }
                    }
                }
            });


        }
        xiazai.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (getSDCard()) {

                    xiazai.setVisibility(View.GONE);
                    progressbar.setVisibility(View.VISIBLE);
                    numble.setVisibility(View.VISIBLE);
                    mDownloadUtil1.start();

                } else {
                    Toast.makeText(context, "sd卡不存在！请检查sd卡！", Toast.LENGTH_SHORT).show();

                }

            }
        });


        if (EmptyUtil.IsNotEmpty(info.getBookname())) {
            tv_shujia_title.setText(info.getBookname());
        }
        if (EmptyUtil.IsNotEmpty(info.getAuthor())) {
            tv_shujia_name.setText("作者： " + info.getAuthor());
        }
        if (EmptyUtil.IsNotEmpty(info.getDescription())) {
            tv_shujia_content.setText(info.getDescription());
        }

//        tv_shujia_name.setText("作者:" + info.getAuthor());
        tv_shujia_read.setText(readStr);
        tv_shujia_content.setText(info.getDescription());
//         ImageLoader.getInstance().displayImage(info.getPicaddress(), iv_shujia_Image);
        ImageLoader.getInstance().displayImage(info.getBookcover(), iv_shujia_Image, MyApplication.options, animateFirstListener);
        return convertView;
    }


    //android判断SD卡是否存在以及获取SD卡的路径
    public Boolean getSDCard() {
        Boolean flag = false;
        // 判断SDCard是否存在
        boolean sdExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdExist) {
            // 获取SDCard的路径
//	                 File sdFile = Environment.getExternalStorageDirectory();
//	                 Toast.makeText(FfffActivity.this, "" + sdFile, 0).show();
            flag = true;
        }
        return flag;
    }

}
