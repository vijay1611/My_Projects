package com.example.mailvalidation

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mailvalidation.paint.DrawPath

class PaintActivity : AppCompatActivity() {
    private lateinit var canvasView: DrawPath
    private lateinit var buttonUndo: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        canvasView = findViewById(R.id.drawPath)
        buttonUndo = findViewById(R.id.button_undo)

        buttonUndo.setOnClickListener {
          //  canvasView.undo()
        }




    }


}