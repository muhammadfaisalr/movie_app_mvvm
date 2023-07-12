package id.muhammadfaisal.moviedb.api.model.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: MutableList<ResultsReview>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class ResultsReview(

	@field:SerializedName("author_details")
	val authorDetails: AuthorDetails,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class AuthorDetails(

	@field:SerializedName("avatar_path")
	val avatarPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)
