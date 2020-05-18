package com.example.dotify.appcode

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dotify.R
import com.example.dotify.model.Song
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_song_main.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */

class SongMainFrag : Fragment() {
    var randCode = Random.nextInt(1000, 9999)

    private var song: Song? = null

    companion object {
        val TAG: String = SongMainFrag::class.java.simpleName

        const val SONG_KEY = "SONG_KEY"
        fun getInstance(song: Song) = SongMainFrag().apply {
            arguments = Bundle().apply {
                putParcelable(SONG_KEY, song)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(SONG_KEY)
            if (song != null) {
                this.song = song
            }

        }

    }
    fun updateSong(song: Song) {
        this.song = song
        updateSongViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_numPlay.text= randCode.toString()+ " Plays"


        updateSongViews()

        bt_change.setOnClickListener {
            bt_apply.apply {
                visibility = View.VISIBLE
            }
            et_name.apply {
                visibility = View.VISIBLE
            }
            us_name.apply {
                visibility = View.INVISIBLE
            }
            bt_change.apply {
                visibility = View.INVISIBLE
            }

        }



        bt_apply.setOnClickListener {

            val et_input = et_name
            val tvUserName =  us_name

            tvUserName.text = et_input.text.toString()

            bt_apply.apply {
                visibility = View.INVISIBLE
            }
            et_name.apply {
                visibility = View.INVISIBLE
            }
            us_name.apply {
                visibility = View.VISIBLE
            }
            bt_change.apply {
                visibility = View.VISIBLE
            }
        }


        tv_back.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show();
        }

        bt_play.setOnClickListener {
            val tvNumPlay: TextView = tv_numPlay
            randCode = (randCode+1)
            tvNumPlay.text= randCode.toString()+" Plays"

        }

        tv_forward.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show();

        }

    }



    private fun updateSongViews() {
        song?.let {
            tv_songTitle.text= it.title
            tv_artName.text=it.artist
            //iv_albumCover.setImageResource(it.largeImageID)
            val myUri = Uri.parse(it.largeImageURL)
            Picasso.get().load(myUri).into(iv_albumCover);

        }
    }




}
