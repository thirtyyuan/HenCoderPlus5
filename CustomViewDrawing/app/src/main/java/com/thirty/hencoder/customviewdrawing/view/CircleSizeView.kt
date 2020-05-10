package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.thirty.hencoder.customviewdrawing.utils.dp

class CircleSizeView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val padding = 100.dp
    private val radius = 100.dp

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val size = (padding + radius) * 2
        val width = View.resolveSize(size.toInt(), widthMeasureSpec)
        val height = View.resolveSize(size.toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        val centerX = padding + radius
        val centerY = padding + radius
        canvas?.drawCircle(centerX, centerY, radius, paint)
    }
}