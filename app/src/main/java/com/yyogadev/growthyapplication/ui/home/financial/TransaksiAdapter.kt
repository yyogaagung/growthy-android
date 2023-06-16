package com.yyogadev.growthyapplication.ui.home.financial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.retrofit.response.DataItem


class TransaksiAdapter (private val listTransaksi: List<DataItem>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>()  {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textJenis: TextView= itemView.findViewById(R.id.stories_name)
        val tvnominal : TextView = itemView.findViewById(R.id.nominal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTransaksi.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listTransaksi[position]

        if(data != null){
            if(data.type.equals("pemasukan")){
                if (data.descPemasukan == null){
                    holder.textJenis.text = ""
                    holder.tvnominal.text = "-Rp.0"
                }else {
                    holder.textJenis.text = data.descPemasukan.toString()
                    holder.tvnominal.text = "+Rp." + data.pemasukan.toString()
                }
            }

            if(data.type.equals("pengeluaran")){
                if (data.descPengeluaran == null){
                    holder.textJenis.text = ""
                    holder.tvnominal.text = "-Rp.0"
                }else{
                    holder.textJenis.text = data.descPengeluaran.toString()
                    holder.tvnominal.text = "-Rp." + data.pengeluaran.toString()
                }

            }

            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTransaksi[holder.adapterPosition]) }

        }


    }

    interface OnItemClickCallback {
        fun onItemClicked(description: DataItem)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}