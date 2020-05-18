package com.example.dotify.backend


import com.example.dotify.model.AllSongs
import com.example.dotify.model.Song
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager(private val songService: SongService) {

    fun getEmail(onEmailReady: (Song) -> Unit, onError: (() -> Unit)? = null) {
        songService.song().enqueue(object : Callback<Song>{
            override fun onResponse(call: Call<Song>, response: Response<Song>) {
                val song = response.body()
                if (song != null) {
                    onEmailReady(song)
                } else {
                    onError?.invoke()
                }
            }

            override fun onFailure(call: Call<Song>, t: Throwable) {
                onError?.invoke()
            }
        })

    }

    fun getListOfSongs(onSongReady: (AllSongs) -> Unit, onError: (() -> Unit)? = null) {

        songService.allSongs().enqueue(object : Callback<AllSongs> {
            override fun onResponse(call: Call<AllSongs>, response: Response<AllSongs>) {
                val allSongs = response.body()
                if (allSongs != null) {
                    onSongReady(allSongs)
                } else {
                    onError?.invoke()
                }
            }

            override fun onFailure(call: Call<AllSongs>, t: Throwable) {
                onError?.invoke()
            }
        })
    }

}