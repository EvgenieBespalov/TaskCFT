package com.example.cft1.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft1.Models.BINInfoModel
import com.example.cft1.R

class BINsHistoryAdapter(): RecyclerView.Adapter<BINsHistoryAdapter.MyViewHolder>() {

    public var binInfo: MutableList<BINInfoModel>? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binCard: TextView = itemView.findViewById(R.id.rowBINCard)
        val bankName: TextView = itemView.findViewById(R.id.rowBankName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        holder.binCard.text = binInfo?.get(position)?.BIN
        holder.bankName.text = binInfo?.get(position)?.bank?.name
    }

    fun update(mainData: MutableList<BINInfoModel>){
        this.binInfo = mainData
        notifyDataSetChanged()
    }

    fun myGetItemCount(): Int {
        return binInfo?.size ?: 0
    }

    override fun getItemCount() = myGetItemCount()
}