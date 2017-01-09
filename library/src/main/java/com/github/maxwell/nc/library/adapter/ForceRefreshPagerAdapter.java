package com.github.maxwell.nc.library.adapter;

import android.support.v4.view.PagerAdapter;

/**
 * notifyDataSetChanged方法强制刷新所有页面的PagerAdapter
 */
public abstract class ForceRefreshPagerAdapter extends PagerAdapter {

    /**
     * 页面数，用于notify强制刷新全部页面
     */
    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        //标记需要重绘制
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;//表示元素已经失效
        }
        return super.getItemPosition(object);
    }

}
