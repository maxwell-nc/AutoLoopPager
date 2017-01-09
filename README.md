# AutoLoopPager

Android Auto Loop ViewPager and Pager Indicator without ANR

# Usage

1.layout
```xml
<com.github.maxwell.nc.library.pager.LoopViewPager
            android:id="@+id/vp_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
```
2.extend adapter
```java
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
```
3.set adapter and show
```java
mViewPager.setAdapter(mAdapter = new SamplePagerAdapter(this));
mAdapter.showData(sampleList);
```

# Hint
you can use AutoPagerIndicator

# see more in project sample
