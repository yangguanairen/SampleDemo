package com.sena.accessibilityservicetest.utils

import android.content.Context

object ViewUtils {

    fun dpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (scale * dp + 0.5f).toInt()
    }
}