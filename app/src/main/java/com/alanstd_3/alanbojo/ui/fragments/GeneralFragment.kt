package com.alanstd_3.alanbojo.ui.fragments

import android.animation.Animator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alanstd_3.alanbojo.R
import com.google.android.material.snackbar.Snackbar
import java.time.Duration

open class GeneralFragment : Fragment() {

    protected fun showDefaultSnackBar(
        message: String,
        view: View,
        duration: Int = Snackbar.LENGTH_INDEFINITE
    ) {

        val snack = Snackbar.make(view, message, duration)

        context?.let {
            snack.setBackgroundTint(ContextCompat.getColor(it, R.color.teal_200))
            snack.setTextColor(ContextCompat.getColor(it, R.color.white))
        }

        snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack.show()
    }

    /**
     * Creado con la finalidad de hacer la vez de Custom Toast._
     */
    protected fun showFunnyMessage(text: String, container: ViewGroup) {

        val tView = layoutInflater.inflate(R.layout.toast_custom, null)

        tView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        tView.alpha = 0f
        tView.animate().apply {
            alpha(1f)
            duration = 1000
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) = Unit

                override fun onAnimationEnd(animation: Animator?) {
                    tView.animate().apply {
                        alpha(0f)
                        duration = 1000
                        startDelay = 1000
                        setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator?) = Unit

                            override fun onAnimationEnd(animation: Animator?) = Unit

                            override fun onAnimationCancel(animation: Animator?) {
                                container.removeView(tView)
                            }

                            override fun onAnimationRepeat(animation: Animator?) = Unit
                        })
                        start()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) = Unit

                override fun onAnimationRepeat(animation: Animator?) = Unit
            })
            start()
        }

        tView.findViewById<TextView>(R.id.text).text = text

        container.addView(tView)
    }

}