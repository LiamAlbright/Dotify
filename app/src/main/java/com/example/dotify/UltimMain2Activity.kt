package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UltimMain2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultim_main2)

          val listsongragment = SongListFrag()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, listsongragment)
            .commit()
    }
}
