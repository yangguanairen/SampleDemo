package com.sena.accessibilityservicetest

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.sena.accessibilityservicetest.utils.AccessibilityOperate

class MyAccessibilityService : AccessibilityService() {

    companion object {
        const val TAG = "AccessibilityService"
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "onServiceConnected: ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }
    
    
    override fun onAccessibilityEvent(event: AccessibilityEvent) {

//        val packageName = event.packageName.toString()
//        val eventType = event.eventType
//        val mAccessibilityService = this

        // 更新service
        AccessibilityOperate.getInstance().updateEvent(this, event)

    }

    override fun onInterrupt() {

    }

}