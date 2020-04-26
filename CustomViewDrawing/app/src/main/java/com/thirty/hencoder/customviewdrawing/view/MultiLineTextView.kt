package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.thirty.hencoder.customviewdrawing.R
import com.thirty.hencoder.customviewdrawing.utils.px

private const val TEXT =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis bibendum leo quis leo fermentum, ac consequat odio fermentum. Duis non luctus massa, ut maximus odio. Vivamus lectus turpis, dapibus in arcu eu, tincidunt pretium arcu. Curabitur nunc velit, vulputate sit amet diam in, gravida ullamcorper enim. Etiam nibh mauris, porttitor at ex fringilla, faucibus cursus nulla. Fusce dignissim dui vitae imperdiet iaculis. Nullam sit amet mollis sem. Integer venenatis tempor quam, eget pretium dolor placerat quis. Donec neque odio, elementum vitae tincidunt eu, ullamcorper ac tellus. Nunc dui neque, eleifend vitae lobortis eget, porta vel justo. Pellentesque imperdiet lectus massa. Etiam at ante libero."

private val IMAGE_PADDING = 50F.px
private val IMAGE_SIZE = 100F.px

class MultiLineTextView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint()
    private val textPaint = Paint().apply {
        textSize = 16F.px
    }
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()), width - IMAGE_SIZE, IMAGE_PADDING, paint)

        textPaint.getFontMetrics(fontMetrics)

        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        val measuredWidth = floatArrayOf(0F)
        while (start < TEXT.length) {
            val width: Float =
                if (verticalOffset + fontMetrics.bottom < IMAGE_PADDING || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) width.toFloat() else (width - IMAGE_SIZE)
            count = textPaint.breakText(TEXT, start, TEXT.length, true, width, measuredWidth)
            canvas.drawText(TEXT, start, start + count, 0F, verticalOffset, textPaint)
            start += count
            verticalOffset += textPaint.fontSpacing
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.ic_avatar_conan, options)

        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.ic_avatar_conan, options)
    }
}