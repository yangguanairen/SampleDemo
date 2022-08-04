package com.sena.accessibilityservicetest.utils

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import com.sena.accessibilityservicetest.MyAccessibilityService

class AccessibilityOperate private constructor() {

    private var mAccessibilityService: MyAccessibilityService? = null
    private lateinit var mAccessibilityEvent: AccessibilityEvent

    companion object {

        private var mInstance: AccessibilityOperate? = null

        fun getInstance(): AccessibilityOperate {
            if (mInstance == null) {
                synchronized(AccessibilityOperate::class.java) {
                    if (mInstance == null) {
                        mInstance = AccessibilityOperate()
                    }
                }
            }
            return mInstance!!
        }
    }

    fun updateEvent(service: AccessibilityService?, event: AccessibilityEvent?) {
        if (service != null && mAccessibilityService == null) {
            mAccessibilityService = service as MyAccessibilityService
        }
        event?.let {
            mAccessibilityEvent = it
        }
    }

    /**
     * 在屏幕上模拟一次点击（只包括down和up事件）
     * @param x 点击位置的x轴坐标
     * @param y 点击位置y轴坐标
     * @param startTime 延时多少时间进行点击，不能为0，否则事件会被NormalFloatingWindow消费
     * @param touchDuration 从down到up的时间
     */
    fun performClick(x: Int, y: Int, startTime: Long, touchDuration: Long) {
        val builder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(x.toFloat(), y.toFloat())
        path.lineTo(x.toFloat(), y.toFloat())
        builder.addStroke(GestureDescription.StrokeDescription(path, startTime, touchDuration))
        val gesture = builder.build()
        mAccessibilityService?.dispatchGesture(gesture, object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                super.onCompleted(gestureDescription)
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                super.onCancelled(gestureDescription)
            }
        }, null)
    }
}