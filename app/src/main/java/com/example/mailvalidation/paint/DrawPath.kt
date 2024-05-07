package com.example.mailvalidation.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.Paint
import android.graphics.Path
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.Stack
import kotlin.random.Random


open class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
{private val paths = mutableListOf<Path>()
    private val colors = mutableListOf<Int>()
    private val paint = Paint()
    private var currentPath: Path? = null
    private var currentColor = Color.BLACK

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw each path with its corresponding color
        for (i in paths.indices) {
            paint.setARGB(Random.nextInt(255),Random.nextInt(255),Random.nextInt(255),Random.nextInt(255))
            canvas.drawPath(paths[i], paint)
        }

        // Draw the current path if exists
        currentPath?.let {
            paint.color = currentColor
            canvas.drawPath(it, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path()
                currentPath?.moveTo(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath?.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                currentPath?.let {
                    paths.add(it)
                    colors.add(currentColor)
                }
                currentPath = null
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    fun setCurrentColor(color: Int) {
        currentColor = color
    }}