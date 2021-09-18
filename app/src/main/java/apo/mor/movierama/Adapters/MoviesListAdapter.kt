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
        viewHolder.movieTitle.text = movie?.title
        viewHolder.movieRating.text = movie?.vote_count.toString()
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