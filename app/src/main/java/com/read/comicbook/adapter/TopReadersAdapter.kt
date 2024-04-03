package com.read.comicbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.read.comicbook.R
import com.read.comicbook.model.UserModel
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation

class TopReadersAdapter : RecyclerView.Adapter<TopReadersAdapter.TopReadersViewHolder>() {

    private val modelList = arrayListOf<UserModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopReadersViewHolder {
        val listItem =
            LayoutInflater.from(parent.context).inflate(R.layout.card_small_row, parent, false)
        return TopReadersViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: TopReadersViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }

    inner class TopReadersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView
        private var img: ImageView

        init {
            img = itemView.findViewById(R.id.roundedImageView)
            name = itemView.findViewById(R.id.name)
        }


        fun bind(item: UserModel) {
            Glide.with(itemView.context)
                .load(item.img)
                .transform(
                    CropCircleWithBorderTransformation(4,ContextCompat.getColor(itemView.context, R.color.white))
                )
                .into(img)

            name.text = item.name
        }
    }

    fun setModelList(newModelList: List<UserModel>){
        modelList.clear()
        modelList.addAll(newModelList)
        notifyDataSetChanged()
    }
}