package com.sena.accessibilityservicetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sena.accessibilityservicetest.databinding.ActivityMainBinding
import com.sena.accessibilityservicetest.utils.AccessibilityUtils
import com.sena.accessibilityservicetest.utils.FloatingWindowManager
import com.sena.accessibilityservicetest.utils.SpUtils


/**
 * 无法把点击事件传递给其他视图
 * 悬浮窗搭载在window上
 *
 * 可能需要的知识：Android试图结构，事件分发机制
 *
 * https://blog.csdn.net/sqlaowen/article/details/85257169
 */


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpData()
        initClick()
    }

    private fun initClick() {
        binding.goToAccessibilitySetting.setOnClickListener {
            AccessibilityUtils.goToAccessibilitySetting(this)
        }
        binding.goToOverlaySetting.setOnClickListener {
            AccessibilityUtils.goToOverlaySetting(this)
        }
        binding.setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        binding.start.setOnClickListener {
            start()
        }
    }

    private fun initSpData() {
        val clickDuration = SpUtils.getIntValue(this, SpUtils.CLICK_DURATION)
        if (clickDuration == 0) SpUtils.setValue(this, SpUtils.CLICK_DURATION, FloatingWindowManager.MIN_CLICK_DURATION)
        val touchDuration = SpUtils.getIntValue(this, SpUtils.TOUCH_DURATION)
        if (touchDuration == 0) SpUtils.setValue(this, SpUtils.TOUCH_DURATION, FloatingWindowManager.MIN_TOUCH_DURATION)
        val clickCount = SpUtils.getIntValue(this, SpUtils.CLICK_COUNT)
        if (clickCount == 0) SpUtils.setValue(this, SpUtils.CLICK_COUNT, FloatingWindowManager.MIN_CLICK_COUNT)
    }

    private fun start() {
        if (!AccessibilityUtils.isAccessibilitySettingsOn(this)) {
            Toast.makeText(this, "需要辅助功能权限", Toast.LENGTH_SHORT).show()
        } else if (!AccessibilityUtils.isOverlaySettingsOn(this)) {
            Toast.makeText(this, "需要悬浮窗权限", Toast.LENGTH_SHORT).show()
        } else {
            if (!FloatingWindowManager.isInit()) {
                FloatingWindowManager.openControlWindow(this)
            }
        }
    }
}