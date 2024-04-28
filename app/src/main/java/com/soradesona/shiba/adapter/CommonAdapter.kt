package com.soradesona.shiba.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soradesona.shiba.R
import com.soradesona.shiba.ShibaImageDownloader
import java.lang.reflect.Type


class CommonAdapter(
    private val shibaImageDownloader : ShibaImageDownloader,
    private val category: String
) : RecyclerView.Adapter<CommonAdapter.DataViewHolder>() {

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

        val placeholder = getPlaceholder()

        Glide.with(holder.itemView.findViewById<ImageView>(R.id.shiba_image).context)
            .load(eachShiba)
            .placeholder(placeholder)
            .into(holder.itemView.findViewById<ImageView>(R.id.shiba_image))

        holder.itemView.setOnClickListener {
            shibaImageDownloader.downloadImage(eachShiba)
        }
    }

    fun addData(shibas: List<String>) {
        this.shibas.apply {
            clear()
            addAll(shibas)
            notifyDataSetChanged()
        }
    }

    private fun getPlaceholder() : Int {
        when(category){
            "shibes" -> return R.drawable.shiba_category
            "cats" -> return R.drawable.cat_category
            "birds" -> return R.drawable.bird_category
        }
        return R.drawable.shiba_category
    }

}