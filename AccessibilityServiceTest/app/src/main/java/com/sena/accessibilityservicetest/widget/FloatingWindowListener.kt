package com.sena.accessibilityservicetest.widget

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlin.math.abs

class FloatingWindowListener(
    private val windowManager: WindowManager,
    private val floatingView: View,
    private val layoutParams: WindowManager.LayoutParams
): View.OnTouchListener {

    private var mTouchStartX: Int = 0
    private var mTouchStartY: Int = 0
    private var mStartX: Int = 0
    private var mStartY: Int = 0

    private var isMove = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isMove = false
                mTouchStartX = event.rawX.toInt()
                mTouchStartY = event.rawY.toInt()
                mStartX = x
                mStartY = y
            }
            MotionEvent.ACTION_MOVE -> {
                val mTouchCurX = event.rawX.toInt()
                val mTouchCurY = event.rawY.toInt()
                layoutParams.x += mTouchCurX - mTouchStartX
                layoutParams.y += mTouchCurY - mTouchStartY
                windowManager.updateViewLayout(floatingView, layoutParams)
                mTouchStartX = mTouchCurX
                mTouchStartY = mTouchCurY

                val deltaX = x - mStartX
                val deltaY = y - mStartY
                if (abs(deltaX) > 5 || abs(deltaY) > 5) {
                    isMove = true
                }
            }
        }

        return isMove
    }
}