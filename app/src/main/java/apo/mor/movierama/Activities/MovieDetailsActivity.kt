package apo.mor.movierama.Activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import apo.mor.movierama.Adapters.CastAdapter
import apo.mor.movierama.Adapters.GenresAdapter
import apo.mor.movierama.Adapters.SimilarMoviesAdapter
import apo.mor.movierama.Models.*
import apo.mor.movierama.R
import apo.mor.movierama.Services.ApiConstants
import apo.mor.movierama.Services.ServiceCalls
import apo.mor.movierama.Utils.GeneralUtils
import apo.mor.movierama.Utils.SharedPreferenceUtil
import apo.mor.movierama.databinding.ActivityMovieDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {

    private var binding: ActivityMovieDetailsBinding? = null
    private var movieId: String? = null
    private var movieDetails: MovieDetailsModel? = null
    private var reviews: ArrayList<ReviewModel> = ArrayList()
    private var similarMovies: ArrayList<MovieModel> = ArrayList()
    private var cast: ArrayList<Cast> = ArrayList()
    private var castAdapter: CastAdapter? = null
    private var similarMoviesAdapter: SimilarMoviesAdapter? = null
    private var genresAdapter: GenresAdapter? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        movieId = intent.getStringExtra("id")
        if (movieId?.isEmpty() == true) {
            finish()
        }
        getData()
    }

    private fun getData() {
        binding?.loadingLayout?.root?.visibility = View.VISIBLE
        getBasicMovieDetails()
        getMovieReviews()
        getSimilarMovies()
    }

    private fun getBasicMovieDetails() {
        ServiceCalls.getMovieDetails(movieId = movieId?: "", callback = object : Callback<MovieDetailsModel> {
            override fun onResponse(
                call: Call<MovieDetailsModel>,
                response: Response<MovieDetailsModel>
            ) {
                movieDetails = response.body()
                updateBasicUi()
            }

            override fun onFailure(call: Call<MovieDetailsModel>, t: Throwable) {
                finish()
            }
        })
    }

    private fun updateBasicUi() {
        binding?.closeButton?.setOnClickListener { finish() }
        binding?.movieTitleText?.text = movieDetails?.title
        binding?.ratingNumber?.text = movieDetails?.vote_average.toString()
        binding?.movieDescription?.text = movieDetails?.overview
        setMoviePoster()
        setMovieCast()
        setMovieGenres()
        setFavoriteStatus()
        binding?.loadingLayout?.root?.visibility = View.GONE
    }

    private fun setMoviePoster() {
        val options =
            RequestOptions().placeholder(R.drawable.placeholder_movie_details).error(R.drawable.placeholder_movie_details)
        binding?.moviePosterImage?.let {
            Glide.with(this@MovieDetailsActivity).load(ApiConstants.IMAGES_POSTER_BASE_URL + movieDetails?.poster_path)
                .apply(options).into(it)
        }
    }

    private fun setMovieCast() {
        setDirector()
        cast = GeneralUtils.getTopBilledCast(movieDetails?.credits?.cast)
        if (cast.isEmpty()) {
            binding?.castTitle?.visibility = View.GONE
            binding?.castList?.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(this@MovieDetailsActivity)
            binding?.castList?.layoutManager = layoutManager
            castAdapter = CastAdapter(cast)
            binding?.castList?.adapter = castAdapter
        }
    }

    private fun setDirector() {
        val director = GeneralUtils.getDirectorName(movieDetails?.credits?.cast as ArrayList<Cast>)
        if (director.isNotEmpty()) {
            binding?.directorName?.text = director
        } else {
            binding?.directorLayout?.visibility = View.GONE
        }
    }

    private fun setMovieGenres() {
        val genresList = movieDetails?.genres
        if (genresList?.isEmpty() == true) {
            binding?.genreList?.visibility = View.GONE
        } else {
            val layoutManager = FlexboxLayoutManager(this@MovieDetailsActivity)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            binding?.genreList?.layoutManager = layoutManager
            genresAdapter = GenresAdapter(genresList as ArrayList<Genres>?)
            binding?.genreList?.adapter = genresAdapter
        }
    }

    private fun setFavoriteStatus() {
        if (GeneralUtils.isMovieFavorite(this@MovieDetailsActivity, movieDetails?.id?.toString()?: "")) {
            binding?.favoriteText?.text = resources.getString(R.string.added_to_favorites_text)
            binding?.favoriteText?.setTextColor(resources.getColor(R.color.favorite))
            binding?.favoriteIcon?.setImageDrawable(resources.getDrawable(R.drawable.favorites_icon))
        } else {
            binding?.favoriteText?.text = resources.getString(R.string.add_to_favorites_text)
            binding?.favoriteText?.setTextColor(resources.getColor(R.color.gray_text))
            binding?.favoriteIcon?.setImageDrawable(resources.getDrawable(R.drawable.favorite_not_icon))
        }
        binding?.favoriteCard?.setOnClickListener { setFavoriteButton() }
    }

    private fun setFavoriteButton() {
        if (GeneralUtils.isMovieFavorite(this@MovieDetailsActivity, movieDetails?.id?.toString() ?: "")) {
            SharedPreferenceUtil.removeMovieFromFavorites(this@MovieDetailsActivity, movieDetails?.id?.toString() ?: "")
        } else {
            SharedPreferenceUtil.saveMovieToFavorites(this@MovieDetailsActivity, movieDetails?.id?.toString() ?: "")
        }
        setFavoriteStatus()
    }

    private fun getMovieReviews() {
        ServiceCalls.getMovieReviews(movieId = movieId?: "", callback = object : Callback<ReviewResult> {
            override fun onResponse(call: Call<ReviewResult>, response: Response<ReviewResult>) {
                if (response.body()?.results != null) {
                    reviews = response.body()?.results as ArrayList<ReviewModel>
                }
                if (reviews.isNotEmpty()) {
                    updateReviewsUi()
                } else {
                    hideReviewsUi()
                }
            }

            override fun onFailure(call: Call<ReviewResult>, t: Throwable) {
                hideReviewsUi()
            }
        })
    }

    private fun updateReviewsUi() {
        binding?.reviewsCard?.setOnClickListener { openReviewsModal() }
    }

    private fun openReviewsModal() {

    }

    private fun hideReviewsUi() {
        binding?.reviewsText?.visibility = View.GONE
        binding?.reviewsCard?.setOnClickListener(null)
    }

    private fun getSimilarMovies() {
        ServiceCalls.getSimilarMovies(movieId = movieId?: "", callback = object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                if (response.body()?.results != null) {
                    similarMovies = response.body()?.results as ArrayList<MovieModel>
                }
                if (similarMovies.isNotEmpty()) {
                    updateSimilarMoviesList()
                } else {
                    hideSimilarMoviesList()
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                hideSimilarMoviesList()
            }
        })
    }

    private fun updateSimilarMoviesList() {
        val layoutManager = LinearLayoutManager(this@MovieDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        binding?.similarMoviesList?.layoutManager = layoutManager
        similarMoviesAdapter = SimilarMoviesAdapter(this@MovieDetailsActivity, similarMovies)
        binding?.similarMoviesList?.adapter = similarMoviesAdapter
    }

    private fun hideSimilarMoviesList() {
        binding?.similarMoviesTitle?.visibility = View.GONE
        binding?.similarMoviesList?.visibility = View.GONE
    }
}