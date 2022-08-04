package com.sena.accessibilityservicetest.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.ImageView
import androidx.annotation.Size
import com.sena.accessibilityservicetest.R
import com.sena.accessibilityservicetest.utils.ViewUtils

@SuppressLint("InflateParams")
class NormalFloatingWindow(private val context: Context) {

    private val view: ImageView = ImageView(context)

    private val mWindowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private lateinit var mLayoutParams: WindowManager.LayoutParams

    init {
        view.scaleType = ImageView.ScaleType.CENTER_CROP
        view.setImageResource(R.drawable.ic_click)

        initLayoutParams()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayoutParams() {
        val layoutParams = WindowManager.LayoutParams()

        // 设置悬浮窗口类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        // 设置悬浮窗口属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        // 设置悬浮窗口透明
        layoutParams.format = PixelFormat.TRANSLUCENT
        // 设置悬浮窗口数据
        val size = ViewUtils.dpToPx(context, 50)
        layoutParams.width = size
        layoutParams.height = size
        // 设置悬浮窗显示位置
        layoutParams.gravity = Gravity.CENTER

        mLayoutParams = layoutParams
        view.setOnTouchListener(FloatingWindowListener(mWindowManager, view, mLayoutParams))
    }

    fun show() {
        mWindowManager.addView(view, mLayoutParams)
    }

    fun remove() {
        mWindowManager.removeView(view)
    }

    fun setVisibility(visibility: Int) {
        view.visibility = visibility
    }

    @Size(2)
    fun getCenterPos(): IntArray {
        // 获取view在屏幕中的位置
        // https://blog.csdn.net/qq_39431405/article/details/124888496
        // https://zhuanlan.zhihu.com/p/274321442
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        location[0] += view.width / 2
        location[1] += view.height / 2
        return location
    }
}