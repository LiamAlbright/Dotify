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
    private var songsAll: List<Song>? = null


    //val allSongdata: List<Song> = (SongDataProvider.getAllSongs())
    //val allSongdataMut  =   allSongdata.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val songsAll = args.getParcelableArrayList<Song>(SONGs_KEY)
            if (songsAll != null) {
                this.songsAll = songsAll
            }
        }

    }


    private var onSongSelectedListener: OnSongSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongSelectedListener) {
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

        updateSongListViews()


    }





    private fun updateSongListViews() {
        songsAll?.let {

            val songsMutfromAct = it.toMutableList()

            val songAdapter = SongListAdapter(songsMutfromAct )

            rvSongList.adapter = songAdapter

            songAdapter.onSongClicked = { someSong: Song ->
                tvsongshow.text = someSong.title +" - "+ someSong.artist
                onSongSelectedListener?.onSongSelected(someSong)


            }

            btShuffle.setOnClickListener{
                val newSongs =   songsMutfromAct.toMutableList().apply { shuffle() }
                songAdapter.change(newSongs)
            }
        }
    }

    companion object {
        // Keys for intents
        val TAG: String = SongListFrag::class.java.simpleName
        const val SONGs_KEY = "SONGs_KEY"

    }
}

interface OnSongSelectedListener {
    fun onSongSelected(song: Song)
}