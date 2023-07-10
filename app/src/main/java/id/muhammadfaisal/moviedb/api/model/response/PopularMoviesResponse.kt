package id.muhammadfaisal.moviedb.api.model.response

data class PopularMoviesResponse(
	val page: Int? = null,
	val totalPages: Int? = null,
	val results: List<ResultsItem>? = null,
	val totalResults: Int? = null
)

data class ResultsItem(
	val overview: String? = null,
	val originalLanguage: String? = null,
	val originalTitle: String? = null,
	val video: Boolean? = null,
	val title: String? = null,
	val genreIds: List<Int?>? = null,
	val posterPath: String? = null,
	val backdropPath: String? = null,
	val releaseDate: String? = null,
	val popularity: Any? = null,
	val voteAverage: Any? = null,
	val id: Int? = null,
	val adult: Boolean? = null,
	val voteCount: Int? = null
)

