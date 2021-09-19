package apo.mor.movierama.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import apo.mor.movierama.Listeners.MovieListListener
import apo.mor.movierama.Models.MovieModel
import apo.mor.movierama.R
import apo.mor.movierama.Services.ApiConstants
import apo.mor.movierama.Utils.GeneralUtils
import apo.mor.movierama.databinding.ListItemSimilarMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView

class SimilarMoviesAdapter (
private var context: Context,
private var movieList: ArrayList<MovieModel>?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderSimilarMovieList(
            ListItemSimilarMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList?.get(holder.absoluteAdapterPosition)
        val viewHolder = holder as ViewHolderSimilarMovieList
        viewHolder.movieTitle.text = movie?.title
        viewHolder.movieYear.text = GeneralUtils.getYearFromDate(movie?.release_date ?: "")
        viewHolder.movieRating.text = movie?.vote_average.toString()
        setMovieImage(context, viewHolder, movie)
        setFavoriteStatus(context, viewHolder, movie)
    }

    private fun setMovieImage(
        context: Context,
        viewHolder: ViewHolderSimilarMovieList,
        movie: MovieModel?
    ) {
        val options =
            RequestOptions().centerCrop().placeholder(R.drawable.placeholder_similar_movie).error(R.drawable.placeholder_similar_movie)
        Glide.with(context).load(ApiConstants.IMAGES_POSTER_BASE_URL + movie?.poster_path)
            .apply(options).into(viewHolder.moviePoster)
    }

    private fun setFavoriteStatus(
        context: Context,
        viewHolder: ViewHolderSimilarMovieList,
        movie: MovieModel?
    ) {
        if (GeneralUtils.isMovieFavorite(context, movie?.id?.toString() ?: "")) {
            viewHolder.favoriteIcon.setImageDrawable(context.resources.getDrawable(R.drawable.favorites_icon))
        } else {
            viewHolder.favoriteIcon.setImageDrawable(context.resources.getDrawable(R.drawable.favorite_not_icon))
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }

    class ViewHolderSimilarMovieList(binding: ListItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var movieTitle: TextView = binding.movieTitle
        var movieYear: TextView = binding.movieYear
        var moviePoster: RoundedImageView = binding.moviePoster
        var movieRating: TextView = binding.ratingNumber
        var favoriteIcon: ImageView = binding.favoriteIcon
    }
}