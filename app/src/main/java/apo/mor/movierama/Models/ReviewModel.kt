package apo.mor.movierama.Models

import com.google.gson.annotations.SerializedName

data class ReviewResult (

    @SerializedName("id")
    val id : Int,

    @SerializedName("page")
    val page : Int,

    @SerializedName("results")
    val results : ArrayList<ReviewModel>,

    @SerializedName("total_pages")
    val total_pages : Int,

    @SerializedName("total_results")
    val total_results : Int
)

data class ReviewModel (

    @SerializedName("author")
    val author : String,

    @SerializedName("author_details")
    val author_details : AuthorDetails,

    @SerializedName("content")
    val content : String,

    @SerializedName("created_at")
    val created_at : String,

    @SerializedName("id")
    val id : String,

    @SerializedName("updated_at")
    val updated_at : String,

    @SerializedName("url")
    val url : String
)

data class AuthorDetails (

    @SerializedName("name")
    val name : String,

    @SerializedName("username")
    val username : String,

    @SerializedName("avatar_path")
    val avatar_path : String,

    @SerializedName("rating")
    val rating : Double
)