package com.github.maxwell.nc.library.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * 基本的循环轮播数据适配器
 */
public abstract class BaseLoopPagerAdapter<T> extends ForceRefreshPagerAdapter {

    protected Context mContext;

    /**
     * 数据列表
     */
    protected List<T> dataList;

    public BaseLoopPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void showData(List<T> dataList) {
        this.dataList = createCycleData(dataList);
        notifyDataSetChanged();
    }

    /**
     * 创建轮播用的List
     */
    private List<T> createCycleData(List<T> dataList) {

        //少于2个则不轮播
        if (dataList.size() < 2) {
            return dataList;
        }

        LinkedList<T> list = new LinkedList<>();
        list.addAll(dataList);

        //取出第一个和最后一个，放在最后和最前，实现过渡效果
        T firstItem = list.getFirst();
        T lastItem = list.getLast();

        //注意不要把上面的放到下面，因为add后list改变
        list.addFirst(lastItem);
        list.addLast(firstItem);

        return list;
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(getItemLayoutId(), container, false);

        T data = dataList.get(position);
        if (data != null) {
            onInit(view, position, data);
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 获取每个页面的布局id
     */
    @LayoutRes
    protected abstract int getItemLayoutId();

    /**
     * 初始化操作
     */
    protected abstract void onInit(View root, int position, T data);

}
