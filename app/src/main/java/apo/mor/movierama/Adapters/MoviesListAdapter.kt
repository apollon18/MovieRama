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
import apo.mor.movierama.Utils.GeneralUtils
import apo.mor.movierama.Utils.SharedPreferenceUtil
import apo.mor.movierama.databinding.ListItemMainMovieBinding
import com.makeramen.roundedimageview.RoundedImageView


class MoviesListAdapter (
    private var context: Context,
    private var movieList: ArrayList<MovieModel>?,
    private var listener: MovieListListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderMovieList(ListItemMainMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movieList?.get(holder.absoluteAdapterPosition)
        val viewHolder = holder as ViewHolderMovieList
        viewHolder.movieTitle.text = GeneralUtils.getDifferentStyleText(context, movie?.title ?: "", GeneralUtils.getYearFromDate(movie?.release_date ?: ""))
        viewHolder.movieRating.text = movie?.vote_average.toString()
        setFavoriteStatus(context, viewHolder, movie)
        setFavoriteAction(viewHolder, movie, position)
        viewHolder.itemLayout.setOnClickListener { listener.onMovieSelected(movie?.id?.toString()?: "") }
    }

    private fun setFavoriteStatus(context: Context, viewHolder: ViewHolderMovieList, movie: MovieModel?) {
        if (GeneralUtils.isMovieFavorite(context, movie?.id?.toString()?: "")) {
            viewHolder.favoriteText.text = context.resources.getString(R.string.added_to_favorites_text)
            viewHolder.favoriteText.setTextColor(context.resources.getColor(R.color.favorite))
            viewHolder.favoriteIcon.setImageDrawable(context.resources.getDrawable(R.drawable.favorites_icon))
        } else {
            viewHolder.favoriteText.text = context.resources.getString(R.string.add_to_favorites_text)
            viewHolder.favoriteText.setTextColor(context.resources.getColor(R.color.gray_text))
            viewHolder.favoriteIcon.setImageDrawable(context.resources.getDrawable(R.drawable.favorite_not_icon))
        }
    }

    private fun setFavoriteAction(viewHolder: ViewHolderMovieList, movie: MovieModel?, position: Int) {
        viewHolder.favoriteIcon.setOnClickListener {
            if (GeneralUtils.isMovieFavorite(context, movie?.id?.toString()?: "")) {
                SharedPreferenceUtil.removeMovieFromFavorites(context, movie?.id?.toString()?: "")
            } else {
                SharedPreferenceUtil.saveMovieToFavorites(context, movie?.id?.toString()?: "")
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }

    fun replaceList(searchList: ArrayList<MovieModel>) {
        movieList?.clear()
        movieList?.addAll(searchList)
        notifyDataSetChanged()
    }

    class ViewHolderMovieList(binding: ListItemMainMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var movieTitle: TextView = binding.movieTitle
        var moviePoster: RoundedImageView = binding.moviePoster
        var movieRating: TextView = binding.ratingNumber
        var favoriteText: TextView = binding.favoriteText
        var favoriteIcon: ImageView = binding.favoriteIcon
        var itemLayout: CardView = binding.movieCardLayout
    }
}