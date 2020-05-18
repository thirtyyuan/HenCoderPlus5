package com.thirty.hencoder.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.thirty.hencoder.utils.dp
import com.thirty.hencoder.utils.getAvatar

class MultiTouchView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp.toInt())
    private var originalOffsetX = 0F
    private var originalOffsetY = 0F
    private var offsetX = 0F
    private var offsetY = 0F
    private var downX = 0F
    private var downY = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val focusX: Float
        val focusY: Float
        var pointerCount = event.pointerCount
        var sumX = 0f
        var sumY = 0f
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP

        for (i in 0 until pointerCount) {
            if (!(isPointerUp && i == event.actionIndex)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }
        if (isPointerUp) {
            pointerCount--
        }
        focusX = sumX / pointerCount
        focusY = sumY / pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                downX = focusX
                downY = focusY
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}