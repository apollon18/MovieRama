package apo.mor.movierama.Utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import apo.mor.movierama.R
import apo.mor.movierama.Utils.GeneralUtils.TypographyWeights.*
import io.github.inflationx.calligraphy3.CalligraphyTypefaceSpan
import io.github.inflationx.calligraphy3.TypefaceUtils
import java.text.SimpleDateFormat
import java.util.*

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

        fun getYearFromDate(dateString: String) : String {
            if (dateString.isEmpty()) {
                return ""
            }
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = sdf.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date?: Date()
            return cal.get(Calendar.YEAR).toString()
        }

        fun isMovieFavorite(context: Context, id: String) : Boolean {
            val favoriteIds = SharedPreferenceUtil.getFavoriteMoviesId(context)
            if (favoriteIds.isNotEmpty()) {
                return favoriteIds.contains(id)
            }
            return false
        }

        fun getDifferentStyleText(context: Context, title: String, year: String) : String {
            val titlePart = getFormattedText(Pair(title, FormattedModel(getSpecificSpan(context, Bold), null, ForegroundColorSpan(context.getColor(R.color.white))))).toString()
            val yearPart = getFormattedText(Pair(year, FormattedModel(getSpecificSpan(context, Regular), null, ForegroundColorSpan(context.getColor(R.color.gray_text))))).toString()
            return "$titlePart $yearPart"
        }

        private fun getFormattedText(vararg params: Pair<String, FormattedModel>): SpannableStringBuilder {
            val builder = SpannableStringBuilder("")
            var tempText = ""
            for (param in params) {
                val formattedModel = param.second
                builder.append(param.first)
                formattedModel.weightSpan?.let {
                    builder.setSpan(it, tempText.length, builder.length, Spannable.SPAN_COMPOSING)
                }
                formattedModel.sizeSpan?.let {
                    builder.setSpan(it, tempText.length, builder.length, Spannable.SPAN_COMPOSING)
                }
                formattedModel.colorSpan?.let {
                    builder.setSpan(it, tempText.length, builder.length, Spannable.SPAN_COMPOSING)
                }
                tempText += param.first
            }
            return builder
        }

        private fun getSpecificSpan(context: Context, weight: TypographyWeights) : CalligraphyTypefaceSpan {
            return when (weight) {
                Bold -> CalligraphyTypefaceSpan(
                    TypefaceUtils.load(context.assets, "fonts/Roboto-Bold.ttf")
                )

                Regular -> CalligraphyTypefaceSpan(
                    TypefaceUtils.load(context.assets, "fonts/Roboto-Regular.ttf")
                )
                Inter -> CalligraphyTypefaceSpan(
                    TypefaceUtils.load(context.assets, "fonts/Inter-ExtraBold.ttf")
                )
            }
        }
    }

    class FormattedModel(var weightSpan: CalligraphyTypefaceSpan? = null, var sizeSpan: AbsoluteSizeSpan? = null, var colorSpan: ForegroundColorSpan? = null)

    enum class TypographyWeights {
        Inter,
        Regular,
        Bold
    }
}