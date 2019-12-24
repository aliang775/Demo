package com.mi.earing.base.utils

import android.content.Context
import android.os.StatFs
import android.support.v4.content.ContextCompat
import java.io.File

/**
 *  Create by lei.tong on 2018/12/1.
 **/
object FileSizeUtils {
    private const val GB = 1024 * 1024 * 1024L//定义GB的计算常量
    private const val MB = 1024 * 1024L//定义MB的计算常量
    private const val KB = 1024L//定义KB的计算常量


    fun getSize(size: Long): String {
        return when {
            size > GB -> "${String.format("%.1f", (size.toFloat() / GB))}G"
            size > MB -> "${String.format("%.1f", (size.toFloat() / MB))}M"
            size > KB -> "${String.format("%.1f", (size.toFloat() / KB))}K"
            else -> "${size}B"
        }
    }

    fun getFreeSize(): String {
        return getSize(getFreeSpaceBytes())
    }

    fun getFreeSpaceBytes(): Long {
        return StatFs(getExternalStorageFiles(Utils.getApp())[0].path).availableBytes
    }

    private fun getExternalStorageFiles(context: Context): List<File> {
        val list = ArrayList<File>()
        val files = ContextCompat.getExternalFilesDirs(context.applicationContext, null)
        for (file in files) {
            if (file != null) {
                list.add(file)
            }
        }
        return list
    }
}