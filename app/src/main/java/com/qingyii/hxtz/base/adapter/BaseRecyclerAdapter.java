package com.qingyii.hxtz.base.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jess.arms.base.BaseHolder;
import com.qingyii.hxtz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BaseRecyclerAdapter<p>
 * Author:oubowu<p>
 * Fuction: RecyclerView通用适配器<p>
 * CreateDate:2016/2/16 22:47<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected List<T> mData;
    protected boolean mUseAnimation;
    private RecyclerView.LayoutManager mLayoutManager;
    protected OnItemClickListener mClickListener;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_FOOTER = 3;


    private boolean showFooterTips = false;
    protected boolean mShowFooter;
    private int selecter = -100;

    public BaseRecyclerAdapter(List<T> data) {
        this(data, true);
    }

    public BaseRecyclerAdapter(List<T> data, boolean useAnimation) {
        this(data, useAnimation, null);
    }

    public BaseRecyclerAdapter(List<T> data, boolean useAnimation, RecyclerView.LayoutManager layoutManager) {
        mUseAnimation = useAnimation;
        mLayoutManager = layoutManager;
        mData = data == null ? new ArrayList<T>() : data;
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new BaseRecyclerViewHolder(parent.getContext(),
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        } else {
            final BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(parent.getContext(),
                    LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(viewType), parent, false));
            if (mClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        mClickListener.onItemClick(getData().get(holder.getLayoutPosition()),v, holder.getLayoutPosition());
                    }
                });
            }
            return holder;
        }
    }

    public void setSelecter(int selecter) {
        this.selecter = selecter;
    }

    public int getSelecter() {
        return selecter;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (mLayoutManager != null) {
                if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    if (((StaggeredGridLayoutManager) mLayoutManager).getSpanCount() != 1) {
                        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView
                                .getLayoutParams();
                        params.setFullSpan(true);
                    }
                } else if (mLayoutManager instanceof GridLayoutManager) {
                    if (((GridLayoutManager) mLayoutManager)
                            .getSpanCount() != 1 && ((GridLayoutManager) mLayoutManager)
                            .getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                        throw new RuntimeException("网格布局列数大于1时应该继承SpanSizeLookup时处理底部加载时布局占满一行。");
                    }
                }
            }
            holder.getPacman(R.id.pac_man).performLoading();
//            if (mShowFooter) {
//                holder.getPacman(R.id.pac_man).setVisibility(View.VISIBLE);
//            } else {
//                holder.getPacman(R.id.pac_man).setVisibility(View.GONE);
//            }
        } else {
            bindData(holder, position, mData.get(position));
            if (mUseAnimation) {
                setAnimation(holder.itemView, position);
            }
        }
    }

    private int lastPosition = -1;

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mUseAnimation && holder.itemView.getAnimation() != null && holder.itemView
                .getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addMoreData(List<T> data) {
        int startPos = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowFooter && getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        }
        return bindViewType(position);
    }

    @Override
    public int getItemCount() {
        int i = mShowFooter ? 1 : 0;
        // KLog.e("插入: "+i);
        return mData != null ? mData.size() + i : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract void bindData(BaseRecyclerViewHolder holder, int position, T item);

    protected int bindViewType(int position) {
        return 0;
    }

    public void showFooter(boolean showFooterTips) {
         Log.e(getClass().getSimpleName(),"Adapter显示尾部: " + getItemCount());
        this.showFooterTips = showFooterTips;
        notifyItemInserted(getItemCount());
        mShowFooter = true;
    }

    public void hideFooter() {
        // KLog.e("Adapter隐藏尾部:" + (getItemCount() - 1));
        notifyItemRemoved(getItemCount() - 1);
        mShowFooter = false;
    }

    /**
     * 遍历所有hodler,释放他们需要释放的资源
     *
     * @param recyclerView
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {

        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof BaseHolder) {
                ((BaseRecyclerViewHolder) viewHolder).onRelease();
            }
        }
    }
    /**
     * ClassName: OnItemClickListener<p>
     * Author:oubowu<p>
     * Fuction: 点击长按的接口<p>
     * CreateDate:2016/2/14 1:48<p>
     * UpdateUser:<p>
     * UpdateDate:<p>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(T Data,View view, int position);
        void onItemLongClick(T Data,View view, int position);
    }
}
