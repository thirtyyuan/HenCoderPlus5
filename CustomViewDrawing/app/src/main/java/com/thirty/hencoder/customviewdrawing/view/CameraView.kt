package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.thirty.hencoder.customviewdrawing.R
import com.thirty.hencoder.utils.dp

private val IMAGE_SIZE = 200F.dp
private val IMAGE_PADDING = 50F.dp

class CameraView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera().apply {
    }

    var flipRotation: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    var topFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    var bottomFlip: Float = 0F
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        val transferLength = IMAGE_PADDING + IMAGE_SIZE / 2;
        // Draw upper half part
        canvas.save()
        canvas.translate(transferLength, transferLength)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0F)
        canvas.rotate(flipRotation)
        canvas.translate(-transferLength, -transferLength)
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        // Draw below half part
        canvas.save()
        canvas.translate(transferLength, transferLength)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_SIZE, 0F, IMAGE_SIZE, IMAGE_SIZE)
        canvas.rotate(flipRotation)
        canvas.translate(-transferLength, -transferLength)
        canvas.drawBitmap(getAvatar(IMAGE_SIZE.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
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