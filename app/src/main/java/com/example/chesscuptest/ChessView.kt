package com.example.chesscuptest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ChessView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val originX = 20f
    private val originY = 200f
    private val cellSide = 130f

    private val imgResIds = setOf(
        R.drawable.pawn_black,
        R.drawable.pawn_white,
        R.drawable.rook_black,
        R.drawable.rook_white,
        R.drawable.knight_black,
        R.drawable.knight_white,
        R.drawable.bishop_black,
        R.drawable.bishop_white,
        R.drawable.queen_black,
        R.drawable.queen_white,
        R.drawable.king_black,
        R.drawable.king_white
    )
    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private val paint = Paint()

    private val darkColor = Color.argb(255, 100, 100, 100) // Тёмно-серый
    private val lightColor = Color.argb(255, 200, 200, 200) // Светло-серый

    var chessDelegate: ChessDelegate? = null
    init {
        loadBitmaps()
    }

    override fun onDraw(canvas: Canvas) {
        if (canvas == null) return
        drawChessBoard(canvas)
        drawPieces(canvas)
    }

    private fun loadBitmaps() {
        imgResIds.forEach { resId ->
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            if (bitmap != null) {
                bitmaps[resId] = bitmap
            }
        }
    }

    private fun drawPieces(canvas: Canvas) {

        for (row in 0..7){
            for (col in 0..7){
                chessDelegate?.pieceAt(col,row)?.let{drawPieceAt(canvas,col,row,it.resId)}
            }
        }

   }

    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, resId: Int) {
        val bitmap = bitmaps[resId]
        if (bitmap != null) {
            val left = originX + col * cellSide
            val top = originY + (7 - row) * cellSide
            val right = originX + (col + 1) * cellSide
            val bottom = originY + (7 - row + 1) * cellSide
            canvas.drawBitmap(bitmap, null, RectF(left, top, right, bottom), paint)
        }
    }

    private fun drawChessBoard(canvas: Canvas) {
        for (i in 0..7) {
            for (j in 0..7) {
                drawSquareAt(canvas,i,j,(i + j) % 2 == 1)
            }
        }
    }
    private fun drawSquareAt(canvas:Canvas?,col:Int,row:Int,isDark:Boolean){
        paint.color = if (isDark) darkColor else lightColor
        canvas?.drawRect(originX + col * cellSide, originY + row * cellSide, originX + (col + 1)* cellSide,originY + (row + 1 ) * cellSide,paint)
    }
}
