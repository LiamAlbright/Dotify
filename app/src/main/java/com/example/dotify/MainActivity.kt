package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var randCode = Random.nextInt(1000, 9999)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_numPlay.text= randCode.toString()+ " Plays"


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


    }

    fun changeName(view: View){

        val et_input = findViewById<EditText>(R.id.et_name)
        val tvUserName =  findViewById<TextView>(R.id.us_name)
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

    fun goPlay(view: View){

        val tvNumPlay: TextView =  findViewById<TextView>(R.id.tv_numPlay)
        randCode = (randCode+1)
        tvNumPlay.text= randCode.toString()+" Plays"



    }

    fun goForward(view: View){

        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show();


    }

    fun goBack(view: View){
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show();


    }
}
