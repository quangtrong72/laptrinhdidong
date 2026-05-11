package com.example.noteapp.model

data class Note(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var fileUrl: String = "",
    var time: Long = 0L
)