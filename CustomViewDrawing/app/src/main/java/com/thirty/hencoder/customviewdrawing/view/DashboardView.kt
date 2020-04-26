package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.customviewdrawing.utils.px
import kotlin.math.cos
import kotlin.math.sin

private const val COUNT = 20
private const val OPEN_ANGLE = 120
private val RADIUS = 150f.px
private val POINTER_LENGTH = 120f.px
private val DASH_LENGTH = 8f.px
private val DASH_WIDTH = 2f.px
private val STROKE_WIDTH = 2f.px

class DashboardView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dashPath = Path()
    private lateinit var pathEffect: PathDashPathEffect

    init {
        paint.strokeWidth = STROKE_WIDTH
        paint.style = Paint.Style.STROKE

        dashPath.addRect(0F, 0F, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val centerX = w / 2;
        val centerY = h / 2;

        path.reset()
        path.addArc(
            centerX - RADIUS, centerY - RADIUS,
            centerX + RADIUS, centerY + RADIUS,
            (90 + OPEN_ANGLE / 2).toFloat(),
            (360 - OPEN_ANGLE).toFloat()
        )

        val pathMeasure = PathMeasure(path, false)
        val advance = (pathMeasure.length - DASH_WIDTH) / 20
        pathEffect = PathDashPathEffect(
            dashPath,
            advance,
            0F,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)

        paint.pathEffect = pathEffect
        canvas?.drawPath(path, paint)
        paint.pathEffect = null

        val centerX = width / 2;
        val centerY = height / 2;
        val radians = markToRadians(5);
        val stopX = centerX + POINTER_LENGTH * cos(radians).toFloat()
        val stopY = centerY + POINTER_LENGTH * sin(radians).toFloat()
        canvas?.drawLine((width / 2).toFloat(), (height / 2).toFloat(), stopX, stopY, paint)
    }

    private fun getRotateAngle(index: Int): Double {
        return (90 + OPEN_ANGLE / 2F + ((360 - OPEN_ANGLE) / 20F) * index).toDouble()
    }

    private fun markToRadians(index: Int): Double {
        return Math.toRadians(getRotateAngle(20))
    }
}