package com.example.eduard.songs.ui.genre

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eduard.songs.R
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.Injection
import kotlinx.android.synthetic.main.songs_recyclerview_main_screen.*


class GenreFragment : Fragment(), GenreContract.View {

  override lateinit var presenter: GenreContract.Presenter
  private val adapter = GenreAdapter(mutableListOf())

  companion object {
    private const val ARG_CATEGORY = "ARG_CATEGORY"

    fun newInstance(category: String): GenreFragment {
      val args = Bundle()
      args.putString(ARG_CATEGORY, category)

      val fragment = GenreFragment()
      fragment.arguments = args
      return fragment
    }
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    presenter = Injection.provideCategoryPresenter(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
    inflater.inflate(R.layout.genres_fragment, container, false)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    songsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    songsRecyclerView.adapter = adapter
  }

  override fun onResume() {
    super.onResume()
    presenter.loadCategory(arguments.getString(ARG_CATEGORY))
  }

  override fun showItems(items: List<Song>) {
    adapter.updateItems(items)
  }
}