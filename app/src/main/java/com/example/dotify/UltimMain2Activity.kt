package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_ultim_main2.*

class UltimMain2Activity : AppCompatActivity(),OnSongSelectedListener {
    companion object {
      //  val TAG: String = UltimMain2Activity::class.java.simpleName
        private const val MINI_BAR = "MINI_BAR"
    }

    private var barString2= ""
    private var barSong: Song?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultim_main2)


        if (savedInstanceState != null) {
            with(savedInstanceState) {
              //  Toast.makeText(applicationContext, "htitin bundle logic "+ getString(MINI_BAR), Toast.LENGTH_SHORT).show();

                barSong = getParcelable(MINI_BAR)
                barSong?.let { onSongSelected(it) }

            }
        } else {
            barSong = null
        }


        val allSongdata: List<Song> = (SongDataProvider.getAllSongs())
        val allSongdataMut  =   allSongdata.toMutableList()



        val songMainFragment = SongMainFrag()
        val argumentBundle2 = Bundle().apply {
            val song = allSongdataMut[1]

            putParcelable(SongMainFrag.SONG_KEY, song)
        }
        songMainFragment.arguments = argumentBundle2




        val argumentBundle = Bundle().apply {
            putParcelableArrayList(SongListFrag.SONGs_KEY, ArrayList(allSongdataMut))
        }


        val listsongragment = SongListFrag()
        listsongragment.arguments =argumentBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, listsongragment)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        btShuffle.setOnClickListener{
            listsongragment.shuffleList()
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
      //  barString = song.title +" - "+ song.artist
        barSong = song
        tvsongshow.setOnClickListener {
            onSelectHelper(song);
        }



    }

    private fun onSelectHelper(songTwo: Song){
        var songMainFragment = getSongMainFragment()

        if (songMainFragment == null) {
            songMainFragment = SongMainFrag()
            val argumentBundle = Bundle().apply {
                putParcelable(SongMainFrag.SONG_KEY, songTwo)
            }
            songMainFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songMainFragment, SongMainFrag.TAG)
                .addToBackStack(SongMainFrag.TAG)
                .commit()
        } else {
            songMainFragment.updateSong(songTwo)
        }
    }



}
