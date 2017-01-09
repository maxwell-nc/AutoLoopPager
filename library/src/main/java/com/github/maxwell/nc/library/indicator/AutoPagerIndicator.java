package com.github.maxwell.nc.library.indicator;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.maxwell.nc.library.R;
import com.github.maxwell.nc.library.adapter.BaseLoopPagerAdapter;
import com.github.maxwell.nc.library.pager.LoopViewPager;
import com.github.maxwell.nc.library.util.DensityUtilLite;

/**
 * 页面指示器
 */
public class AutoPagerIndicator extends LinearLayout {

    /**
     * 指示点数量
     */
    private int pointCount = 0;

    /**
     * 当前指示位置
     */
    private int currentPosition = 0;

    /**
     * 圆点的大小
     */
    private static final int POINT_SIZE = 5;

    /**
     * 圆点的间隔
     */
    private static final int POINT_MARGIN = 2;


    public AutoPagerIndicator(Context context) {
        super(context);
    }

    public AutoPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 创建圆点View
     */
    private ImageView createPointView() {
        Context context = getContext();

        int size = DensityUtilLite.dp2px(context, POINT_SIZE);
        int margin = DensityUtilLite.dp2px(context, POINT_MARGIN);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
        lp.setMargins(margin, margin, 0, 0);

        ImageView pointView = new ImageView(context);
        pointView.setLayoutParams(lp);
        pointView.setFocusableInTouchMode(false);
        pointView.setBackgroundResource(R.drawable.point_default);

        return pointView;
    }

    /**
     * 设置指示器
     *
     * @param num 指示器数量
     */
    public void setIndicator(int num) {

        //数量增加
        while (pointCount < num) {
            addView(createPointView());
            pointCount++;
        }

        //数量减少，删除多余的
        while (pointCount > num) {
            removeViewAt(getChildCount() - 1);
            pointCount--;
        }

        //选中第一个
        select(0);

        //只有一个点的时候不显示
        if (pointCount <= 1) {
            setVisibility(GONE);
        }
    }

    /**
     * 选中指示器
     *
     * @param position 指示器位置
     */
    public void select(int position) {

        View newSelectView = getChildAt(position);
        if (newSelectView != null) {

            //去掉之前选中的
            View currentView = getChildAt(currentPosition);
            if (currentView != null) {
                currentView.setBackgroundResource(R.drawable.point_default);
            }

            //选中新的
            newSelectView.setBackgroundResource(R.drawable.point_selected);
            currentPosition = position;
        }

    }

    /**
     * 绑定ViewPager无限滚动
     */
    public void bindViewPager(final ViewPager viewPager) {

        //自动设置数值
        final PagerAdapter adapter = viewPager.getAdapter();
        adapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {
                int count = adapter.getCount();
                if (count > 0) {
                    if (adapter instanceof BaseLoopPagerAdapter && count > 2) {
                        setIndicator(count - 2);
                    }
                    adapter.unregisterDataSetObserver(this);
                }
            }

        });

        //监听页面变化
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                //支持无限滚动
                if (viewPager instanceof LoopViewPager) {

                    if (position == 0) {//第一个View
                        select(pointCount - 1);
                    } else if (position == pointCount + 1) {//最后一个View
                        select(0);
                    } else {
                        select(position - 1);
                    }

                } else {
                    select(position);
                }

            }

        });
    }

}
