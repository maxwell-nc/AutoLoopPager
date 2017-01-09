package com.github.maxwell.nc.library.pager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 带轮播的ViewPager
 * 参考:http://blog.csdn.net/nsw911439370/article/details/52512125
 */
public class LoopViewPager extends ViewPager {

    /**
     * 轮播任务
     */
    private Runnable mLoopTask;

    private PagerAdapterObserver mDataSetObserver;

    /**
     * 要无限循环滑动最少2页,再加上伪头尾就是4页
     */
    public static final int MIN_CYCLE_NUM = 4;

    /**
     * 轮播间隔时间（毫秒）
     */
    public static final int INTERVAL_MILLIS = 5000;

    /**
     * 真实第一个Position
     */
    public static final int REAL_FIRST = 1;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 跳转到第一个
     */
    private void jumpToFirst() {
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(REAL_FIRST, false);
            }
        });
    }

    /**
     * 跳转到最后一个
     */
    private void jumpToLast() {
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(getAdapter().getCount() - 2, false);
            }
        });
    }


    /**
     * 开始轮播
     */
    public void startLoop() {
        stopLoop();
        postDelayed(mLoopTask, INTERVAL_MILLIS);
    }

    /**
     * 停止轮播
     */
    public void stopLoop() {
        removeCallbacks(mLoopTask);
    }

    private int getCount() {
        return getAdapter().getCount();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        mLoopTask = new Runnable() {
            @Override
            public void run() {

                //少于最少循环数量，不循环
                if (getCount() < MIN_CYCLE_NUM) {
                    return;
                }

                //滑动到下一个
                setCurrentItem((getCurrentItem() + 1) % getCount(), (getCurrentItem() + 1) % getCount() != 0);

                //循环
                postDelayed(this, INTERVAL_MILLIS);
            }
        };

        addOnPageChangeListener(new SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //少于最少循环数量，不循环
                if (getCount() < MIN_CYCLE_NUM) {
                    return;
                }

                //判断是否到达边缘
                if (position == (getCount() - 1) && positionOffset == 0) {
                    jumpToFirst();
                } else if (position == 0 && positionOffset == 0) {
                    jumpToLast();
                }
            }

        });

        //注册数据观察者
        mDataSetObserver = new PagerAdapterObserver();
        mDataSetObserver.setAttached(true);
        getAdapter().registerDataSetObserver(mDataSetObserver);

        startCycleAndAutoLoop();
    }

    /**
     * 开始循环并自动轮播
     */
    private void startCycleAndAutoLoop() {
        if (getCount() >= MIN_CYCLE_NUM) {
            setCurrentItem(REAL_FIRST);
            startLoop();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getAdapter() != null) {
            if (!mDataSetObserver.isAttached()) {
                getAdapter().registerDataSetObserver(mDataSetObserver);
                mDataSetObserver.setAttached(true);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getAdapter() != null) {
            if (mDataSetObserver.isAttached()) {
                getAdapter().unregisterDataSetObserver(mDataSetObserver);
                mDataSetObserver.setAttached(false);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //处理轮播事件，按下时不轮播
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopLoop();
        } else if (ev.getAction() == MotionEvent.ACTION_UP
                || ev.getAction() == MotionEvent.ACTION_CANCEL
                || ev.getAction() == MotionEvent.ACTION_OUTSIDE) {
            startLoop();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 数据变化时重新轮播
     */
    private class PagerAdapterObserver extends DataSetObserver {

        private boolean attached = false;

        @Override
        public void onChanged() {
            startCycleAndAutoLoop();
        }

        public void setAttached(boolean attached) {
            this.attached = attached;
        }

        public boolean isAttached() {
            return attached;
        }

    }

}
