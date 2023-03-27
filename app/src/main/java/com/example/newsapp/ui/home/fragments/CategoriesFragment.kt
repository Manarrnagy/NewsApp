package com.example.newsapp.ui.home.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.CategoriesAdapter
import com.example.newsapp.model.Category

class CategoriesFragment: Fragment() {
    lateinit var categoriesRecyclerview : RecyclerView
    lateinit var categoriesAdapter: CategoriesAdapter
    val categoriesInfo: List<Category> = listOf(
        Category("sports","Sports",R.drawable.sports, Color.parseColor("#C91C22"),true),
        Category("entertainment","Entertainment",R.drawable.politics, Color.parseColor("#003E90"),false),
        Category("health","Health",R.drawable.health, Color.parseColor("#ED1E79"),true),
        Category("business","Business",R.drawable.business, Color.parseColor("#CF7E48"),false),
        Category("technology","Technology",R.drawable.environment, Color.parseColor("#4882CF"),true),
        Category("science","Science",R.drawable.science, Color.parseColor("#F2D352"),false),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }
    fun initViews(view: View){
        categoriesRecyclerview = view.findViewById(R.id.categoriesRV)
        categoriesAdapter = CategoriesAdapter(categoriesInfo)
        categoriesAdapter.categoryClick = object : CategoriesAdapter.OnCategory{
            override fun onCategoryClick(category: Category, position: Int) {
                onCategoryClick?.onCategoryClick(category)


            }

        }
        categoriesRecyclerview.adapter = categoriesAdapter


    }

    var onCategoryClick:OnCategoryClick? = null
    interface OnCategoryClick{
        fun onCategoryClick(category: Category)
    }
}