package com.thirty.hencoder.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TouchViewGroup(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val velocityTracker = VelocityTracker.obtain()
    private var isScrolling = false
    private var downX: Float = 0F
    private var downY: Float = 0F
    private var downScrollX: Float = 0F
    private val viewConfiguration = ViewConfiguration.get(context)
    private val pagingSlop = viewConfiguration.scaledPagingTouchSlop
    private val minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private val maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private val overScroller = OverScroller(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (isScrolling) {
                    return false
                }
                val offsetX = downX - event.x
                if (abs(offsetX) > pagingSlop) {
                    isScrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                scrollX = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(width)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}