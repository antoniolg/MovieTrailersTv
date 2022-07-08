package com.devexperto.movietrailerstv.ui.main

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.devexperto.movietrailerstv.R
import com.devexperto.movietrailerstv.data.server.RemoteConnection
import com.devexperto.movietrailerstv.data.server.toDomain
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse_title)

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()

        viewLifecycleOwner.lifecycleScope.launch {
            val movies =
                RemoteConnection.service.listPopularMovies(getString(R.string.api_key)).results.map { it.toDomain() }

            (1..5).forEach { categoryId ->
                val categoryTitle = "Category $categoryId"

                val listRowAdapter = ArrayObjectAdapter(cardPresenter).apply {
                    addAll(
                        0, movies
                    )
                }

                val header = HeaderItem(categoryId.toLong(), categoryTitle)
                rowsAdapter.add(ListRow(header, listRowAdapter))
            }

            adapter = rowsAdapter
        }
    }

    override fun setHeaderPresenterSelector(headerPresenterSelector: PresenterSelector?) {
        super.setHeaderPresenterSelector(headerPresenterSelector)
    }
}