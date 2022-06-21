package com.soradesona.shiba.adapter

import android.app.Application
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soradesona.shiba.R
import com.soradesona.shiba.ShibaApplication
import kotlinx.android.synthetic.main.shiba_rv_item.view.*


class ShibaAdapter : RecyclerView.Adapter<ShibaAdapter.DataViewHolder>() {

    private var shibas: ArrayList<String> = ArrayList()

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(shiba: String) {
            Glide.with(itemView.shiba_image.context)
                .load(shiba)
                .placeholder(R.drawable.shibaplaceholder)
                .into(itemView.shiba_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.shiba_rv_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = shibas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(shibas[position])

    fun addData(shibas: List<String>) {
        this.shibas.apply {
            clear()
            addAll(shibas)
        }
    }

}