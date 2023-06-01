package com.example.uas_nurani

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter (var mContext: Context, var images: List<Image>
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>()

{
    inner class ImageViewHolder (var v: View) : RecyclerView.ViewHolder(v) {
        val imageSrc = v.findViewById<ImageView>(R.id._image)
        val title = v.findViewById<TextView>(R.id._title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(v)
    }
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val newList = images[position]
        holder.imageSrc.loadImage(newList.imageSrc)
        holder.title.text = newList.imageTitle
        holder.v.setOnClickListener {

            val imageSrc = newList.imageSrc
            val imageTitle = newList.imageTitle
            val imageDesc = newList.imageDesc

            val mIntent = Intent(mContext, DetailActivity::class.java)
            mIntent.putExtra("IMAGESRC", imageSrc)
            mIntent.putExtra("IMAGETITLE", imageTitle)
            mIntent.putExtra("IMAGEDESC", imageDesc)
            mContext.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int = images.size
}