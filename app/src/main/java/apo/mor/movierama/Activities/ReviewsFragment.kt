package apo.mor.movierama.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import apo.mor.movierama.Adapters.ReviewsAdapter
import apo.mor.movierama.Models.ReviewModel
import apo.mor.movierama.R
import apo.mor.movierama.databinding.FragmentReviewsBinding

class ReviewsFragment : Fragment() {

    private var binding: FragmentReviewsBinding? = null
    private var reviews: ArrayList<ReviewModel>? = null
    private var reviewsAdapter: ReviewsAdapter? = null

    companion object {
        fun getInstance(reviews: ArrayList<ReviewModel>): ReviewsFragment {
            val fragment = ReviewsFragment()
            fragment.reviews = reviews
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reviews, container, false)
        binding = FragmentReviewsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCloseButton()
        initReviews()
    }

    private fun initCloseButton() {
        binding?.modalCloseButton?.setOnClickListener {
            (activity as MovieDetailsActivity)?.let { it.closeReviewsModal() }
        }
    }

    private fun initReviews() {
        val layoutManager = LinearLayoutManager(activity)
        binding?.reviewsList?.layoutManager = layoutManager
        reviewsAdapter = ReviewsAdapter(reviews)
        binding?.reviewsList?.adapter = reviewsAdapter
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}