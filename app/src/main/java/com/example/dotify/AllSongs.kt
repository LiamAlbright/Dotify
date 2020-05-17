package com.example.dotify


data class AllSongs(
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)