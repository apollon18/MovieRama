package apo.mor.movierama.Models

import com.google.gson.annotations.SerializedName

data class ResultModel (

    @SerializedName("page")
    val page : Int,

    @SerializedName("results")
    val results : ArrayList<MovieModel>,

    @SerializedName("total_pages")
    val total_pages : Int,

    @SerializedName("total_results")
    val total_results : Int
)
