package com.mi.earing.base.utils

import android.content.Context
import android.os.Parcelable
import com.mi.earing.base.log.FmLog
import com.tencent.mmkv.MMKV

/**
 *  Create by lei.tong on 2019/1/15.
 **/
class SpUtils {

    companion object {
        private const val TAG = "SpUtils"
        private lateinit var mmkv: MMKV

        fun init(context: Context) {
            val rootDir = MMKV.initialize(context)
            FmLog.i(TAG, "MMKV dir: $rootDir")
            mmkv = MMKV.defaultMMKV()
        }

        fun put(key: String, value: Boolean) {
            mmkv.encode(key, value)
        }

        fun getBool(key: String, defaultValue: Boolean = false): Boolean {
            return mmkv.decodeBool(key, defaultValue)
        }

        fun put(key: String, value: Int) {
            mmkv.encode(key, value)
        }

        fun getInt(key: String, defaultValue: Int = 0): Int {
            return mmkv.decodeInt(key, defaultValue)
        }

        fun put(key: String, value: Long) {
            mmkv.encode(key, value)
        }

        fun getLong(key: String, defaultValue: Long = 0): Long {
            return mmkv.decodeLong(key, defaultValue)
        }

        fun put(key: String, value: Float) {
            mmkv.encode(key, value)
        }

        fun getFloat(key: String, defaultValue: Float = 0.0f): Float {
            return mmkv.decodeFloat(key, defaultValue)
        }

        fun put(key: String, value: Double) {
            mmkv.encode(key, value)
        }

        fun getDouble(key: String, defaultValue: Double = 0.0): Double {
            return mmkv.decodeDouble(key, defaultValue)
        }

        @JvmStatic
        fun put(key: String, value: String) {
            mmkv.encode(key, value)
        }

        @JvmStatic
        fun getString(key: String, defaultValue: String = ""): String {
            return mmkv.decodeString(key, defaultValue)
        }

        fun put(key: String, value: Set<String>) {
            mmkv.encode(key, value)
        }

        fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String> {
            return mmkv.decodeStringSet(key, defaultValue)
        }

        fun put(key: String, value: ByteArray) {
            mmkv.encode(key, value)
        }

        fun getBytes(key: String): ByteArray {
            return mmkv.decodeBytes(key)
        }

        fun put(key: String, value: Parcelable) {
            mmkv.encode(key, value)
        }

        fun <T: Parcelable> getParcelable(key: String, clazz: Class<T>): T {
            return mmkv.decodeParcelable(key, clazz)
        }

        fun containsKey(key: String): Boolean {
            return mmkv.containsKey(key)
        }

        fun allKeys(): Array<String> {
            return mmkv.allKeys()
        }

        fun count(): Long {
            return mmkv.count()
        }

        fun totalSize(): Long {
            return mmkv.totalSize()
        }

        fun removeValueForKey(key: String) {
            mmkv.removeValueForKey(key)
        }

        fun removeValuesForKeys(keys: Array<String>) {
            mmkv.removeValuesForKeys(keys)
        }

        fun clearAll() {
            mmkv.clearAll()
        }
    }
}