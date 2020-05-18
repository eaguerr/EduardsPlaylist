package com.example.eduard.songs.ui.favorite_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.eduard.songs.R
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.events.DeleteSongFromFavoriteScreenEvent
import com.example.eduard.songs.ui.Injection
import kotlinx.android.synthetic.main.favorite_screen_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FavoriteScreenActivity : AppCompatActivity(), FavoriteScreenContract.View, FavoriteScreenAdapter.CartAdapterListener {

  override lateinit var presenter: FavoriteScreenContract.Presenter
  private val adapter = FavoriteScreenAdapter(mutableListOf(), this)

  companion object {
    fun newIntent(context: Context) = Intent(context, FavoriteScreenActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.favorite_screen_activity)

    presenter = Injection.provideCartPresenter(this)

    title = getString(R.string.favorites_title)

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    cartRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    cartRecyclerView.adapter = adapter
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
    EventBus.getDefault().register(this)
  }

  override fun onPause() {
    super.onPause()
    EventBus.getDefault().unregister(this)
  }

  override fun showCart(items: List<Song>, notify: Boolean) {
    adapter.updateItems(items, notify)
    if (items.isEmpty()) {
      emptyLabel.visibility = View.VISIBLE
      cartRecyclerView.visibility = View.INVISIBLE
    } else {
      emptyLabel.visibility = View.INVISIBLE
      cartRecyclerView.visibility = View.VISIBLE
    }
  }

  override fun removeItem(item: Song) {
    presenter.removeItem(item)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartDeleteItemEvent(event: DeleteSongFromFavoriteScreenEvent) {
    adapter.notifyItemRemoved(event.position)
    presenter.loadCart(false)
  }
}
