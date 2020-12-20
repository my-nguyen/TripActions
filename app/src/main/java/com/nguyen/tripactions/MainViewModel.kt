package com.nguyen.tripactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: ArticleRepository) : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    fun fetchHome() : LiveData<List<Article>> {
        return repository.home()
    }

    fun articleSearch(query: String, page: Int) : LiveData<List<Article>> {
        return repository.articleSearch(query, page)
    }
}
