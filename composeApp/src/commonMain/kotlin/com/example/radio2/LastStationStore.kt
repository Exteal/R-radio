package com.example.radio2

interface LastStationStore {
    suspend fun saveLastStation(id : String)
    suspend fun loadLastStation() : String?
}