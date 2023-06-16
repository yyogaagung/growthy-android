package com.yyogadev.growthyapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop

class PenyakitAdapter (private val listPenyakit: List<DiseaseItem>) : RecyclerView.Adapter<PenyakitAdapter.ViewHolder>(){
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPenyakit: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvDiseaseName : TextView = itemView.findViewById(R.id.tv_diseases_name)
        val tvDiseaseNameLatin : TextView = itemView.findViewById(R.id.tv_diseases_latin)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_disease, parent, false)
        return PenyakitAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPenyakit.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listPenyakit[position]

        Glide.with(holder.itemView.context)
            .load(data.diseaseImgNormal)
            .apply { centerCrop() }
            .into(holder.imgPenyakit)

        holder.tvDiseaseName.text = data.diseaseLocalName
        holder.tvDiseaseNameLatin.text = data.scienticName

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPenyakit[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(description: DiseaseItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}