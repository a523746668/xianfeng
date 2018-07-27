package com.qingyii.hxtz.base.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

//实现Recyclerview的滑动监听
public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private int itemCount, lastPosition, lastItemCount;

    public abstract void onLoadMore();


}
