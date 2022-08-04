package com.sena.accessibilityservicetest.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import com.sena.accessibilityservicetest.MyAccessibilityService

object AccessibilityUtils {

    private val ACCESSIBILITY_SERVICE_PATH by lazy {
        MyAccessibilityService::class.java.canonicalName
    }

    /**
     * 跳转辅助功能设置
     */
    fun goToAccessibilitySetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "无法前往，请手动前往", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * 跳转设置悬浮窗权限
     */
    fun goToOverlaySetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "无法前往，请手动前往", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * 判读辅助功能是否开启
     */
    fun isAccessibilitySettingsOn(context: Context): Boolean {

        val contentResolver = context.applicationContext.contentResolver

        var accessibilityEnabled = 0
        try {
            accessibilityEnabled =
                Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }

        val packageName = context.packageName
        val serviceStr = "$packageName/$ACCESSIBILITY_SERVICE_PATH"

        if (1 == accessibilityEnabled) {
            val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')

            Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )?.let {
                mStringColonSplitter.setString(it)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    if (accessibilityService == serviceStr) {
                        return true
                    }
                }
            }
        }

        return false
    }

    /**
     * 判断悬浮窗权限是否开启
     */
    fun isOverlaySettingsOn(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

}