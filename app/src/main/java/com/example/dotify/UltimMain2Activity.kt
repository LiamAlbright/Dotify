
package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.dotify.appcode.OnSongSelectedListener
import com.example.dotify.appcode.SongListFrag
import com.example.dotify.appcode.SongMainFrag
import com.example.dotify.backend.ApiManager
import com.example.dotify.backend.CoolHttpApp
import com.example.dotify.model.Song
import kotlinx.android.synthetic.main.activity_ultim_main2.*

class UltimMain2Activity : AppCompatActivity(),
    OnSongSelectedListener {
    companion object {
        private const val MINI_BAR = "MINI_BAR"

    }

    private var barSong: Song?= null
    private lateinit var apiManager: ApiManager
    private val TAG = "liam"
    var retSongs: List<Song> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultim_main2)

        //////////btGetSongs/////////////////////////////////
        apiManager = (application as CoolHttpApp).apiManager
        btGetSongs.setOnClickListener {
            fetchSongWithRetroFit(savedInstanceState)
        }



    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(MINI_BAR, barSong)
        super.onSaveInstanceState(outState)
    }
    private fun getSongMainFragment() = supportFragmentManager.findFragmentByTag(SongMainFrag.TAG) as? SongMainFrag

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }


    override fun onSongSelected(song: Song) {
        tvsongshow.text = song.title +" - "+ song.artist
        barSong = song

        tvsongshow.setOnClickListener {
            onSelectHelper(song);
        }



    }

    private fun onSelectHelper(songTwo: Song){
        var songMainFragment = getSongMainFragment()

        if (songMainFragment == null) {
            songMainFragment = SongMainFrag.getInstance(songTwo)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songMainFragment, SongMainFrag.TAG)
                .addToBackStack(SongMainFrag.TAG)
                .commit()

        } else {
            songMainFragment.updateSong(songTwo)

        }
    }

    private fun fetchSongWithRetroFit(savedInstanceState: Bundle?) :  List<Song> {


        apiManager.getListOfSongs({ allSongs ->
            retSongs = allSongs.songs
            onhtpHelper(allSongs.songs,savedInstanceState )
        })
        return retSongs
    }


    private fun onhtpHelper(inithttpsongs: List<Song>, savedInstanceState: Bundle?){
        retSongs = inithttpsongs
        Log.i(TAG, "return list test "+ retSongs)

        if (savedInstanceState != null) {
            with(savedInstanceState) {

                barSong = getParcelable(com.example.dotify.UltimMain2Activity.MINI_BAR)
                barSong?.let { onSongSelected(it) }

            }
        } else {
            barSong = null
        }


        val allSongdata: List<Song> = retSongs
        val allSongdataMut  =   allSongdata.toMutableList()



        val songMainFragment = SongMainFrag()
        val argumentBundle2 = Bundle().apply {
            val song = allSongdataMut[1]
            Log.i(TAG, "song image test1 "+ song)

            putParcelable(SongMainFrag.SONG_KEY, song)
        }
        songMainFragment.arguments = argumentBundle2







        if (supportFragmentManager.findFragmentByTag(SongMainFrag.TAG) == null) {
            // There is no song detail fragment
            val listsongragment = SongListFrag()
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFrag.SONGs_KEY, ArrayList(allSongdataMut))
            }
            listsongragment.arguments =argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, listsongragment)
                .commit()

            btShuffle.setOnClickListener{
                listsongragment.shuffleList()
            }
        } else {

        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                btShuffle.apply {
                    visibility = View.INVISIBLE
                }
                tvsongshow.apply {
                    visibility = View.INVISIBLE
                }
                btGetSongs.apply {
                   visibility = View.INVISIBLE
           }

            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                btShuffle.apply {
                    visibility = View.VISIBLE
                }
                tvsongshow.apply {
                    visibility = View.VISIBLE
                }
                btGetSongs.apply {
                    visibility = View.VISIBLE
                }
            }
        }







    }








}