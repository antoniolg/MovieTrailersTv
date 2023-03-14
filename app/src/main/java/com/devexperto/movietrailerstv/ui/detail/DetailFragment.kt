package com.devexperto.movietrailerstv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devexperto.movietrailerstv.domain.Movie
import com.devexperto.movietrailerstv.ui.common.loadImageUrl
import com.devexperto.movietrailerstv.ui.common.parcelable
import com.devexperto.movietrailerstv.ui.detail.DetailActivity.Companion.MOVIE_EXTRA
import kotlinx.coroutines.launch

class DetailFragment : DetailsSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val detailsBackgroundState = DetailsBackgroundState(this)

    private val vm by viewModels<DetailViewModel> {
        val movie = checkNotNull(requireActivity().intent.parcelable<Movie>(MOVIE_EXTRA))
        DetailViewModelFactory(movie)
    }

    private lateinit var presenter: FullWidthDetailsMovieRowPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = FullWidthDetailsMovieRowPresenter(requireActivity())

        rowsAdapter = ArrayObjectAdapter(presenter)
        adapter = rowsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.state.collect { state ->
                    updateUI(state.movie)
                }
            }
        }
    }

    private fun updateUI(movie: Movie) {
        val row = DetailsOverviewRow(movie)
        row.loadImageUrl(requireContext(), movie.poster)
        row.actionsAdapter = presenter.buildActions()

        rowsAdapter.add(row)
        detailsBackgroundState.loadUrl(movie.backdrop)
    }
}