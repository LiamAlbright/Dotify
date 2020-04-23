package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private var listOfSongs: List<String>,private val listOfDescip: List<String>,private val listOfSmallimgs: List<Int>,private val  allsongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val SongName = listOfSongs[position]
        val SongDescip = listOfDescip[position]
        val SongSmallimg = listOfSmallimgs[position]
        val allTheSongs= allsongs[position]
        holder.bind(SongName, SongDescip, SongSmallimg, allTheSongs)

    }

     fun change(newSon: List<String>) {
        // Normal way up applying updates to list
        listOfSongs = newSon
        notifyDataSetChanged()

        // Animated way of applying updates to list
   //     val callback = PersonDiffCallback(listOfPeople, newPeople)
     //   val diffResult = DiffUtil.calculateDiff(callback)
    //    diffResult.dispatchUpdatesTo(this)

        // We update the list
      //  listOfPeople = newPeople


    }

    inner  class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.textTitle)
        private val tvDescip = itemView.findViewById<TextView>(R.id.textDesc)
        private val ivCovers = itemView.findViewById<ImageView>(R.id.iv_Cover)

        fun bind(name: String, desc: String, smallImg:Int, song: Song) {
            tvName.text = name
            tvDescip.text = desc
            ivCovers.setImageResource(smallImg)
            itemView.setOnClickListener{
                onSongClickListener?.invoke(song)
            }
        }


    }
}