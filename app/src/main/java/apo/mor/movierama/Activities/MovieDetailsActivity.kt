package apo.mor.movierama.Activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import apo.mor.movierama.databinding.ActivityMovieDetailsBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class MovieDetailsActivity : AppCompatActivity() {

    private var binding: ActivityMovieDetailsBinding? = null
    private var movieId: String? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        movieId = intent.getStringExtra("id")
        if (movieId?.isEmpty() == true) {
            finish()
        }
        getMovieDetails()
    }

    private fun getMovieDetails() {

    }
}