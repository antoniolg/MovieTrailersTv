package com.devexperto.movietrailerstv.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.devexperto.movietrailerstv.R
import com.devexperto.movietrailerstv.data.repository.MoviesRepository
import com.devexperto.movietrailerstv.domain.Movie
import com.devexperto.movietrailerstv.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    private lateinit var moviesRepository: MoviesRepository
    private val backgroundState = BackgroundState(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRepository = MoviesRepository(getString(R.string.api_key))

        title = getString(R.string.browse_title)


        viewLifecycleOwner.lifecycleScope.launch {
            adapter = buildAdapter()
        }

        onItemViewClickedListener =
            OnItemViewClickedListener { vh, movie, _, _ ->
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    (vh.view as ImageCardView).mainImageView,
                    DetailActivity.SHARED_ELEMENT_NAME
                ).toBundle()

                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.MOVIE_EXTRA, movie as Movie)
                }
                startActivity(intent, bundle)
            }

        onItemViewSelectedListener = OnItemViewSelectedListener { _, movie, _, _ ->
            (movie as? Movie)?.let { backgroundState.loadUrl(movie.backdrop) }
        }
    }

    private suspend fun buildAdapter(): ArrayObjectAdapter {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        moviesRepository.getCategories().forEach { (category, movies) ->
            val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                addAll(0, movies)
            }

            val header = HeaderItem(category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
        return rowsAdapter
    }

    override fun setHeaderPresenterSelector(headerPresenterSelector: PresenterSelector?) {
        super.setHeaderPresenterSelector(headerPresenterSelector)
    }
}