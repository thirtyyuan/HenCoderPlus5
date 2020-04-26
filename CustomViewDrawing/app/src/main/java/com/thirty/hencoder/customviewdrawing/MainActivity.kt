package com.thirty.hencoder.customviewdrawing

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.drawToBitmap
import com.thirty.hencoder.customviewdrawing.view.PROVINCES
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animator = ObjectAnimator.ofInt(view, "index", PROVINCES.size - 1)
        animator.startDelay = 200
        animator.duration = 5000
        animator.start()

    }
}