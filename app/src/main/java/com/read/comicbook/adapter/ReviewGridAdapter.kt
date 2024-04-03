package com.read.comicbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.read.comicbook.R
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.utils.FileUtils

class ReviewGridAdapter(val onClick: (item: PostInfoModel) -> Unit) : PostAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.card_grid_large_row, parent, false)
        return NewUpdatesViewHolder(listItem)
    }

    inner class NewUpdatesViewHolder(itemView: View) : PostViewHolder(itemView) {
        private var img: ImageView
        private var title: TextView
        private var desc: TextView
        private var materialCardView: MaterialCardView

        init {
            img = itemView.findViewById(R.id.roundedImageView)
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            materialCardView = itemView.findViewById(R.id.materialCardView)
        }


        override fun bind(item: PostInfoModel) {
            Glide.with(itemView.context)
                .load(FileUtils.retrieveImgFile(item.thumbnail?:"", itemView.context))
                .into(img)
            title.text = item.title
            desc.text = item.desc
            materialCardView.setOnClickListener {
                onClick(item)
            }
        }
    }
}