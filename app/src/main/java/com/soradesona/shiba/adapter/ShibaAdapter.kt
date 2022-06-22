package com.soradesona.shiba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soradesona.shiba.R
import com.soradesona.shiba.ShibaImageDownloader
import kotlinx.android.synthetic.main.shiba_rv_item.view.*
import javax.inject.Inject


class ShibaAdapter @Inject constructor(
    private val shibaImageDownloader : ShibaImageDownloader
    ) : RecyclerView.Adapter<ShibaAdapter.DataViewHolder>() {

    private var shibas: ArrayList<String> = ArrayList()

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.shiba_rv_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = shibas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int){
        val eachShiba = shibas[position]

        Glide.with(holder.itemView.shiba_image.context)
            .load(eachShiba)
            .placeholder(R.drawable.shibaplaceholder)
            .into(holder.itemView.shiba_image)

        holder.itemView.setOnClickListener {
            shibaImageDownloader.downloadImage(eachShiba)
        }

    }

    fun addData(shibas: List<String>) {
        this.shibas.apply {
            clear()
            addAll(shibas)
        }
    }

}