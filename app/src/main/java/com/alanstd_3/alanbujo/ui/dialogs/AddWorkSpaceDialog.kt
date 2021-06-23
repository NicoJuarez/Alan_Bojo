package com.alanstd_3.alanbujo.ui.dialogs

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

class AddWorkSpaceDialog : BaseDialog() {

    private lateinit var binding: DialogAddWorkSpaceBinding
    var listener: OnSubmitListener = object : OnSubmitListener {
        override fun onSubmitClick(work: Work) {
            dialog?.dismiss()
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
//                mPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
//                    override fun onChooseColor(position: Int, color: Int) {
//
//                    }
//
//                    override fun onCancel() {
//
//                    }
//                })

                mPicker.setOnFastChooseColorListener(object :
                    ColorPicker.OnFastChooseColorListener {
                    override fun setOnFastChooseColorListener(position: Int, color: Int) {
                        binding.selectedColor.backgroundTintList =
                            ContextCompat.getColorStateList(context!!, listColors[position])
                        hexColor = ColorUtils.getColorHex(listColors[position], context!!)
                        Log.d(TAG, "setOnFastChooseColorListener: $hexColor")
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
            val work = Work(
                binding.inputTitle.text.toString(),
                binding.inputSubtitle.text.toString(),
                ""
            )
            work.color = hexColor

            listener.onSubmitClick(work)
            dialog?.dismiss()
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

//    fun setOnClickListener(listener: View.OnClickListener?) {
//        if (listener == null) {
//            binding.submitButtom.setOnClickListener {
//                dialog?.dismiss()
//            }
//        } else {
//            binding.submitButtom.setOnClickListener(listener)
//        }
//    }

    interface OnSubmitListener {
        fun onSubmitClick(work: Work)
    }


}