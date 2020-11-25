package ru.ridkeim.animationstest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Guideline
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

class MainActivity : AppCompatActivity() {
    private lateinit var sunImageView: ImageView
    private var currentAnimatorSet: AnimatorSet? = null
    private lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sunImageView = findViewById<ImageView>(R.id.sun)
        sunImageView.setBackgroundResource(R.drawable.sun)
        animationDrawable = sunImageView.background as AnimationDrawable
        val rootLayout = findViewById<View>(R.id.layout)
        rootLayout.addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _ ->
            createSunAnimation(v)
            currentAnimatorSet?.start()
        }
        sunImageView.setOnClickListener {
            if(currentAnimatorSet?.isPaused!!){
                currentAnimatorSet?.resume()
            }else{
                currentAnimatorSet?.pause()
            }
            if(animationDrawable.isRunning){
                animationDrawable.stop()
            }else{
                animationDrawable.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        animationDrawable.start()
        currentAnimatorSet?.resume()
    }

    override fun onPause() {
        super.onPause()
        animationDrawable.stop()
        currentAnimatorSet?.pause()
    }

    private fun createSunAnimation(v : View){
        if(currentAnimatorSet == null) {
            val visibleRect = Rect()
            v.getGlobalVisibleRect(visibleRect)
            val animateScaleX = ObjectAnimator.ofFloat(sunImageView, View.SCALE_X, 1F, 1.4F)
            val animateScaleY = ObjectAnimator.ofFloat(sunImageView, View.SCALE_Y, 1F, 1.4F)
            val animateAlpha = ObjectAnimator.ofFloat(sunImageView, "alpha", 0.5F, 1F)
            val animateY = ObjectAnimator.ofFloat(sunImageView,View.Y,visibleRect.bottom*0.8F,visibleRect.bottom*0.1F)
            currentAnimatorSet = AnimatorSet()
            currentAnimatorSet?.apply{
                play(animateScaleX).with(animateScaleY).with(animateAlpha).with(animateY)
                duration = 8000
                interpolator = DecelerateInterpolator()
            }
        }
    }
}