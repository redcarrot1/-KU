package com.example.volunteerku.data

data class Room(
    val id: Int,
    val kakaoUrl: String,
    val internetUrl: String,
    val title: String,
    val currentHeadCount : Int,
    val limitHeadCount: Int,
    val closedDateTime: String,
    val content: String
)