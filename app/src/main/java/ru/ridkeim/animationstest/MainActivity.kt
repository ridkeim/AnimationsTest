package ru.ridkeim.animationstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sunImageView = findViewById<ImageView>(R.id.sun)
        sunImageView.animation = AnimationUtils.loadAnimation(this, R.anim.sun_rise)

        val layout = findViewById<View>(R.id.layout)
        layout.setOnClickListener {
            if(!sunImageView.animation.hasEnded()){
                sunImageView.animation.cancel()
            }
            sunImageView.animation.start()
        }
    }
}