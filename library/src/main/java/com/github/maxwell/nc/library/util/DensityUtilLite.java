package com.github.maxwell.nc.library.util;

import android.content.Context;

/**
 * Android设备密度工具类
 */
public class DensityUtilLite {

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
