package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.utils.dp

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
    add("台湾省")
    add("台湾省")
    add("台湾省")
}

class ProvinceView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val paint: Paint = Paint().apply {
        textSize = 80F.dp
        textAlign = Paint.Align.CENTER
    }

    var index: Int = 0
        set(value) {
            field = value
            invalidate()
        }


    override fun onDraw(canvas: Canvas) {
        canvas.drawText(PROVINCES[index], (width / 2).toFloat(), (height / 2).toFloat(), paint)
    }
}

