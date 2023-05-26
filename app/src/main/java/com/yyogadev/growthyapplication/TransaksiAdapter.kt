package com.yyogadev.growthyapplication

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TransaksiAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class TransaksiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnItemClickCallback {
//        fun onItemClicked(selectedNote: Note?, position: Int?)
    }

//    fun addItem(note: Note) {
//        this.listNotes.add(note)
//        notifyItemInserted(this.listNotes.size - 1)
//    }
//    fun updateItem(position: Int, note: Note) {
//        this.listNotes[position] = note
//        notifyItemChanged(position, note)
//    }
//    fun removeItem(position: Int) {
//        this.listNotes.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, this.listNotes.size)
//    }

}