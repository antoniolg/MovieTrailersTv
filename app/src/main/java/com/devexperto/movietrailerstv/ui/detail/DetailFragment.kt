package com.devexperto.movietrailerstv.ui.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import com.devexperto.movietrailerstv.domain.Movie

class DetailFragment : DetailsSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity().intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)

        val presenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())

        val rowsAdapter = ArrayObjectAdapter(presenter)
        rowsAdapter.add(DetailsOverviewRow(movie))

        adapter = rowsAdapter
    }
}