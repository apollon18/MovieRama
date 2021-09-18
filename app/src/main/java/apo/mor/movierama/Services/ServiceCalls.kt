package apo.mor.movierama.Services

import apo.mor.movierama.Models.MovieDetailsModel
import apo.mor.movierama.Models.ResultModel
import apo.mor.movierama.Models.ReviewResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceCalls {

    companion object {

        fun getPopularMovieList(page: Int, callback: Callback<ResultModel>) {
            val call: Call<ResultModel?>? = RestClient.REST_CLIENT?.getPopularList(
                ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                page.toString()
            )
            call?.enqueue(object : Callback<ResultModel?> {
                override fun onResponse(
                    call: Call<ResultModel?>,
                    response: Response<ResultModel?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ResultModel?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        fun getSearchResults(page: Int, searchText: String, callback: Callback<ResultModel>) {
            val call: Call<ResultModel?>? = RestClient.REST_CLIENT?.getSearchResults(
                ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                page.toString(),
                searchText
            )
            call?.enqueue(object : Callback<ResultModel?> {
                override fun onResponse(
                    call: Call<ResultModel?>,
                    response: Response<ResultModel?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ResultModel?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        fun getMovieDetails(movieId: Int, callback: Callback<MovieDetailsModel>) {
            val call: Call<MovieDetailsModel?>? = RestClient.REST_CLIENT?.getMovieDetails(
                movieId.toString(),
                ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                "credits"
            )
            call?.enqueue(object : Callback<MovieDetailsModel?> {
                override fun onResponse(
                    call: Call<MovieDetailsModel?>,
                    response: Response<MovieDetailsModel?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<MovieDetailsModel?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        fun getMovieReviews(movieId: Int, callback: Callback<ReviewResult>) {
            val call: Call<ReviewResult?>? = RestClient.REST_CLIENT?.getMovieReviews(
                movieId.toString(), "reviews",
                ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                "1"
            )
            call?.enqueue(object : Callback<ReviewResult?> {
                override fun onResponse(
                    call: Call<ReviewResult?>,
                    response: Response<ReviewResult?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ReviewResult?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        fun getSimilarMovies(movieId: Int, callback: Callback<ResultModel>) {
            val call: Call<ResultModel?>? = RestClient.REST_CLIENT?.getSimilarMovies(
                movieId.toString(), "similar",
                ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                "1"
            )
            call?.enqueue(object : Callback<ResultModel?> {
                override fun onResponse(
                    call: Call<ResultModel?>,
                    response: Response<ResultModel?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ResultModel?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }
    }
}