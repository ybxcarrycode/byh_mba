package com.xszj.mba.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by sy on 2016/3/21.
 * notifyItemChanged(int position) 更新列表position位置上的数据可以调用
 * notifyItemInserted(int position) 列表position位置添加一条数据时可以调用，伴有动画效果
 * notifyItemRemoved(int position) 列表position位置移除一条数据时调用，伴有动画效果
 * notifyItemMoved(int fromPosition, int toPosition) 列表fromPosition位置的数据移到toPosition位置时调用，伴有动画效果
 * notifyItemRangeChanged(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项进行数据刷新
 * notifyItemRangeInserted(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
 * notifyItemRangeRemoved(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    public List<T> list;
    public Context context;
    public LayoutInflater inflater;

    public BaseRecyclerAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    /**
     * 添加单个数据到指定位置
     *
     * @param position
     * @param data
     */
    public void addItem(int position, T data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    /**
     * 替换list
     * @param datas
     */
    public void addItemList(List<T> datas) {
        List<T> listdata = datas;
        list.clear();
        list.addAll(listdata);
        notifyItemRangeInserted(0,listdata.size());
    }
    /**
     * 添加数据到最后
     * 初始list  1 2 3 4 5   传入datas 6 7 8 9 0
     * 添加完毕后list 1,2,3,4,5，6,7,8,9,0
     *
     * @param datas
     */
    public void addItemLast(List<T> datas) {
        list.addAll(datas);
        notifyItemRangeChanged(getItemCount()-1,datas.size());
    }

    /**
     * 添加数据到顶部
     * 初始list  1 2 3 4 5   ，传入datas 6 7 8 9 0
     * 添加完毕后list 6,7,8,9,0,1,2,3,4,5
     *notifyItemRangeInserted(int positionStart, int itemCount)
     * @param datas
     */
//    public void addItemTop(List<T> datas) {
//        for (int i = 0; i < datas.size(); i++) {
//            list.add(0, datas.get(i));
//        }
//        notifyItemRangeInserted(0, datas.size());
//    }

    /**
     * 删除指定位置数据
     *
     * @param position
     */
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除所有数据
     */
    public void removeAll() {
        list.clear();
        notifyItemRangeRemoved(0,list.size()-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取adapter中list的数据
     */
    public List<T> getDatas() {
        return list;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        bindHolder(holder, position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    protected abstract void bindHolder(BaseRecyclerViewHolder viewHolder, int position);

    protected abstract BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType);

    public OnItemClickListenerRecyclerView onItemClickListenerRecyclerView;

    public OnItemClickListenerRecyclerView getOnRecyclerViewItemClickListener() {
        return onItemClickListenerRecyclerView;
    }

    public void setOnItemClickListenerRecyclerView(OnItemClickListenerRecyclerView onItemClickListenerRecyclerView) {
        this.onItemClickListenerRecyclerView = onItemClickListenerRecyclerView;
    }

    /**
     * 类似ListView的 onItemClickListener接口
     */
//    public interface OnItemClickListenerRecyclerView<T> {
    public interface OnItemClickListenerRecyclerView {
        /**
         * Item View发生点击回调的方法
         * @param view     点击的View
         * @param position 具体Item View的索引
         */
//        void onItemClick(View view, int position, T model);
//        void onItemLongClick(View view, int position, T model);
        void onItemClick(View view, int position);

//        void onItemLongClick(View view, int position);
    }
}
