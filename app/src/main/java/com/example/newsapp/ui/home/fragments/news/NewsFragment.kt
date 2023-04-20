package com.example.newsapp.ui.home.fragments.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.AppConstants
import com.example.newsapp.R
import com.example.newsapp.adapter.ArticlesAdapter
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.*
import com.example.newsapp.ui.home.HomeActivity.OnSearchClick
import com.example.newsapp.ui.home.fragments.ArticleDetailsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment(val category : Category) : Fragment() {

    lateinit var viewModel : NewsViewModel
     var isLoading: Boolean =false
    var PAGE_SIZE =20
    var currentPage = 1
    lateinit var tabId : String
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var adapter: ArticlesAdapter =ArticlesAdapter(listOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListners(PAGE_SIZE,currentPage)
        observeViewModel()
        viewModel.getTabs(category.id)
//        var searchClick = object : OnSearchClick{
//            override fun onSearchClick() {
//
//            }
//
//        }
    }

    fun initViews(view: View){
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        tabLayout = view.findViewById(R.id.tabs)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.articleRV)
        recyclerView.adapter = adapter
        adapter.onArticleClick = object : ArticlesAdapter.OnArticleClick{
            override fun onArticleClick(article: Article, position: Int) {
                pushFragment(ArticleDetailsFragment(article))
            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                val visibleThreshold = 10
                if (!isLoading && totalItemCount-lastVisibleItemPosition <= visibleThreshold ){
                    currentPage++
                    isLoading = true
                    viewModel.getArticles(tabId,PAGE_SIZE,currentPage)
                }
            }
        })

    }
    fun pushFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, fragment)?.commit()
    }

    fun showTabs(tabs: List<TabDM>){
        tabs.forEach {
            val newTab = tabLayout.newTab()
            newTab.text = it?.name
            newTab.tag = it.id?: ""
            tabLayout.addTab(newTab)
        }

        Log.e("NEWS FRAGMENT", "#### SHOW TABS")
    }
    fun initListners(pageSize: Int,currentPage : Int){
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab?.tag!! as String
                tabId = id
                viewModel.getArticles(tabId,PAGE_SIZE,currentPage)
            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.e("ON TAB UN SELECTED ", "")
                val id = tab?.tag!! as String
                tabId = id
                viewModel.getArticles(tabId,PAGE_SIZE,currentPage)

                    ///Todo: Fix this
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val id = tab?.tag!! as String
                tabId = id
                viewModel.getArticles(tabId,PAGE_SIZE,currentPage)
            }

        })
    }

    fun observeViewModel(){
        viewModel.tabsLiveData.observe(viewLifecycleOwner
        ) {tabsList->
            viewModel.tabsLiveDataStatus.observe(viewLifecycleOwner,{
                if(it) showTabs(tabsList as List<TabDM>)
                else  Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_LONG).show()
            })

        }
        viewModel.articlesLiveData.observe(viewLifecycleOwner
        ) { articlesList->
            viewModel.articlesLiveDataStatus.observe(viewLifecycleOwner,{
                if(it)
                    adapter.changeData(articlesList)
                else  Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_LONG).show()
            })
        }
        viewModel.progressBarLiveData.observe(viewLifecycleOwner,{
        })
    }

    var onArticleClick:OnArcticleClick? = null
    interface OnArcticleClick{
        fun OnArcticleClick(article: Article)
    }
}