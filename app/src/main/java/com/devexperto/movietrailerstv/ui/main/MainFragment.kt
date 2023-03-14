package com.devexperto.movietrailerstv.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devexperto.movietrailerstv.R
import com.devexperto.movietrailerstv.data.repository.MoviesRepository
import com.devexperto.movietrailerstv.domain.Category
import com.devexperto.movietrailerstv.domain.Movie
import com.devexperto.movietrailerstv.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    private val backgroundState = BackgroundState(this)

    private val vm by viewModels<MainViewModel> {
        MainViewModelFactory(MoviesRepository(getString(R.string.api_key)))
    }
    private val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)

        adapter = rowsAdapter

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect {
                    if (it.isLoading) progressBarManager.show() else progressBarManager.hide()
                    updateUi(it.categories)
                }
            }
        }

        vm.onUiReady()
    }

    private fun updateUi(categories: Map<Category, List<Movie>>) {
        rowsAdapter.clear()
        val cardPresenter = CardPresenter()
        categories.forEach { (category, movies) ->
            val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                addAll(0, movies)
            }

            val header = HeaderItem(category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }

    override fun setHeaderPresenterSelector(headerPresenterSelector: PresenterSelector?) {
        super.setHeaderPresenterSelector(headerPresenterSelector)
    }
}