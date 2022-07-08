package com.devexperto.movietrailerstv.data.repository

import com.devexperto.movietrailerstv.data.server.RemoteConnection
import com.devexperto.movietrailerstv.data.server.toDomain
import com.devexperto.movietrailerstv.domain.Category
import com.devexperto.movietrailerstv.domain.Movie

class MoviesRepository(private val apiKey: String) {

    suspend fun getCategories(): Map<Category, List<Movie>> {
        return Category.values().associateWith { category ->
            RemoteConnection
                .service
                .listPopularMovies(apiKey, category.id).results.map { it.toDomain() }
        }
    }
}