package com.example.volunteerku.data

data class Room(
    val kakaoUrl: String,
    val internetUrl: String,
    val title: String,
    val limitHeadCount: Int,
    val closedDateTime: String,
    val content: String
)