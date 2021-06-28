package com.alanstd_3.alanbujo.general

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class ColorUtils {

    companion object {
        fun getColorHex(resource: Int, c: Context): String {
            var ret = ""

            try {
                val hex = "#" + Integer.toHexString(
                    ContextCompat.getColor(c, resource)
                ).toString()
                ret = hex
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }

            return ret
        }

        fun getColorHex(@ColorInt resource: Int): String = "#" + Integer.toHexString(resource).toString()
    }
}