package com.moter.tutorial.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moter.tutorial.R
import com.moter.tutorial.network.NewsService
import com.moter.tutorial.view.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

        newsAdapter= NewsAdapter()
        rvNewsList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter=newsAdapter
        }

        lifecycleScope.launch {
           mainViewModel.listData.collect {
               newsAdapter.submitData(it)
           }
        }


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