package com.read.comicbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.read.comicbook.R
import com.read.comicbook.model.CategoryModel

open class CategoryAdapter(val onClick: (item: CategoryModel) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val modelList = arrayListOf<CategoryModel>()

    open inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var img: ImageView
        var card: MaterialCardView
        init {
            name = itemView.findViewById(R.id.name)
            img = itemView.findViewById(R.id.img)
            card = itemView.findViewById(R.id.materialCardView)
        }
        open fun bind(item: CategoryModel) {
            name.text = item.name
            Glide.with(itemView.context)
                .load(item.img)
                .into(img)
            card.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)
        return CategoryViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }

    fun setModelList(newModelList: List<CategoryModel>){
        modelList.clear()
        modelList.addAll(newModelList)
        notifyDataSetChanged()
    }
}