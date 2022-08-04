package com.sena.accessibilityservicetest.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SpUtils {

    private const val DATA_NAME = "accessibility_params"
    const val CLICK_DURATION = "click_duration"
    const val TOUCH_DURATION = "touch_duration"
    const val CLICK_COUNT = "click_count"

    fun setValue(context: Context, key: String, value: Any) {
        val editor = getSp(context, DATA_NAME)?.edit() ?: return
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> Log.e("SpUtils: ", "setValue() 不支持的数据类型${value.javaClass.name}")
        }
        editor.apply()
    }

    fun getStringValue(context: Context, key: String): String? {
        return getSp(context, DATA_NAME)?.getString(key, null)
    }

    fun getIntValue(context: Context, key: String): Int {
        return getSp(context, DATA_NAME)?.getInt(key, 0) ?: 0
    }

    fun getLongValue(context: Context, key: String): Long {
        return getSp(context, DATA_NAME)?.getLong(key, 0) ?: 0
    }

    fun getFloatValue(context: Context, key: String): Float {
        return getSp(context, DATA_NAME)?.getFloat(key, 0f) ?: 0f
    }

    fun getBooleanValue(context: Context, key: String): Boolean {
        return getSp(context, DATA_NAME)?.getBoolean(key, false) ?: false
    }


    private fun getSp(context: Context, name: String): SharedPreferences? {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}