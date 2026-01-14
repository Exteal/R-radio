package com.example.radio2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform