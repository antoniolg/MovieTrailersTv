package com.devexperto.movietrailerstv.data.server

import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("discover/movie")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int = 100
    ): RemoteResult

}