package apo.mor.movierama.Utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import apo.mor.movierama.Models.Cast

import apo.mor.movierama.R
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GeneralUtils {

    companion object {

        fun getHeaderText(context: Context): String {
            val calendar = Calendar.getInstance()
            return when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 5..12 -> context.resources.getString(R.string.main_header_morning)
                in 12..19 -> context.resources.getString(R.string.main_header_afternoon)
                in 19..23 -> context.resources.getString(R.string.main_header_evening)
                in 0..5 -> context.resources.getString(R.string.main_header_evening)
                else -> "Hello"
            }
        }

        fun getYearFromDate(dateString: String): String {
            if (dateString.isEmpty()) {
                return ""
            }
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = sdf.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date ?: Date()
            return cal.get(Calendar.YEAR).toString()
        }

        fun isMovieFavorite(context: Context, id: String): Boolean {
            val favoriteIds = SharedPreferenceUtil.getFavoriteMoviesId(context)
            if (favoriteIds.isNotEmpty()) {
                return favoriteIds.contains(id)
            }
            return false
        }

        fun getDirectorName(cast: ArrayList<Cast>) : String {
            for (people in cast) {
                if (people.job == "Director")
                    return people.name
            }
            return ""
        }

        fun getTopBilledCast(cast: ArrayList<Cast>?) : ArrayList<Cast> {
            val topCast : ArrayList<Cast> = ArrayList()
            try {
                cast?.subList(0, 5)?.let { topCast.addAll(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return topCast
        }

        fun getRatingToOneDecimal(rating: Double): Double {
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            return df.format(rating).toDouble()
        }

        fun slideFragmentUp(view: FrameLayout?) {
            view?.let {
                view.visibility = View.VISIBLE
                val animate = TranslateAnimation(0f, 0f, view.height.toFloat(), 0f)
                animate.duration = 700L
                animate.interpolator = DecelerateInterpolator()
                animate.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {}
                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                view.startAnimation(animate)
            }
        }

        fun slideFragmentDown(view: FrameLayout?) {
            view?.let {
                val animate = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
                animate.duration = 500L
                animate.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) {
                        view.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                view.startAnimation(animate)
            }
        }
    }
}