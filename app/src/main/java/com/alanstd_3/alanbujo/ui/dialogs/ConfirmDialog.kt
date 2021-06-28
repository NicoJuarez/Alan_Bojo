package com.alanstd_3.alanbujo.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.databinding.DialogConfirmBinding
import com.alanstd_3.alanbujo.ui.dialogs.ConfirmDialog.OnConfirmListener

class ConfirmDialog : BaseDialog() {

    private var binding: DialogConfirmBinding? = null

    var title: String = ""
        set(title) {
            field = title
            binding?.title?.text = title
        }
    var content: String = ""
        set(content) {
            field = content
            binding?.content?.text = content
        }

    //    var confirmListener: ConfirmListener? = null
    var confirmListener: OnConfirmListener? = null
        set(listener) {
            field = listener
        }
    var confirmColor: Int = Color.BLACK
        set(color) {
            field = color
            binding?.confirmButtom?.backgroundTintList = ColorStateList.valueOf(color)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogConfirmBinding.bind(
            inflater.inflate(R.layout.dialog_confirm, container, false)
        )

        configureButtons()
        configureDialog()

        return binding?.root
    }

    private fun configureButtons() {
        binding?.cancelButtom?.setOnClickListener { dismiss() }
        binding?.confirmButtom?.setOnClickListener {
            confirmListener?.confirm()
            dismiss()
        }
    }

    private fun configureDialog() {
        binding?.apply {
            title.text = this@ConfirmDialog.title
            content.text = this@ConfirmDialog.content
            confirmButtom.backgroundTintList =
                ColorStateList.valueOf(this@ConfirmDialog.confirmColor)
        }
    }

    fun confirmListener(value: () -> Unit) {
        this.confirmListener = OnConfirmListener {
            value()
        }
    }


    fun interface OnConfirmListener {
        fun confirm()
    }

}
