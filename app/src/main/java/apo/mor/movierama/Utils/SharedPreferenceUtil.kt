package apo.mor.movierama.Utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceUtil {

    companion object {
        private const val BASIC_PATH = "apo.mor.movierama.prefs"

        private const val favoriteMoviesIds = "favoriteMoviesIds"

        /**
         * @param valueToSave
         * @param preferenceName
         */
        private fun saveSharedPreference(context: Context, valueToSave: String?, preferenceName: String) {
            try {
                val editor = getSharedEditor(context, BASIC_PATH)
                editor.putString(preferenceName, valueToSave)
                editor.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun loadSharedPreference(context: Context, preferenceName: String): String {
            var returnValue = ""
            returnValue = try {
                val sharedPref: SharedPreferences =
                    context.getSharedPreferences(BASIC_PATH, Context.MODE_PRIVATE)
                sharedPref.getString(preferenceName, returnValue).toString()
            } catch (e: Exception) {
                ""
            }
            return returnValue
        }

        private fun getSharedEditor(context: Context, dir: String): SharedPreferences.Editor {
            val sharedPref = context.getSharedPreferences(dir, Context.MODE_PRIVATE)
            return sharedPref.edit()
        }

        fun getFavoriteMoviesId(context: Context) : ArrayList<String> {
            val jsonString = loadSharedPreference(context, favoriteMoviesIds)
            if (jsonString.isEmpty()) {
                return ArrayList()
            }
            return Gson().fromJson(jsonString, object : TypeToken<ArrayList<String>>(){}.type)
        }

        fun saveMovieToFavorites(context: Context, id: String) {
            val newList : ArrayList<String> = ArrayList()
            val existingList = getFavoriteMoviesId(context)
            if (existingList.isNotEmpty()) {
                newList.addAll(existingList)
            }
            newList.add(id)
            saveSharedPreference(context, Gson().toJson((newList)), favoriteMoviesIds)
        }

        fun removeMovieFromFavorites(context: Context, id: String) {
            val newList: ArrayList<String> = ArrayList()
            val existingList = getFavoriteMoviesId(context)
            if (existingList.isNotEmpty()) {
                existingList.removeIf { it == id }
            }
            newList.addAll(existingList)
            saveSharedPreference(context, Gson().toJson((newList)), favoriteMoviesIds)
        }
    }
}