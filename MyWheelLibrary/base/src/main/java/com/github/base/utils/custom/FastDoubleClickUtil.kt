package com.mi.earing.base.utils.custom

import android.view.View

/**
 * View点击的时候判断屏蔽快速点击事件
 */
object FastDoubleClickUtil {

    // 防止快速点击默认等待时长为1000ms
    private var intervalTime: Long = 800L
    private var lastClickTime: Long = 0L

    @Synchronized
    fun isFastDoubleClick(): Boolean {
        var currentTime = System.currentTimeMillis()
        var time = currentTime - lastClickTime

        if (time in 0L..(intervalTime - 1L)) {
            return true
        }
        lastClickTime = currentTime
        return false
    }

    /**
     * 设置默认快速点击事件时间间隔
     *
     * @param intervalTime
     */
    fun setIntervalTime(intervalTime: Long) {
        if (intervalTime > 100L) {
            this.intervalTime = intervalTime
        }
    }
}
