package com.github.maxwell.nc.autolooppager.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.maxwell.nc.autolooppager.R;
import com.github.maxwell.nc.autolooppager.adapter.SamplePagerAdapter;
import com.github.maxwell.nc.autolooppager.domain.SampleBean;
import com.github.maxwell.nc.library.indicator.AutoPagerIndicator;
import com.github.maxwell.nc.library.pager.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LoopViewPager mViewPager;
    private AutoPagerIndicator mPagerIndicator;
    private SamplePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        emulateData();
    }

    private void initView() {
        mViewPager = (LoopViewPager) findViewById(R.id.vp_content);
        mPagerIndicator = (AutoPagerIndicator) findViewById(R.id.pi_indicator);
    }

    private void initData() {
        mViewPager.setAdapter(mAdapter = new SamplePagerAdapter(this));
        mPagerIndicator.bindViewPager(mViewPager);
    }

    /**
     * 模拟数据
     */
    private void emulateData() {
        List<SampleBean> sampleList = new ArrayList<>();
        sampleList.add(new SampleBean(Color.RED));
        sampleList.add(new SampleBean(Color.BLUE));
        sampleList.add(new SampleBean(Color.GRAY));
        sampleList.add(new SampleBean(Color.GREEN));
        sampleList.add(new SampleBean(Color.YELLOW));
        mAdapter.showData(sampleList);
    }

}
