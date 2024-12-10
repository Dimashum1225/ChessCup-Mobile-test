package com.example.chesscuptest

interface ChessDelegate {
    fun pieceAt(col:Int,row:Int):ChessPiece?

}