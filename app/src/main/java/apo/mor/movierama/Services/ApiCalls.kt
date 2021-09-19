package apo.mor.movierama.Services

import apo.mor.movierama.Models.MovieDetailsModel
import apo.mor.movierama.Models.ResultModel
import apo.mor.movierama.Models.ReviewResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCalls {

    @GET(ApiConstants.TMDB_BASE_URL + "popular")
    fun getPopularList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: String?
    ) : Call<ResultModel?>?

    @GET(ApiConstants.TMDB_SEARCH_URL + "movie")
    fun getSearchResults(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: String?,
        @Query("query") query: String?
    ) : Call<ResultModel?>?

    @GET(ApiConstants.TMDB_BASE_URL + "{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("append_to_response") credits: String?
    ) : Call<MovieDetailsModel?>?

    @GET(ApiConstants.TMDB_BASE_URL + "{movie_id}" + "/" + "{reviews}")
    fun getMovieReviews(
        @Path("movie_id") movieId: String?,
        @Path("reviews") reviews: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: String?
    ) : Call<ReviewResult?>?

    @GET(ApiConstants.TMDB_BASE_URL + "{movie_id}" + "/" + "{similar}")
    fun getSimilarMovies(
        @Path("movie_id") movieId: String?,
        @Path("similar") similar: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: String?
    ) : Call<ResultModel?>?
}