package com.example.eduard.songs.ui.songs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.eduard.songs.R
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.events.FavoriteScreenEvent
import com.example.eduard.songs.ui.Injection
import com.example.eduard.songs.ui.favorite_screen.FavoriteScreenActivity
import com.example.eduard.songs.ui.genre.SongsGenreActivity
import kotlinx.android.synthetic.main.songs_recyclerview_main_screen.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class SongsActivity : AppCompatActivity(), SongsContract.View, SongsAdapter.ItemsAdapterListener {

  override lateinit var presenter: SongsContract.Presenter
  private val adapter = SongsAdapter(mutableListOf(), this)

  private var itemCount: TextView? = null
  private var itemCountCircle: FrameLayout? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.songs_recyclerview_main_screen)

    presenter = Injection.provideItemsPresenter(this)

    setupRecyclerView()
    setupFavoriteScreenIcon()
  }

  private fun setupFavoriteScreenIcon() {
    itemsRootView.viewTreeObserver.addOnGlobalLayoutListener {
      itemCount = findViewById(R.id.itemCount)
      itemCountCircle = findViewById(R.id.itemCountCircle)
      updateCartIcon()
    }
  }

  private fun setupRecyclerView() {
    songsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    songsRecyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    super.onCreateOptionsMenu(menu)
    menuInflater.inflate(R.menu.three_dots_menu, menu)
    val item = menu.findItem(R.id.cart_menu_item)
    item.actionView.setOnClickListener { menu.performIdentifierAction(item.itemId, 0) }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.cart_menu_item -> startActivity(FavoriteScreenActivity.newIntent(this))
      R.id.add_all_menu_item -> presenter.addAllSongsToFavorite()
      R.id.remove_all_menu_item -> presenter.clearAllSongsFromFavorite()
      R.id.categories_menu_item -> showCategories()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showCategories() {
    startActivity(SongsGenreActivity.newIntent(this))
  }

  private fun updateCartIcon() {
    val cartSize = presenter.favoritesListSize()
    itemCount?.text = "$cartSize"
    itemCountCircle?.visibility = if (cartSize > 0) View.VISIBLE else View.INVISIBLE
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun showItems(items: List<Song>) {
    adapter.updateItems(items)
  }

  override fun removeItem(item: Song, favoriteButton: ImageView) {
    presenter.removeItem(item)
  }

  override fun addItem(item: Song, foodImage: ImageView, favoriteButton: ImageView) {
    presenter.addItem(item)
  }

  @Suppress("UNUSED_PARAMETER")
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCartEvent(event: FavoriteScreenEvent) {
    updateCartIcon()
    adapter.notifyDataSetChanged()
  }
}


