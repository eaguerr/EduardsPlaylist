package com.example.eduard.songs.ui.songs

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.eduard.songs.R
import com.example.eduard.songs.app.inflate
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.play_song_screen.FoodActivity
import kotlinx.android.synthetic.main.song_list_main_screen.view.*


class SongsAdapter(private val items: MutableList<Song>, private val listener: ItemsAdapterListener) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(parent.inflate(R.layout.song_list_main_screen))

  override fun onBindViewHolder(holder: SongsAdapter.ViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount() = items.size

  fun updateItems(items: List<Song>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var item: Song

    init {
      itemView.setOnClickListener(this)
    }

    fun bind(item: Song) {
      this.item = item
      val context = itemView.context
      itemView.foodImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
      itemView.name.text = item.name
      itemView.favoriteButton.setImageResource(if (item.isInCart) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
      itemView.favoriteButton.setOnClickListener {
        if (item.isInCart) {
          listener.removeItem(item, itemView.favoriteButton)
        } else {
          listener.addItem(item, itemView.foodImage, itemView.favoriteButton)
        }
      }
    }

    override fun onClick(view: View) {
      val context = view.context
      context.startActivity(FoodActivity.newIntent(context, item.id))
    }
  }

  interface ItemsAdapterListener {
    fun removeItem(item: Song, favoriteButton: ImageView)
    fun addItem(item: Song, foodImage: ImageView, favoriteButton: ImageView)
  }
}