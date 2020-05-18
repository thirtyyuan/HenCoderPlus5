package com.thirty.hencoder.touch

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.thirty.hencoder.utils.dp
import com.thirty.hencoder.utils.getAvatar

private val IMAGE_SIZE = 300.dp
private const val EXTRA_SCALE_FACTOR = 1.5F

class ScalableImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_SIZE.toInt())
    private var big = false
    private var originalOffsetX = 0F
    private var originalOffsetY = 0F
    private var offsetX = 0F
    private var offsetY = 0F
    private var minScaleFactor = 0F
    private var maxScaleFactor = 0F
    private var currentScaleFactor = 0F
        set(value) {
            field = value
            invalidate()
        }

    private val gestureListener = OnGestureListener()
    private val scaleGestureListener = ScaleGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, gestureListener)
    private val scaleGestureDetector = ScaleGestureDetector(context, scaleGestureListener)
    private val scaleAnimator = ObjectAnimator.ofFloat(this, "currentScaleFactor", minScaleFactor, maxScaleFactor)
    private val scroller = OverScroller(context)
    private val flingRunner = FlingRunner()

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        originalOffsetX = (width - IMAGE_SIZE) / 2;
        originalOffsetY = (height - IMAGE_SIZE) / 2;

        val bitmapWidthAndHeightRate = bitmap.width / bitmap.height.toFloat()
        val viewWidthAndHeightRate = width / height.toFloat()

        if (bitmapWidthAndHeightRate > viewWidthAndHeightRate) {
            minScaleFactor = width / bitmap.width.toFloat()
            maxScaleFactor = (height / bitmap.height.toFloat()) * EXTRA_SCALE_FACTOR
        } else {
            minScaleFactor = height / bitmap.height.toFloat()
            maxScaleFactor = (width / bitmap.width.toFloat()) * EXTRA_SCALE_FACTOR
        }
        currentScaleFactor = minScaleFactor
        scaleAnimator.setFloatValues(minScaleFactor, maxScaleFactor)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (scaleGestureDetector.isInProgress) {
            return scaleGestureDetector.onTouchEvent(event)
        }
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.i("haha", "scale=$currentScaleFactor")

        val scaleFactor = (currentScaleFactor - minScaleFactor) / (maxScaleFactor - minScaleFactor)
        canvas?.translate(offsetX * scaleFactor, offsetY * scaleFactor)
        canvas?.scale(currentScaleFactor, currentScaleFactor, (width / 2F), (height / 2F))
        canvas?.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    private fun fixOffsets() {
        offsetX = minOf(offsetX, (bitmap.width * maxScaleFactor - width) / 2)
        offsetX = maxOf(offsetX, -(bitmap.width * maxScaleFactor - width) / 2)
        offsetY = minOf(offsetY, (bitmap.height * maxScaleFactor - height) / 2)
        offsetY = maxOf(offsetY, -(bitmap.height * maxScaleFactor - height) / 2)
    }

    private inner class OnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                offsetX = (e.x - width / 2f) * (1 - maxScaleFactor / minScaleFactor)
                offsetY = (e.y - height / 2f) * (1 - maxScaleFactor / minScaleFactor)
                fixOffsets()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }

        override fun onScroll(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffsets()
            invalidate()
            return true
        }

        override fun onFling(
            downEvent: MotionEvent?,
            currentEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val widthOffset = ((bitmap.width * maxScaleFactor - width) / 2).toInt()
            val heightOffset = ((bitmap.height * maxScaleFactor - height) / 2).toInt()
            scroller.fling(
                offsetX.toInt(),
                offsetY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                -widthOffset,
                widthOffset,
                -heightOffset,
                heightOffset
            )
            ViewCompat.postOnAnimation(this@ScalableImageView, flingRunner)
            return true
        }
    }

    inner class FlingRunner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }

    inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2f) * (1 - maxScaleFactor / minScaleFactor)
            offsetY = (detector.focusY - height / 2f) * (1 - maxScaleFactor / minScaleFactor)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tempCurrentScale = currentScaleFactor * detector.scaleFactor
            if (tempCurrentScale < minScaleFactor || tempCurrentScale > maxScaleFactor) {
                return false
            } else {
                currentScaleFactor *= detector.scaleFactor
                return true
            }
        }
    }
}