package com.nguyen.tripactions.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesService {
    // https://api.nytimes.com/svc/search/v2/articlesearch.json?q=messi&api-key=GYWXJ04BtYKmLWLwGouVEON0y34KNYgh
    @GET("search/v2/articlesearch.json")
    fun articleSearch(@Query("q") query: String?, @Query("page") page: Int, @Query("begin_date") beginDate: String?, @Query("fq") filterQuery: String?, @Query("sort") sort: String?, @Query("api-key") apiKey: String) : Call<ArticleSearch>

    @GET("search/v2/articlesearch.json")
    fun articleSearch(@Query("q") query: String, @Query("page") page: Int, @Query("api-key") apiKey: String) : Call<ArticleSearch>

    @GET("topstories/v2/home.json")
    fun home(@Query("api-key") apiKey: String) : Call<Home>
}
