package com.nguyen.tripactions.views

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyen.tripactions.*
import com.nguyen.tripactions.databinding.ActivityMainBinding
import com.nguyen.tripactions.dependencies.MyApplication
import com.nguyen.tripactions.models.Article
import com.nguyen.tripactions.models.ArticleAdapter
import com.nguyen.tripactions.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var binding: ActivityMainBinding
    lateinit var articles: MutableList<Article>
    lateinit var adapter: ArticleAdapter
    lateinit var scrollListener : EndlessRecyclerViewScrollListener
    var query: String = ""
    var isSearchable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = GregorianCalendar()
        val formatter = SimpleDateFormat("EEEE, MMMM dd, yyyy")
        val today = formatter.format(calendar.time)
        binding.textToday.text = today

        binding.buttonSearch.setOnClickListener {
            isSearchable = !isSearchable
            if (isSearchable) {
                binding.editSearch.visibility = View.VISIBLE
                binding.buttonGo.visibility = View.VISIBLE
            } else {
                binding.editSearch.visibility = View.GONE
                binding.buttonGo.visibility = View.GONE
            }
        }
        binding.buttonGo.setOnClickListener {
            search()
        }
        binding.editSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    search()
                    return true
                }
                return false
            }
        })

        articles = mutableListOf<Article>()
        adapter = ArticleAdapter(this, articles)
        binding.recyclerArticles.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerArticles.layoutManager = layoutManager
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerArticles.addItemDecoration(divider)
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (query.isEmpty()) {
                    home()
                } else {
                    articleSearch(query, page)
                }
            }
        }
        binding.recyclerArticles.addOnScrollListener(scrollListener)

        home()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE;
        binding.recyclerArticles.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.INVISIBLE;
        binding.recyclerArticles.visibility = View.VISIBLE
    }

    private fun search() {
        query = binding.editSearch.text.toString()
        if (query.isEmpty()) {
            home()
        } else {
            articleSearch(query, 0)
        }
        hideKeyboard()
    }

    private fun home() {
        showProgress()
        mainViewModel.fetchHome().observe(this, {
            val size = articles.size
            if (size != 0) {
                articles.clear()
                adapter.notifyItemRangeRemoved(0, size)
            }
            Log.d(TAG, "home size: ${it.size}")
            articles.addAll(it)
            adapter.notifyItemRangeInserted(0, it.size)
            hideProgress();
        })
    }

    private fun articleSearch(query: String, page: Int) {
        showProgress()
        mainViewModel.articleSearch(query, page).observe(this, {
            val size = articles.size
            if (size != 0) {
                articles.clear()
                adapter.notifyItemRangeRemoved(0, size)
            }
            Log.d(TAG, "search size: ${it.size}")
            articles.addAll(it)
            adapter.notifyItemRangeInserted(0, it.size)
            if (page != 0) {
                val position = page*10 - scrollListener.visibleThreshold
                binding.recyclerArticles.layoutManager?.scrollToPosition(position)
            }
            hideProgress()
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
