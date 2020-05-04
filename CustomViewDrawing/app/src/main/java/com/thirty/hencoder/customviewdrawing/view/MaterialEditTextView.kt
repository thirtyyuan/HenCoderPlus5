package com.thirty.hencoder.customviewdrawing.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.thirty.hencoder.customviewdrawing.R
import com.thirty.hencoder.customviewdrawing.utils.dp

private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp

class MaterialEditTextView(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    private val animator by lazy { ObjectAnimator.ofFloat(this, "floatLabelFraction", 0F, 1F) }

    private val verticalOffset = 24.dp
    private val horizontalOffset = 4.dp
    private val extraVerticalOffset = 16.dp

    var useFloatLabel = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (field) {
                setPadding(paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
            } else {
                setPadding(paddingLeft, (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
            }
        }

    var floatLabelShown = false

    var floatLabelFraction: Float = 0F
        set(value) {
            if (useFloatLabel) {
                field = value
                invalidate()
            }
        }

    private val floatPaint = Paint().apply {
        textSize = TEXT_SIZE
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditTextView)
        useFloatLabel = typeArray.getBoolean(R.styleable.MaterialEditTextView_useFloatLabel, true)
        typeArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (useFloatLabel) {
            floatPaint.alpha = (0xFF * floatLabelFraction).toInt()
            val currentVerticalOffset = verticalOffset + (extraVerticalOffset * (1 - floatLabelFraction))
            canvas?.drawText(hint.toString(), horizontalOffset, currentVerticalOffset, floatPaint)
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (useFloatLabel) {
            if (floatLabelShown && text.isNullOrEmpty()) {
                floatLabelShown = false
                animator.reverse()
            } else if (!floatLabelShown && !text.isNullOrEmpty()) {
                floatLabelShown = true
                animator.start()
            }
        }
    }
}