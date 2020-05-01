package com.example.dotify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

import com.example.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.songlistview.*
import java.util.Collections.shuffle

class SongListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.songlistview)

        val allSongdata: List<Song> = (SongDataProvider.getAllSongs())
        val allSongdataMut  =   allSongdata.toMutableList()
        val firstSong: Song = allSongdata[1]
        println(firstSong)




        val songAdapter = SongListAdapter(allSongdataMut )

        rvSongList.adapter = songAdapter

        songAdapter.onSongClicked = { someSong: Song ->
            tvsongshow.text = someSong.title +" - "+ someSong.artist

                val intent = Intent(this, MainActivity::class.java)

                    intent.putExtra(SONG_KEY, someSong)

            tvsongshow.setOnClickListener{
                startActivity(intent)

            }

        }

        btShuffle.setOnClickListener{
          val newSongs =   allSongdataMut.toMutableList().apply { shuffle() }
            songAdapter.change(newSongs)
        }
    }
}


