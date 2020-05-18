package com.thirty.hencoder.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.thirty.hencoder.utils.dp
import com.thirty.hencoder.utils.getAvatar

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp.toInt())
    private var originalOffsetX = 0F
    private var originalOffsetY = 0F
    private var offsetX = 0F
    private var offsetY = 0F
    private var downX = 0F
    private var downY = 0F
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                if (pointerId == trackingPointerId) {
                    val nextIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    trackingPointerId = event.getPointerId(nextIndex)
                    downX = event.getX(nextIndex)
                    downY = event.getY(nextIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }
        }
        return true
    }
}