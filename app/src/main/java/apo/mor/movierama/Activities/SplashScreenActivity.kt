package apo.mor.movierama.Activities

import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import apo.mor.movierama.Models.MovieModel
import apo.mor.movierama.Models.ResultModel
import apo.mor.movierama.Services.ServiceCalls
import apo.mor.movierama.databinding.ActivitySplashScreenBinding
import com.google.gson.Gson
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {

    private var binding: ActivitySplashScreenBinding? = null
    private var popularList: ArrayList<MovieModel> = ArrayList()
    private var maxRetries: Int = 3
    private var counter: Int = 0

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { ViewPumpContextWrapper.wrap(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setLogoAnimation()
        getPopularMovies()
    }

    private fun setLogoAnimation() {
        val rotateAnimation = RotateAnimation(
            0f,
            359f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.repeatCount = INFINITE
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 3000
        binding?.reelInside?.startAnimation(rotateAnimation)
    }

    private fun getPopularMovies() {
        counter = counter++
        ServiceCalls.getPopularMovieList(page = 1, object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                popularList.addAll(response.body()?.results as ArrayList<MovieModel>)
                redirectToMainList()
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                if (counter <= maxRetries) {
                    getPopularMovies()
                } else {
                    redirectToMainList()
                }
            }
        })
    }

    private fun redirectToMainList() {
        binding?.reelInside?.clearAnimation()
        val mainListIntent = Intent(this@SplashScreenActivity, MainListActivity::class.java)
        mainListIntent.putExtra("list", Gson().toJson(popularList))
        startActivity(mainListIntent)
        finish()
    }
}