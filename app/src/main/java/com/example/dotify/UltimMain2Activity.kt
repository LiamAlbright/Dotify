package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class UltimMain2Activity : AppCompatActivity(),OnSongSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultim_main2)

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


    }
    private fun getSongMainFragment() = supportFragmentManager.findFragmentByTag(SongMainFrag.TAG) as? SongMainFrag

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }
    override fun onSongSelected(song: Song) {
        var songMainFragment = getSongMainFragment()

        if (songMainFragment == null) {
            songMainFragment = SongMainFrag()
            val argumentBundle = Bundle().apply {
                putParcelable(SongMainFrag.SONG_KEY, song)
            }
            songMainFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songMainFragment, SongMainFrag.TAG)
                .addToBackStack(SongMainFrag.TAG)
                .commit()
        } else {
            songMainFragment.updateSong(song)
        }    }


}
