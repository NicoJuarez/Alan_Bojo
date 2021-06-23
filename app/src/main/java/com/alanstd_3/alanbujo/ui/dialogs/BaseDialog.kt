package com.alanstd_3.alanbujo.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class BaseDialog : DialogFragment() {

    override fun onStart() {
        super.onStart()

        dialog?.window?.let {

            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            it.setWindowAnimations(animationStyle)

        }
    }




}