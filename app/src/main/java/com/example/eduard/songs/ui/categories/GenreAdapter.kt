package com.example.eduard.songs.ui.categories

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.eduard.songs.R
import com.example.eduard.songs.app.inflate
import com.example.eduard.songs.model.Song
import kotlinx.android.synthetic.main.song_list_main_screen.view.*


class GenreAdapter(private val items: MutableList<Song>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(parent.inflate(R.layout.song_list_genre_screen))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount() = items.size

  fun updateItems(items: List<Song>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Song) {
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
      itemView.name.text = item.name
    }
  }
}