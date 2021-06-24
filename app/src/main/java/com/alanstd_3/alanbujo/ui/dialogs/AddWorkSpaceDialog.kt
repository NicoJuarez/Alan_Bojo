package com.alanstd_3.alanbujo.ui.dialogs

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.entities.Work
import com.alanstd_3.alanbujo.databinding.DialogAddWorkSpaceBinding
import com.alanstd_3.alanbujo.general.ColorUtils
import petrov.kristiyan.colorpicker.ColorPicker
import java.lang.IllegalArgumentException

class AddWorkSpaceDialog : BaseDialog() {

    private lateinit var binding: DialogAddWorkSpaceBinding
    var listener: OnSubmitListener = object : OnSubmitListener {
        override fun onSubmitClick(work: Work) {
            dialog?.dismiss()
        }

        override fun canSaveWork(title: String): Boolean {
            return true
        }
    }
    private var hexColor = ""

    companion object {
        const val TAG = "WS_DIALOG::"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddWorkSpaceBinding.bind(
            inflater.inflate(R.layout.dialog_add_work_space, container, false)
        )

        configureButtons()
        setDefaultColor()

        return binding.root
    }

    private fun configureButtons() {
        binding.addColorButton.setOnClickListener {
            activity?.let {
                val mPicker = ColorPicker(it)

                val listColors = listOf(
                    R.color.alan_aqua, R.color.alan_yellow,
                    R.color.alan_green, R.color.alan_pink
                )
                mPicker.setColors(getListColors(listColors))

                mPicker.setOnFastChooseColorListener(object :
                    ColorPicker.OnFastChooseColorListener {
                    override fun setOnFastChooseColorListener(position: Int, color: Int) {

                        context?.let { c ->
                            binding.selectedColor.backgroundTintList =
                                ContextCompat.getColorStateList(c, listColors[position])
                            hexColor = ColorUtils.getColorHex(
                                ContextCompat.getColor(c, listColors[position])
                            )
                            Log.d(TAG, "setOnFastChooseColorListener: $hexColor")
                        }
                        mPicker.dismissDialog()

                    }

                    override fun onCancel() {
                        mPicker.dismissDialog()
                    }
                })

                mPicker.show()
            }
        }

        binding.submitButtom.setOnClickListener {
            if (listener.canSaveWork(binding.inputTitle.text.toString())) {
                val work = Work(
                    binding.inputTitle.text.toString(),
                    binding.inputSubtitle.text.toString(),
                    ""
                )
                work.color = hexColor

                listener.onSubmitClick(work)
                dialog?.dismiss()

            } else {
                setErrorTitle(getString(R.string.text_error_title_exists))
            }
        }
    }

    private fun getListColors(vararg colors: Int): ArrayList<String> {
        val listColors = mutableListOf<Int>()
        for (color in colors)
            listColors.add(color)

        return getListColors(listColors)
    }

    private fun getListColors(listColors: List<Int>): ArrayList<String> {
        val list = ArrayList<String>()

        context?.let { c ->

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

    private fun setErrorTitle(error: String?) {
        binding.inputTitle.error = error
        binding.inputTitle.requestFocus()
    }

    private fun setDefaultColor() {
        context?.let { c ->
            val color = ContextCompat.getColorStateList(c, R.color.alan_primary)
            color?.let { cl ->
                binding.selectedColor.backgroundTintList = cl
                hexColor = ColorUtils.getColorHex(cl.defaultColor)
            }

        }
    }

    interface OnSubmitListener {
        fun onSubmitClick(work: Work)
        fun canSaveWork(title: String): Boolean
    }


}