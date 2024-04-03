package com.read.comicbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.read.comicbook.R
import com.read.comicbook.model.ComicImageModel
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.utils.FileUtils

open class ComicImageAdapter : RecyclerView.Adapter<ComicImageAdapter.ComicImageViewHolder>() {

    private val modelList = arrayListOf<String?>()

    open inner class ComicImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var ivComic: ImageView
        init {
            ivComic = itemView.findViewById(R.id.iv_comic)
        }
        open fun bind(item: String?) {
            Glide.with(itemView.context)
                .load(FileUtils.retrieveImgFile(item?:"", itemView.context))
                .into(ivComic)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicImageViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.comic_image_row, parent, false)
        return ComicImageViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ComicImageViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }

    fun setModelList(newModelList: List<String?>){
        modelList.clear()
        modelList.addAll(newModelList)
        notifyDataSetChanged()
    }
}