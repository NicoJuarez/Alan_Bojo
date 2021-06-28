package com.alanstd_3.alanbujo.ui.fragments.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.databinding.FragmentTestBinding
import com.alanstd_3.alanbujo.ui.fragments.GeneralFragment
import java.text.SimpleDateFormat
import java.util.*

class TestFragment : GeneralFragment() {

    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.bind(
            inflater.inflate(R.layout.fragment_test, container, false)
        )

        test()

        return binding.root
    }

    private fun test() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEEE yyyyMMdd - hh:mm:ss", Locale.US)

        val calendar2 = Calendar.getInstance()
        calendar2.set(2021, 5, 29)
        calendar2.set(Calendar.HOUR, 10)
//        calendar2.timeInMillis = 1656509559404L
//        val dbDate = "20210629"

        val day1 = calendar.get(Calendar.DAY_OF_WEEK)
        val day2 = calendar2.get(Calendar.DAY_OF_WEEK)


//        val output = "${sdf.format(calendar.time)}\n${sdf.format(calendar2.time)}\n\nComparison: ${calendar.compareTo(calendar2)}"
//        val output = "${date.time}\n${calendar2.timeInMillis}\n\nComparison: ${calendar.compareTo(calendar2)}"
        val output = "${sdf.format(calendar.time)}\n${sdf.format(calendar2.time)}\n\nComparison: ${day1 == day2}"

        binding.output.text = output


    }

}