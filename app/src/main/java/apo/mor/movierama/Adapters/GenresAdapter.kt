package apo.mor.movierama.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apo.mor.movierama.Models.Genres
import apo.mor.movierama.databinding.ListItemGenreBinding

class GenresAdapter (
    private var genresList: ArrayList<Genres>?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderGenreList(
            ListItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val genre = genresList?.get(holder.absoluteAdapterPosition)
        val viewHolder = holder as ViewHolderGenreList
        viewHolder.genreName.text = genre?.name
    }

    override fun getItemCount(): Int {
        return genresList?.size ?: 0
    }

    class ViewHolderGenreList(binding: ListItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var genreName: TextView = binding.genreName
    }
}