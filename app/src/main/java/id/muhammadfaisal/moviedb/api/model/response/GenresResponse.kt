package id.muhammadfaisal.moviedb.api.model.response

import com.google.gson.annotations.SerializedName

data class GenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int
)
