package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.customviewdrawing.utils.dp

private val RADIUS = 160F.dp
private val STROKE_WIDTH = 24F.dp

class SportView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val bounds = Rect()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 96F.dp
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = STROKE_WIDTH
        paint.color = Color.GRAY
        canvas.drawCircle(width / 2F, height / 2F, RADIUS, paint)

        paint.strokeWidth = STROKE_WIDTH
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = Color.GREEN

        val centerX = width / 2F
        val centerY = height / 2F
        canvas.drawArc(
            centerX - RADIUS,
            centerY - RADIUS,
            centerX + RADIUS,
            centerY + RADIUS,
            -90F,
            225F,
            false,
            paint
        )
        val text = "abap"
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val offset = (bounds.top + bounds.bottom) / 2
//        val offset = (textPaint.fontMetrics.top + textPaint.fontMetrics.bottom) / 2
        canvas.drawText(text, centerX, centerY - offset, textPaint)
    }
}