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
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.utils.FileUtils

open class SubCategoryAdapter(val onClick: (item: PostInfoModel) -> Unit) : RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    private val modelList = arrayListOf<PostInfoModel>()

    open inner class SubCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var img: ImageView
        var materialCardView: MaterialCardView
        init {
            name = itemView.findViewById(R.id.name)
            img = itemView.findViewById(R.id.img)
            materialCardView = itemView.findViewById(R.id.materialCardView)
        }
        open fun bind(item: PostInfoModel) {
            name.text = item.title
            Glide.with(itemView.context)
                .load(FileUtils.retrieveImgFile(item.thumbnail?:"", itemView.context))
                .into(img)
            materialCardView.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_category_row, parent, false)
        return SubCategoryViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }

    fun setModelList(newModelList: List<PostInfoModel>){
        modelList.clear()
        modelList.addAll(newModelList)
        notifyDataSetChanged()
    }
}