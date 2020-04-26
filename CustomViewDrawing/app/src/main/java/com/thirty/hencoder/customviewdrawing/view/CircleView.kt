package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.customviewdrawing.utils.px

class CircleView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val paint = Paint()

    var radius: Float = 30F.px
        set(value) {
            field = value
            invalidate()
        }

    var pointF: PointF = PointF(0F, 0F)
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(pointF.x, pointF.y, radius, paint)
    }
}