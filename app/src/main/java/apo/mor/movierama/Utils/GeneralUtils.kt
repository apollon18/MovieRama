package apo.mor.movierama.Utils

import android.content.Context
import apo.mor.movierama.Models.Cast

import apo.mor.movierama.R
import java.lang.Exception
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

    }
}