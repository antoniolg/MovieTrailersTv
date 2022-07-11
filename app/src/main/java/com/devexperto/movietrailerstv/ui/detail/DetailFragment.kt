package com.devexperto.movietrailerstv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import com.devexperto.movietrailerstv.domain.Movie
import com.devexperto.movietrailerstv.ui.common.loadImageUrl

class DetailFragment : DetailsSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val detailsBackgroundState = DetailsBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity().intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found")

        val presenter = FullWidthDetailsMovieRowPresenter(requireActivity())

        rowsAdapter = ArrayObjectAdapter(presenter)

        val row = DetailsOverviewRow(movie)
        row.loadImageUrl(requireContext(), movie.poster)
        row.actionsAdapter = presenter.buildActions()

        rowsAdapter.add(row)

        adapter = rowsAdapter

        detailsBackgroundState.loadUrl(movie.backdrop)
    }
}