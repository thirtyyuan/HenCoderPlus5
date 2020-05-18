package com.thirty.hencoder.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.thirty.hencoder.utils.dp

class MultiTouchView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.BLACK
        strokeWidth = 2.dp
    }
    private val paths = SparseArray<Path>()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0 until paths.size()) {
            val path = paths.valueAt(i)
            canvas?.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val actionIndex = event.actionIndex

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val path = Path()
                path.moveTo(event.getX((actionIndex)), event.getY(actionIndex))
                paths.append(event.getPointerId(actionIndex), path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until paths.size()) {
                    val pointerId = event.getPointerId(i)
                    val path = paths.get(pointerId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val pointerId = event.getPointerId(actionIndex)
                paths.remove(pointerId)
                invalidate()
            }
        }
        return true
    }
}