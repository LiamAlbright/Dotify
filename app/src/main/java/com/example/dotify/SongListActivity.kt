package com.example.dotify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
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
       // getAllSongTitles(allSongdata);


        val listOfSongs =getAllSongTitles(allSongdataMut);
        val listOfDescip =getAllSongDisc(allSongdataMut);
        val listOfSmallimgs =getAllSongSmallImg(allSongdataMut);

        val listOfSongsMut  =   listOfSongs.toMutableList()

        val songAdapter = SongListAdapter(listOfSongs,listOfDescip, listOfSmallimgs, allSongdataMut )

        rvSongList.adapter = songAdapter

        songAdapter.onSongClickListener = { someSong: Song ->
           Toast.makeText(this, "My song title is ${someSong.title}", Toast.LENGTH_SHORT).show()
        //    val intent = Intent(this, PersonActivity::class.java)
//            intent.putExtra(NAME_KEY, name)
////            intent.putExtra(POSITION_KEY, pos)
//
          //  intent.putExtra(PERSON_KEY, somePerson)

        //    startActivity(intent)


        }
        btShuffle.setOnClickListener{
          val newSongs=  listOfSongsMut.apply { shuffle() }
            songAdapter.change(newSongs)
        }
    }
}

fun getAllSongTitles(allsongs: List<Song>):  List<String> {
    val retListOftitles = mutableListOf<String>()


    for (item in allsongs) {
        retListOftitles += item.title
    }

    return retListOftitles
}


fun getAllSongDisc(allarts: List<Song>):  List<String> {
    val retListOfarts = mutableListOf<String>()


    for (item in allarts) {
        retListOfarts += item.artist
    }

    return retListOfarts
}

fun getAllSongSmallImg(allimgs: List<Song>):  List<Int> {
    val retListOfimgs = mutableListOf<Int>()


    for (item in allimgs) {
        retListOfimgs += item.smallImageID
    }

    return retListOfimgs
}

