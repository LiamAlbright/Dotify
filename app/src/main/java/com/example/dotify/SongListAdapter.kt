package com.example.dotify

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dotify.SongMainFrag.Companion.TAG
import com.squareup.picasso.Picasso

class SongListAdapter( initallsongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>()  {
    private var allsongs: List<Song> = initallsongs.toList()  // This is so we create a duplicate of the list passed in


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = allsongs.size
    var onSongClicked: ((song: Song) -> Unit)? = null


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        val allTheSongs= allsongs[position]
        holder.bind( allTheSongs)

    }

     fun change(newSongs: List<Song>) {

        val callback = SongDiffCallback(allsongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

         allsongs = newSongs


    }

    inner  class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.textTitle)
        private val tvDescip = itemView.findViewById<TextView>(R.id.textDesc)
        private val ivCovers = itemView.findViewById<ImageView>(R.id.iv_Cover)

        fun bind(song: Song) {
            tvTitle.text = song.title
            tvDescip.text = song.artist
           // ivCovers.setImageResource(song.smallImageID)
            val myUri = Uri.parse(song.smallImageURL)
            Picasso.get().load(myUri).into(ivCovers);

            //
            itemView.setOnClickListener{
                onSongClicked?.invoke(song)
            }
        }


    }
}
