package com.example.newsapp.ui.home
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.ui.home.fragments.CategoriesFragment
import com.example.newsapp.ui.home.fragments.news.NewsFragment
import com.example.newsapp.ui.home.fragments.SettingsFragment


class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var icDrawer: ImageView
    lateinit var categories: TextView
    lateinit var settings: TextView
    lateinit var searchIcon : ImageView
    lateinit var searchIconCover : View
    val categoryFragment = CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)
        pushFragment(categoryFragment)
        initViews()
        Log.e("HOME ACTIVITY ##","On Create")
        initListeners()

    }
    fun initViews(){
        drawerLayout = findViewById(R.id.drawer_layout)
        icDrawer = findViewById(R.id.drawerIcon)
        categories = findViewById(R.id.categoriesDrawerTv)
        settings = findViewById(R.id.settingsDrawerTv)
        searchIcon = findViewById(R.id.searchIcon)
        searchIconCover = findViewById(R.id.searchIconCover)
        searchIconCover.setBackgroundColor(getResources().getColor(R.color.green))
    }
    fun initListeners(){
        categoryFragment.onCategoryClick = object:CategoriesFragment.OnCategoryClick{
            override fun onCategoryClick(category: Category) {
                searchIconCover.setBackgroundColor(Color.TRANSPARENT)
                pushFragment(NewsFragment(category))
            }

        }
        icDrawer.setOnClickListener {
            drawerLayout.open()
        }
        categories.setOnClickListener {
            searchIconCover.setBackgroundColor(getResources().getColor(R.color.green))
            pushFragment(categoryFragment)
            drawerLayout.close()
        }
        settings.setOnClickListener {
            pushFragment(SettingsFragment())
            drawerLayout.close()
        }
        searchIcon.setOnClickListener {
            onSearchClick?.onSearchClick()
        }

    }

    private fun pushFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()


    }

    var onSearchClick : OnSearchClick?= null
    interface OnSearchClick{
        fun onSearchClick()
    }

}