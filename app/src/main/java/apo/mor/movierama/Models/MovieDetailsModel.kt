package apo.mor.movierama.Models

import com.google.gson.annotations.SerializedName

/**
 * Created by amoropoulos on 18/9/21.
 */
data class MovieDetailsModel(

    @SerializedName("adult")
    val adult : Boolean,

    @SerializedName("backdrop_path")
    val backdrop_path : String,

    @SerializedName("budget")
    val budget : Int,

    @SerializedName("genres")
    val genres : List<Genres>,

    @SerializedName("homepage")
    val homepage : String,

    @SerializedName("id")
    val id : Int,

    @SerializedName("imdb_id")
    val imdb_id : String,

    @SerializedName("original_language")
    val original_language : String,

    @SerializedName("original_title")
    val original_title : String,

    @SerializedName("overview")
    val overview : String,

    @SerializedName("popularity")
    val popularity : Double,

    @SerializedName("poster_path")
    val poster_path : String,

    @SerializedName("release_date")
    val release_date : String,

    @SerializedName("revenue")
    val revenue : Int,

    @SerializedName("runtime")
    val runtime : Int,

    @SerializedName("status")
    val status : String,

    @SerializedName("tagline")
    val tagline : String,

    @SerializedName("title")
    val title : String,

    @SerializedName("video")
    val video : Boolean,

    @SerializedName("vote_average")
    val vote_average : Double,

    @SerializedName("vote_count")
    val vote_count : Int,

    @SerializedName("credits")
    val credits : Credits
)

data class Genres (

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String
)

data class Credits (

    @SerializedName("cast")
    val cast : ArrayList<Cast>,
)

data class Cast (

    @SerializedName("adult")
    val adult : Boolean,

    @SerializedName("gender")
    val gender : Int,

    @SerializedName("id")
    val id : Int,

    @SerializedName("known_for_department")
    val known_for_department : String,

    @SerializedName("name")
    val name : String,

    @SerializedName("original_name")
    val original_name : String,

    @SerializedName("popularity")
    val popularity : Double,

    @SerializedName("profile_path")
    val profile_path : String,

    @SerializedName("cast_id")
    val cast_id : Int,

    @SerializedName("character")
    val character : String,

    @SerializedName("credit_id")
    val credit_id : String,

    @SerializedName("order")
    val order : Int,

    @SerializedName("job")
    val job : String
)