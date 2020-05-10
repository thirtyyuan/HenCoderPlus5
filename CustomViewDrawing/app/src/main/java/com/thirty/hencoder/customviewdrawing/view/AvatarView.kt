package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.customviewdrawing.R
import com.thirty.hencoder.utils.dp

private val STROKE_WIDTH = 4F.dp
private val IMAGE_PADDING = 50F.dp
private val IMAGE_WIDTH = 200F.dp
private val XFERMODE_SRC_IN = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
private val XFERMODE_SRC_OVER = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)

class AvatarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds =
        RectF(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH
        )
    private val strokeBounds =
        RectF(
            IMAGE_PADDING - STROKE_WIDTH,
            IMAGE_PADDING - STROKE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH + STROKE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH + STROKE_WIDTH
        )


    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(strokeBounds, null)
        canvas.drawOval(bounds, paint)

        paint.xfermode = XFERMODE_SRC_IN
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)

        canvas.saveLayer(strokeBounds, null)

        paint.xfermode = XFERMODE_SRC_OVER
        paint.strokeWidth = STROKE_WIDTH
        paint.style = Paint.Style.STROKE
        canvas.drawOval(bounds, paint)
        paint.xfermode = null
        paint.style = Paint.Style.FILL

        canvas.restoreToCount(count)
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