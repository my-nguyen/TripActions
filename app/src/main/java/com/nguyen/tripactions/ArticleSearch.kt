package com.nguyen.tripactions

data class ArticleSearch(val response: Response)

data class Response(val docs: List<Doc>)

data class Doc(val abstract: String,
               val web_url: String,
               val multimedia: List<MultimediaAS>,
               val headline: Headline,
               val section_name: String,
               val subsection_name: String,
               val byline: Byline
)

data class MultimediaAS(val subtype: String, val url: String)

data class Headline(val main: String)

data class Byline(val original: String)