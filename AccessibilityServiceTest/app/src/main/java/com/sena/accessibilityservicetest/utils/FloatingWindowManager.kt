package com.sena.accessibilityservicetest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import com.sena.accessibilityservicetest.widget.ControlFloatingWindow
import com.sena.accessibilityservicetest.widget.NormalFloatingWindow
import kotlinx.coroutines.*

object FloatingWindowManager {


    private val normalFwList = arrayListOf<NormalFloatingWindow>()
    private lateinit var job: Job
    private var isInit = false

    const val MIN_CLICK_DURATION = 50
    const val MIN_TOUCH_DURATION = 10
    const val MIN_CLICK_COUNT = 0
    private const val MAX_NORMAL_WINDOW_COUNT = 5


    @SuppressLint("ClickableViewAccessibility")
    fun openControlWindow(context: Context) {
        val controlFw = ControlFloatingWindow(context)

        controlFw.setAddClickListener {
            createNormalWindow(context)
        }
        controlFw.setDeleteClickListener {
            deleteNormalWindow()
        }
        controlFw.setStartClickListener {
            startJob(context)
        }
        controlFw.setStopTouchListener { _, _ ->
            stopJob()
            false
        }

        controlFw.show()
        isInit = true
    }

    private fun createNormalWindow(context: Context) {
        if (normalFwList.size < MAX_NORMAL_WINDOW_COUNT) {
            val normalFw = NormalFloatingWindow(context)
            normalFwList.add(normalFw)
            normalFw.show()
        } else {
            try {
                Toast.makeText(context, "无法添加更多!!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {

            }
        }
    }

    private fun deleteNormalWindow() {
        if (normalFwList.isEmpty()) return

        val normalFw = normalFwList.removeLast()
        normalFw.remove()
    }

    private fun startJob(context: Context) {

        // 防止死循环
        if (normalFwList.isEmpty() || (this::job.isInitialized && job.isActive)) {
            return
        }

        val xList = arrayListOf<Int>()
        val yList = arrayListOf<Int>()
        normalFwList.forEach {
            val position = it.getCenterPos()
            xList.add(position[0])
            yList.add(position[1])
        }

        val clickDuration = SpUtils.getIntValue(context, SpUtils.CLICK_DURATION).toLong()
        val touchDuration = SpUtils.getIntValue(context, SpUtils.TOUCH_DURATION).toLong()
        val clickCount = SpUtils.getIntValue(context, SpUtils.CLICK_COUNT)
        val delayTime = 20L  // 模拟点击的延时执行时间，不能为0，否则点击事件会被NormalFw消费

        job = MainScope().launch {
            if (clickCount == 0) {
                while (true) {
                    normalFwList.forEachIndexed { i, normalFw ->
                        delay(clickDuration)
                        normalFw.setVisibility(View.GONE)
                        AccessibilityOperate.getInstance().performClick(xList[i], yList[i], delayTime, touchDuration)
                        delay(delayTime)
                        normalFw.setVisibility(View.VISIBLE)
                    }
                }
            } else {
                for (i in 1..clickCount) {
                    normalFwList.forEachIndexed { index, normalFw ->
                        delay(clickDuration)
                        normalFw.setVisibility(View.GONE)
                        AccessibilityOperate.getInstance().performClick(xList[index], yList[index], delayTime, touchDuration)
                        delay(delayTime)
                        normalFw.setVisibility(View.VISIBLE)
                    }
                }
            }
        }
    }

    /**
     * 停止循环，所有NormalFw设置为可见
     * 停止时可能有些NormalFw处于不可见状态
     */
    private fun stopJob() {
        if (this::job.isInitialized) {
            job.cancel()
            normalFwList.forEach {
                it.setVisibility(View.VISIBLE)
            }
        }
    }

    fun isInit(): Boolean {
        return isInit
    }
}