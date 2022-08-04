package com.sena.accessibilityservicetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sena.accessibilityservicetest.databinding.ActivitySettingBinding
import com.sena.accessibilityservicetest.utils.FloatingWindowManager
import com.sena.accessibilityservicetest.utils.SpUtils

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private var clickDuration: Int = 0
    private var touchDuration: Int = 0
    private var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClick()
    }

    private fun initView() {
        clickDuration = SpUtils.getIntValue(this, SpUtils.CLICK_DURATION)
        touchDuration = SpUtils.getIntValue(this, SpUtils.TOUCH_DURATION)
        clickCount = SpUtils.getIntValue(this, SpUtils.CLICK_COUNT)

        binding.cdEdit.setText(clickDuration.toString())
        binding.tdEdit.setText(touchDuration.toString())
        binding.ccEdit.setText(clickCount.toString())
    }

    private fun initClick() {

        binding.save.setOnClickListener {
            val cdStr = binding.cdEdit.text.toString()
            if (!(cdStr.matches(Regex("^[1-9]\\d*\$")) && cdStr.toInt() >= FloatingWindowManager.MIN_CLICK_DURATION)) {
                Toast.makeText(this, "点击周期非法输入", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tdStr = binding.tdEdit.text.toString()
            if (!(tdStr.matches(Regex("^[1-9]\\d*\$")) && tdStr.toInt() >= FloatingWindowManager.MIN_TOUCH_DURATION)) {
                Toast.makeText(this, "触摸时长非法输入", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val ccStr = binding.ccEdit.text.toString()
            if (!(ccStr.matches(Regex("^[1-9]\\d*|0\$")) && ccStr.toInt() >= FloatingWindowManager.MIN_CLICK_COUNT)) {
                Toast.makeText(this, "执行次数非法输入", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            save()
        }
    }

    private fun save() {
        SpUtils.setValue(this, SpUtils.CLICK_DURATION, binding.cdEdit.text.toString().toInt())
        SpUtils.setValue(this, SpUtils.TOUCH_DURATION, binding.tdEdit.text.toString().toInt())
        SpUtils.setValue(this, SpUtils.CLICK_COUNT, binding.ccEdit.text.toString().toInt())
    }
}