package com.example.newsapp.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.newsapp.R
import com.example.newsapp.model.Category

class CategoriesAdapter(var items: List<Category >) : Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        var categoryTitle : TextView = itemView.findViewById(R.id.itemCategoryTitle)
        var categoryImg : ImageView = itemView.findViewById(R.id.categoryImg)
        var categoryBg: Button = itemView.findViewById(R.id.btntest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val category = items.get(position)
        holder.categoryTitle.text = category.title
        holder.categoryImg.setImageResource(category.imageId)
//       val myColor = Color.BLACK
//        holder.btn.setBackgroundTintList(ColorStateList.valueOf(myColor))
        holder.categoryBg.setBackgroundTintList(ColorStateList.valueOf(category.colorId))
        if(category.isLeftSided) holder.categoryBg.setBackgroundResource(R.drawable.category_left_layout) else holder.categoryBg.setBackgroundResource(R.drawable.category_right_layout)

        holder.categoryBg.setOnClickListener {
            categoryClick?.onCategoryClick(category, position)
        }

    }

    override fun getItemCount(): Int {
       return items.size
    }

    var categoryClick : OnCategory? = null
    interface OnCategory{
        fun onCategoryClick(category: Category , position: Int)
    }

}