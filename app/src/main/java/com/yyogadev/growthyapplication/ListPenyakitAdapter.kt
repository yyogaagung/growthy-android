package com.yyogadev.growthyapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListPenyakitAdapter(private val listPenyakit: ArrayList<Penyakit>) : RecyclerView.Adapter<ListPenyakitAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvDiseaseName: TextView = itemView.findViewById(R.id.tv_diseases_name)
        val tvDiseaseLatin: TextView = itemView.findViewById(R.id.tv_diseases_latin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_disease, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listPenyakit.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (nama_tradisional, nama_ilmiah, photo) = listPenyakit[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvDiseaseName.text = nama_tradisional
        holder.tvDiseaseLatin.text = nama_ilmiah

        holder.itemView.setOnClickListener {
            //val intentDetail = Intent(holder.itemView.context, RincianActivity::class.java)
            //intentDetail.putExtra("key_hewan", listHewan[holder.adapterPosition])
            //holder.itemView.context.startActivity(intentDetail)
            onItemClickCallback.onItemClicked(listPenyakit[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Penyakit)
    }
}