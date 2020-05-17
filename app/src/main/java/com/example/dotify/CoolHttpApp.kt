package com.example.dotify

import android.app.Application
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class CoolHttpApp: Application() {

    lateinit var apiManager: ApiManager
    private lateinit var songService: SongService

    override fun onCreate() {
        super.onCreate()

        // Init Retrofit + MailService
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create()) // this will automatically apply Gson conversion :)
            .build()
        songService = retrofit.create(SongService::class.java)

        // Load managers
        apiManager = ApiManager(songService)
    }
}

interface SongService {

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    fun allSongs(): Call<AllSongs>

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    fun song(): Call<Song>
}