package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.utils.dp
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.dp
private val TRANSLATE_LENGTH = 24f.dp
private val ANGLES = floatArrayOf(15F, 30F, 45F, 60F, 90F, 120F)
private val COLORS =
    listOf(Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLACK, Color.YELLOW)

class PieView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var translateIndex = 1

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val centerX = width / 2
        val centerY = height / 2

        var startAngle = 0F
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            if (index == translateIndex) {
                canvas?.save()
                val radians = Math.toRadians((startAngle + angle / 2).toDouble()).toFloat()

                canvas?.translate(
                    TRANSLATE_LENGTH * cos(radians),
                    TRANSLATE_LENGTH * sin(radians)
                )
            }

            canvas?.drawArc(
                centerX - RADIUS, centerY - RADIUS,
                centerX + RADIUS, centerY + RADIUS,
                startAngle, angle, true, paint
            )
            startAngle += angle

            if (index == translateIndex) {
                canvas?.restore()
            }
        }
    }
}