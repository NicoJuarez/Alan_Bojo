package com.alanstd_3.alanbujo.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alanstd_3.alanbujo.R
import com.alanstd_3.alanbujo.database.repository.Repository
import com.alanstd_3.alanbujo.ui.fragments.WorkSpaceFragment
import com.alanstd_3.alanbujo.ui.fragments.entities.WorkSpace

class WorkSpaceActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "_WorkSpc_ACT::"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_space)

        val bundle = intent.extras
        if (bundle != null && bundle.getLong("id") != 0L) {
            setFragment(bundle.getLong("id"))
//            setFragment()
        } else {
            setDefaultFragment()
        }

    }

    private fun setFragment() {
        val ws = WorkSpaceFragment(
            WorkSpace("Fiverr", "General/root/...", "")
        )
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true).add(R.id.container, ws, "ws").commit()
    }

    private fun setFragment(workID: Long) {
        val repository = Repository(applicationContext)
        val work = repository.getWorkByID(workID)
        if (work != null) {
            val ws = WorkSpaceFragment(work)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true).add(R.id.container, ws, "ws").commit()
            Log.d(TAG, "setFragment: creando fragmento: id = $work")

            if (work.color.isNotBlank())
                setStatusBarColor(Color.parseColor(work.color))
            else
                setStatusBarColor(ContextCompat.getColor(applicationContext, R.color.alan_default))

        } else {
            setDefaultFragment()
            setStatusBarColor(ContextCompat.getColor(applicationContext, R.color.alan_default))
        }
    }

    private fun setDefaultFragment() {
        Log.d(TAG, "setDefaultFragment: <Creando Fragmento por Defecto>")
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        window?.let {
            it.statusBarColor = color
        }
    }
}