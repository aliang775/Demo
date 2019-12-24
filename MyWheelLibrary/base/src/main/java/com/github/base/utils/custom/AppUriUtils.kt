package com.mi.earing.base.utils.custom

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils

//暂时使用,看看能不能合并
class AppUriUtils {
    companion object {
        fun buildUri(path: String?): Uri? {
            var pathTemp = path
            // path 为空默认去首页
            if (TextUtils.isEmpty(path)) {
                pathTemp = "/view/tabHost"
            }

            val uriBuilder = Uri.parse("erting://main$pathTemp").buildUpon()
            return uriBuilder.build()
        }

        fun convertUriParamToBundle(uri: Uri?): Bundle? {
            if (uri == null) {
                return null
            }

            val bundle = Bundle()
            val names: Set<String> = uri.queryParameterNames
            for (key in names) {
                val value = uri.getQueryParameter(key)
                bundle.putString(key, value)
            }
            return bundle
        }

        fun appendFromToTargetUrl(targetUrl: String, vararg params: String): String {
            var result = targetUrl

            for(it in params){
                result = if (result.contains("?")) {
                    "$result&$it"
                } else {
                    "$result?$it"
                }
            }

            return result
        }
    }
}
