package com.read.comicbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.read.comicbook.R
import com.read.comicbook.model.PostInfoModel

open class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val modelList = arrayListOf<PostInfoModel>()

    open inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: PostInfoModel) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.card_large_row, parent, false)
        return PostViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }

    fun setModelList(newModelList: List<PostInfoModel>){
        modelList.clear()
        modelList.addAll(newModelList)
        notifyDataSetChanged()
    }
}