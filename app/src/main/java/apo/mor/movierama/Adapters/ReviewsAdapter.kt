package apo.mor.movierama.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apo.mor.movierama.Models.ReviewModel
import apo.mor.movierama.databinding.ListItemReviewBinding

class ReviewsAdapter(
    private var reviewList: ArrayList<ReviewModel>?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderReviewList(
            ListItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review = reviewList?.get(holder.absoluteAdapterPosition)
        val viewHolder = holder as ViewHolderReviewList
        viewHolder.authorName.text = review?.author_details?.username
        viewHolder.reviewText.text = review?.content
        viewHolder.ratingText.text = review?.author_details?.rating.toString()
    }

    override fun getItemCount(): Int {
        return reviewList?.size ?: 0
    }

    class ViewHolderReviewList(binding: ListItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var authorName: TextView = binding.username
        var reviewText: TextView = binding.reviewText
        var ratingText: TextView = binding.ratingNumber
    }
}