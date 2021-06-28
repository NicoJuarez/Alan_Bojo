package com.alanstd_3.alanbujo.ui.dialogs

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.general.ColorUtils
import petrov.kristiyan.colorpicker.ColorPicker

class ColorDialog(activity: Activity): ColorPicker(activity) {

    init {
        val listColors = listOf(
            R.color.alan_aqua, R.color.alan_yellow,
            R.color.alan_green, R.color.alan_pink,
            R.color.alan_blue, R.color.alan_orange,
            R.color.alan_red, R.color.alan_light_blue,
            R.color.alan_grape
        )

        setColors(getListColors(listColors))
        setColorButtonDrawable(R.drawable.shape_circle)

//        setOnFastChooseColorListener(object :
//            ColorPicker.OnFastChooseColorListener {
//            override fun setOnFastChooseColorListener(position: Int, color: Int) {
//
//                activity.applicationContext?.let { c ->
//                    binding.selectedColor.backgroundTintList =
//                        ContextCompat.getColorStateList(c, listColors[position])
//                    hexColor = ColorUtils.getColorHex(
//                        ContextCompat.getColor(c, listColors[position])
//                    )
//                    Log.d(AddWorkSpaceDialog.TAG, "setOnFastChooseColorListener: $hexColor")
//                }
//                mPicker.dismissDialog()
//
//            }
//
//            override fun onCancel() {
//                mPicker.dismissDialog()
//            }
//        })
    }

    private fun getListColors(vararg colors: Int): ArrayList<String> {
        val listColors = mutableListOf<Int>()
        for (color in colors)
            listColors.add(color)

        return getListColors(listColors)
    }

    private fun getListColors(listColors: List<Int>): ArrayList<String> {
        val list = ArrayList<String>()

        dialogViewLayout.context?.let { c ->

            for (color in listColors) {
                try {
//                    val hex = "#" + Integer.toHexString(
//                        ContextCompat.getColor(c, color)
//                    ).toString()
                    val hex = ColorUtils.getColorHex(color, c)
                    if (hex.isNotBlank())
                        list.add(hex)
                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                }
            }
        }

        return list
    }

}