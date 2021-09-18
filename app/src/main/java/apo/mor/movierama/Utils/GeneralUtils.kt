package apo.mor.movierama.Utils

import android.content.Context
import apo.mor.movierama.R
import java.util.*

/**
 * Created by amoropoulos on 18/9/21.
 */
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
    }
}