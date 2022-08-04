package com.sena.accessibilityservicetest.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.ImageView
import com.sena.accessibilityservicetest.R

@SuppressLint("InflateParams")
class ControlFloatingWindow(context: Context) {

    private lateinit var view: View
    private val addIv: ImageView
    private val deleteIv: ImageView
    private val startIv: ImageView
    private val stopIv: ImageView

    private val mWindowManager: WindowManager
    private lateinit var mFloatingParams: WindowManager.LayoutParams

    init {
        if (!this::view.isInitialized) {
            view = LayoutInflater.from(context).inflate(R.layout.floating_window_control, null)
        }
        addIv = view.findViewById(R.id.add)
        deleteIv = view.findViewById(R.id.delete)
        startIv = view.findViewById(R.id.start)
        stopIv = view.findViewById(R.id.stop)

        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        initWindowParams()
    }

    private fun initWindowParams() {
        val layoutParams = WindowManager.LayoutParams()
        // 设置悬浮窗口类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        // 设置悬浮窗口属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        // 设置悬浮窗口透明
        layoutParams.format = PixelFormat.TRANSLUCENT
        // 设置悬浮窗口数据
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        // 设置悬浮窗显示位置
        layoutParams.gravity = Gravity.CENTER

        mFloatingParams = layoutParams
        view.setOnTouchListener(FloatingWindowListener(mWindowManager, view, mFloatingParams))
    }

    fun show() {
        mWindowManager.addView(view, mFloatingParams)
    }

    fun setAddClickListener(listener: View.OnClickListener) {
        addIv.setOnClickListener(listener)
    }

    fun setDeleteClickListener(listener: View.OnClickListener) {
        deleteIv.setOnClickListener(listener)
    }

    fun setStartClickListener(listener: View.OnClickListener) {
        startIv.setOnClickListener(listener)
    }

    /**
     * Accessibility.dispatchGesture()执行期间其他点击事件被强行取消，导致点击暂停可能无法生效
     * 触摸事件不会被取消
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setStopTouchListener(l: View.OnTouchListener) {
        stopIv.setOnTouchListener(l)
    }

}