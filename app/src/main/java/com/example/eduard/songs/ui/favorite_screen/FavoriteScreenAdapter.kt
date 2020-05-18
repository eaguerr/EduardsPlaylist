package com.example.eduard.songs.ui.favorite_screen

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.example.eduard.songs.R
import com.example.eduard.songs.app.inflate
import com.example.eduard.songs.model.Song
import kotlinx.android.synthetic.main.song_list_favorite_screen.view.*


class FavoriteScreenAdapter(private val items: MutableList<Song>, private val listener: favScreenAdapterListener) : Adapter<FavoriteScreenAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.song_list_favorite_screen))

    override fun onBindViewHolder(holder: FavoriteScreenAdapter.ViewHolder, position: Int) =
            holder.bind(items[position])

    override fun getItemCount() = items.size

    fun updateItems(items: List<Song>, notify: Boolean) {
        this.items.clear()
        this.items.addAll(items)
        if (notify) notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var item: Song

        fun bind(item: Song) {
            this.item = item
            val context = itemView.context
            itemView.songImage.setImageResource(context.resources.getIdentifier(item.thumbnail, null, context.packageName))
            itemView.songName.text = item.name
            itemView.deleteButton.setOnClickListener {
                if (item.isMarkedFavorite) {
                    listener.removeItem(item)
                }
            }
        }
    }

    interface favScreenAdapterListener {
        fun removeItem(item: Song)
    }
}