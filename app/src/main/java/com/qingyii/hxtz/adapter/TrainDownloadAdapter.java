package com.qingyii.hxtz.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyii.hxtz.R;
import com.qingyii.hxtz.http.HttpUrlConfig;
import com.qingyii.hxtz.pojo.TrainFilesList;

import java.io.File;
import java.util.List;

/**
 * Created by XRJ on 16/9/11.
 */
public class TrainDownloadAdapter extends BaseAdapter {
    private Context context;
    private List<TrainFilesList.DataBean> tDataList;
    private onDownloadClickListener listener;

    public TrainDownloadAdapter(Context context, List<TrainFilesList.DataBean> tDataList) {
        this.context = context;
        this.tDataList = tDataList;
    }

    @Override
    public int getCount() {
        return tDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return tDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = View.inflate(context, R.layout.train_load_listview_item, null);
            vh = new ViewHolder();
            vh.file_name = (TextView) view.findViewById(R.id.file_name);
            vh.file_time = (TextView) view.findViewById(R.id.file_time);
            vh.file_size = (TextView) view.findViewById(R.id.file_size);
            vh.file_state = (TextView) view.findViewById(R.id.file_download_state);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        TrainFilesList.DataBean tDataBean = tDataList.get(i);

        vh.file_name.setText(tDataBean.getFilename());
        vh.file_time.setText(tDataBean.getDescription());
        File file = new File(HttpUrlConfig.cacheDir, tDataBean.getFilename());
        if (file.exists()) {
            vh.file_state.setText("打开");
        }else{
            vh.file_state.setText("下载");
        }
        int size = tDataBean.getSize();
        if (size / 1024 / 1024 > 1) {
            vh.file_size.setText((size / 1024) / 1024 + " MB");
        } else if (size / 1024 > 1) {
            vh.file_size.setText(size / 1024 + " KB");
        } else {
            vh.file_size.setText(size + " B");
        }
        vh.file_state.setOnClickListener(v -> {
            listener.onClickDownload(tDataBean,v);
        });

        return view;
    }
    class ViewHolder {
        TextView file_name;
        TextView file_time;
        TextView file_size;
        TextView file_state;
    }
    public interface onDownloadClickListener{
        void onClickDownload(TrainFilesList.DataBean tDataBean,View v);
    }
    public void setOnDownloadClickListener(onDownloadClickListener listener){
        this.listener = listener;
    }
}
