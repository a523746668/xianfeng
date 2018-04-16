package com.qingyii.hxtz.base.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qingyii.hxtz.base.widget.PacManRefreshHead;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * ClassName: com.huasi.githubtrends.BaseRecyclerViewHolder<p>
 * Author:oubowu<p>
 * Fuction: RecyclerView通用适配器Holder<p>
 * CreateDate:2016/2/16 23:00<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    //集合类，layout里包含的View,以view的id作为key，value是view对象
    protected SparseArray<View> mViews;
    protected Context mContext;//上下文对象

    public BaseRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
        mContext = context;
        mViews = new SparseArray<View>();
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public PacManRefreshHead getPacman(int viewId) {
        return (PacManRefreshHead) getView(viewId);
    }


    public BaseRecyclerViewHolder setVisibility(int viewId, int value) {
        View view = findViewById(viewId);
        view.setVisibility(value);
        return this;
    }
    public BaseRecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }
    public BaseRecyclerViewHolder setImage(int viewId, int value) {
        ImageView view = findViewById(viewId);
        view.setImageResource(value);
        return this;
    }
    public BaseRecyclerViewHolder setTextStyle(int viewId, int style) {
        TextView view = findViewById(viewId);
        view.setTypeface(Typeface.defaultFromStyle(style));
        return this;
    }
    public BaseRecyclerViewHolder setTextColor(int viewId, int ResId) {
        TextView view = findViewById(viewId);
        view.setTextColor(mContext.getResources().getColor(ResId));
        return this;
    }
    public BaseRecyclerViewHolder setTextColorRes(int viewId, int ResId) {
        TextView view = findViewById(viewId);
        view.setTextColor(ResId);
        return this;
    }

    public BaseRecyclerViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public BaseRecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public BaseRecyclerViewHolder setBackgroundResource(int viewId, int resId){
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public void onRelease() {
    }
    public enum DataLoadType {

        /**
         * 刷新成功
         */
        TYPE_REFRESH_SUCCESS,

        /**
         * 刷新失败
         */
        TYPE_REFRESH_FAIL,

        /**
         * 加载更多成功
         */
        TYPE_LOAD_MORE_SUCCESS,

        /**
         * 加载更多失败
         */
        TYPE_LOAD_MORE_FAIL

    }
}
