package com.devexperto.movietrailerstv.ui.main

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.devexperto.movietrailerstv.R
import com.devexperto.movietrailerstv.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MainFragment : BrowseSupportFragment() {

    private lateinit var moviesRepository: MoviesRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRepository = MoviesRepository(getString(R.string.api_key))

        title = getString(R.string.browse_title)


        viewLifecycleOwner.lifecycleScope.launch {
            adapter = buildAdapter()
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