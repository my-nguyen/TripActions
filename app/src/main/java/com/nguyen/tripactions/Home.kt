package com.nguyen.tripactions

data class Home(val results: List<Result>)

data class Result(val section: String,
                  val subsection: String,
                  val title: String,
                  val abstract: String,
                  val url: String,
                  val byline: String,
                  val multimedia: List<MultimediaH>)

data class MultimediaH(val url: String, val format: String)
