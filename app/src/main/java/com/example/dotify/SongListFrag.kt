package com.example.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.frag_list_song.*

class SongListFrag: Fragment() {


    val allSongdata: List<Song> = (SongDataProvider.getAllSongs())
    val allSongdataMut  =   allSongdata.toMutableList()



    private var onSongSelectedListener: onSongSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is onSongSelectedListener) {
            onSongSelectedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.frag_list_song, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val songAdapter = SongListAdapter(allSongdataMut )

        rvSongList.adapter = songAdapter

        songAdapter.onSongClickListener = { someSong: Song ->
            tvsongshow.text = someSong.title +" - "+ someSong.artist

            val intent = Intent(context, MainActivity::class.java)

            intent.putExtra(MainActivity.SONG_KEY, someSong)

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

interface onSongSelectedListener {
    fun onSongSelected(song: Song)
}