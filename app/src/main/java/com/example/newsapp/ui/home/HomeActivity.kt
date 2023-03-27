package com.example.newsapp.ui.home
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.model.Category
import com.example.newsapp.ui.home.fragments.CategoriesFragment
import com.example.newsapp.ui.home.fragments.NewsFragment
import com.example.newsapp.ui.home.fragments.SettingsFragment


class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var icDrawer: ImageView
    lateinit var categories: TextView
    lateinit var settings: TextView
    val categoryFragment = CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)
        pushFragment(categoryFragment)
        initViews()
        initListeners()

    }
    fun initViews(){
        drawerLayout = findViewById(R.id.drawer_layout)
        icDrawer = findViewById(R.id.drawerIcon)
        categories = findViewById(R.id.categoriesDrawerTv)
        settings = findViewById(R.id.settingsDrawerTv)
    }
    fun initListeners(){
        categoryFragment.onCategoryClick = object:CategoriesFragment.OnCategoryClick{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment(category))
            }

        }
        icDrawer.setOnClickListener {
            drawerLayout.open()
        }
        categories.setOnClickListener {
            pushFragment(categoryFragment)
            drawerLayout.close()
        }
        settings.setOnClickListener {
            pushFragment(SettingsFragment())
            drawerLayout.close()
        }
    }

    private fun pushFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()


    }

}