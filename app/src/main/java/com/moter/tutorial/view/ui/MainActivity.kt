package com.moter.tutorial.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moter.tutorial.R
import com.moter.tutorial.network.NewsService
import com.moter.tutorial.view.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(NewsService.create())
        ).get(MainViewModel::class.java)

        viewManager = LinearLayoutManager(this)

        rvNewsList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }
        mainViewModel.newsResponse.observe(this, Observer {
            Log.d("MainActivity", "response ${it.toString()}")
            newsAdapter = NewsAdapter()
//            newsAdapter.submitData(it.articles)
            rvNewsList.apply {
                adapter = newsAdapter
            }
        })

        rvNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val layoutManager = rvNewsList.layoutManager as LinearLayoutManager
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                mainViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })

    }
}