package com.mi.earing.base.utils

import android.os.Build
import android.text.TextUtils
import com.mi.earing.base.log.FmLog
import java.lang.reflect.InvocationTargetException
import java.util.regex.Pattern

/**
 *  Create by lei.tong on 2019/1/21.
 **/
class BrandUtils {
    companion object {
        private const val TAG = "BrandUtils"
        private var sEMUIVersionName = -1
        private const val EMUI_PROPERTIES = "ro.build.hw_emui_api_level"
        /**
         * 获得华为EMUI版本号
         *
         * @return 华为EMUI版本号，未取到时返回-1
         */
        fun getEMUIVersion(): Int {
            if (sEMUIVersionName <= 0) {
                var version = getProperties(EMUI_PROPERTIES)
                if (!TextUtils.isEmpty(version)) {
                    version = version!!.replace("\\s+".toRegex(), "")
                    if (isNumeric(version)) {
                        try {
                            sEMUIVersionName = Integer.parseInt(version)
                        } catch (e: NumberFormatException) {
                            FmLog.e(TAG, e)
                        }

                    }
                }
            }
            return sEMUIVersionName
        }

        private fun isNumeric(str: String): Boolean {
            val pattern = Pattern.compile("[0-9]*")
            return pattern.matcher(str).matches()
        }

        private fun getProperties(key: String): String? {
            var value: String? = null
            try {
                val method = Build::class.java.getDeclaredMethod("getString", String::class.java)
                method.isAccessible = true
                value = method.invoke(Build(), key) as String
            } catch (e: NoSuchMethodException) {
                FmLog.e(TAG, e)
            } catch (e: InvocationTargetException) {
                FmLog.e(TAG, e)
            } catch (e: IllegalAccessException) {
                FmLog.e(TAG, e)
            }

            return value
        }
    }


}