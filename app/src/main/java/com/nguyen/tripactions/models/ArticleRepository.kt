package com.nguyen.tripactions.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyen.tripactions.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val NYTimesService: NYTimesService) {
    companion object {
        const val TAG = "ArticleRepository"
    }

    private val homeArticles: MutableLiveData<List<Article>> = MutableLiveData()
    private val searchedArticles: MutableLiveData<List<Article>> = MutableLiveData()
    private var savedQuery: String? = null

    fun home() : LiveData<List<Article>> {
        NYTimesService.home(BuildConfig.NYTIMES_API_KEY)
            .enqueue(object : Callback<Home> {
                override fun onResponse(call: Call<Home>, response: Response<Home>) {
                    val articles = mutableListOf<Article>()
                    val body = response.body()
                    if (body != null) {
                        for (result in body.results) {
                            val article = Article(result)
                            articles.add(article)
                        }
                        homeArticles.value = articles
                    }
                }

                override fun onFailure(call: Call<Home>, t: Throwable) {
                    Log.w(TAG, "home-onFailure $t")
                    homeArticles.value = null
                }
            })
        return homeArticles
    }

    fun articleSearch(query: String, page: Int): LiveData<List<Article>> {
        NYTimesService.articleSearch(query, page, BuildConfig.NYTIMES_API_KEY)
            .enqueue(object : Callback<ArticleSearch> {
                override fun onResponse(call: Call<ArticleSearch>, response: Response<ArticleSearch>) {
                    val articles = mutableListOf<Article>()
                    Log.d(TAG, "query: $query, saved: $savedQuery, page: $page")
                    if (query == savedQuery) {
                        articles.addAll(searchedArticles.value!!)
                    } else {
                        savedQuery = query
                    }

                    val body = response.body()
                    if (body != null) {
                        for (doc in body.response.docs) {
                            val article = Article(doc)
                            articles.add(article)
                        }
                    }
                    Log.d(TAG, "articles size: ${articles.size}")
                    searchedArticles.value = articles
                }

                override fun onFailure(call: Call<ArticleSearch>, t: Throwable) {
                    Log.w(TAG, "articleSearch-onFailure $t")
                    searchedArticles.value = null
                }
            })
        return searchedArticles
    }
}
