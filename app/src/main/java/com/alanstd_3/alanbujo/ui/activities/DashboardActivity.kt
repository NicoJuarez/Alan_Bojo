package com.alanstd_3.alanbujo.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity() {

    private var _binding: ActivityDashboardBinding? = null
    private val binding: ActivityDashboardBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.alan_light_blue)
    }


}