package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.thirty.hencoder.utils.dp
import kotlin.random.Random

private val PROVINCES = arrayListOf<String>().apply {
    add("河北省")
    add("山西省")
    add("辽宁省")
    add("吉林省")
    add("黑龙江省")
    add("江苏省")
    add("浙江省")
    add("安徽省")
    add("福建省")
    add("江西省")
    add("山东省")
    add("河南省")
    add("湖北省")
    add("湖南省")
    add("广东省")
    add("海南省")
    add("四川省")
    add("贵州省")
    add("云南省")
    add("陕西省")
    add("甘肃省")
    add("青海省")
    add("台湾省")
}

private val TEXT_SIZES = arrayListOf<Int>().apply {
    add(12)
    add(16)
    add(24)
}

private val COLORS = arrayListOf<Int>().apply {
    add(Color.GREEN)
    add(Color.RED)
    add(Color.YELLOW)
    add(Color.BLUE)
    add(Color.GRAY)
}

private val CORNER_RADIUS = 4.dp
private val X_PADDING = 16.dp.toInt()
private val Y_PADDING = 8.dp.toInt()

private val RANDOM = Random(31)

class RandomTextView(context: Context, attributeSet: AttributeSet? = null) : AppCompatTextView(context, attributeSet) {

    private val paint: Paint = Paint().apply {
        color = COLORS[RANDOM.nextInt(COLORS.size)]
        textAlign = Paint.Align.CENTER
    }

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[RANDOM.nextInt(TEXT_SIZES.size)].toFloat()
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
        text = PROVINCES[RANDOM.nextInt(PROVINCES.size)]
    }

    private var index: Int = RANDOM.nextInt(PROVINCES.size)

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), CORNER_RADIUS, CORNER_RADIUS, paint)
        super.onDraw(canvas)
    }
}

