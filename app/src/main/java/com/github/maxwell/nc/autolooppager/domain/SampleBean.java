package com.github.maxwell.nc.autolooppager.domain;

import java.io.Serializable;

/**
 * 例子数据
 */
public class SampleBean implements Serializable {

    /**
     * 颜色
     */
    private int color;

    public SampleBean(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
