package com.github.maxwell.nc.autolooppager.adapter;

import android.content.Context;
import android.view.View;

import com.github.maxwell.nc.autolooppager.R;
import com.github.maxwell.nc.autolooppager.domain.SampleBean;
import com.github.maxwell.nc.library.adapter.BaseLoopPagerAdapter;

/**
 * 例子页面适配器
 */
public class SamplePagerAdapter extends BaseLoopPagerAdapter<SampleBean> {

    public SamplePagerAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_view_pager;
    }

    @Override
    protected void onInit(View root, int position, SampleBean data) {
        root.findViewById(R.id.iv_ad).setBackgroundColor(data.getColor());
    }

}
