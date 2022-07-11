package com.devexperto.movietrailerstv.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import com.devexperto.movietrailerstv.R
import com.devexperto.movietrailerstv.domain.Movie
import com.devexperto.movietrailerstv.ui.common.loadImageUrl

class DetailFragment : DetailsSupportFragment() {

    private enum class Options(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_trailer),
        FAVORITE(R.string.favorite);
    }

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val detailsBackgroundState = DetailsBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity().intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found")

        val presenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        rowsAdapter = ArrayObjectAdapter(presenter)

        val row = DetailsOverviewRow(movie)
        row.loadImageUrl(requireContext(), movie.poster)
        row.actionsAdapter = buildActions(presenter)

        rowsAdapter.add(row)

        adapter = rowsAdapter

        detailsBackgroundState.loadUrl(movie.backdrop)
    }

    private fun buildActions(presenter: FullWidthDetailsOverviewRowPresenter): ArrayObjectAdapter {

        presenter.setOnActionClickedListener { action ->
            val option = Options.values().first { it.ordinal == action.id.toInt() }
            Toast.makeText(requireContext(), option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            Options.values().forEach { option ->
                add(Action(option.ordinal.toLong(), getString(option.stringRes)))
            }
        }
    }
}