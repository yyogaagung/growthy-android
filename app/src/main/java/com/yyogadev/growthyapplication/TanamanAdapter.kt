package com.yyogadev.growthyapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yyogadev.growthyapplication.retrofit.response.DataItem
import com.yyogadev.growthyapplication.retrofit.response.TanamanItem
import com.yyogadev.growthyapplication.ui.home.financial.TransaksiAdapter

class TanamanAdapter(private val listTanaman: List<TanamanItem>) : RecyclerView.Adapter<TanamanAdapter.ViewHolder>(){

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTanaman: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName : TextView = itemView.findViewById(R.id.tv_plant_name)
        val tvNameLatin : TextView = itemView.findViewById(R.id.tv_plant_latin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return TanamanAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTanaman.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTanaman[position]

        Glide.with(holder.itemView.context)
            .load(data.plantImgNormal)
            .apply { centerCrop() }
            .into(holder.imgTanaman)

        holder.tvName.text = data.localName
        holder.tvNameLatin.text = data.species

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTanaman[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(description: TanamanItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}

