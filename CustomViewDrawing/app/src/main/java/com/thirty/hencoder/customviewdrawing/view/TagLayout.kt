package com.thirty.hencoder.customviewdrawing.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineMaxWidth = 0
        var lineMaxHeight = 0

        val widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)

        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            if (widthMeasureSpecMode != MeasureSpec.UNSPECIFIED &&
                    lineMaxWidth + child.measuredWidth > widthMeasureSpecSize) {
                lineMaxWidth = 0
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }

            val childrenBound = childrenBounds[index]
            childrenBound.set(lineMaxWidth, heightUsed, lineMaxWidth + child.measuredWidth, heightUsed + child.measuredHeight)
            lineMaxWidth += child.measuredWidth
            widthUsed = maxOf(lineMaxWidth, widthUsed)
            lineMaxHeight = maxOf(lineMaxHeight, child.measuredHeight)
        }

        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBound = childrenBounds[index]
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom)
        }
    }
}