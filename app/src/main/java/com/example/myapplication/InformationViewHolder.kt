package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InformationViewHolder(itemView: View, val onClick: (Information) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val informationIdView: TextView = itemView.findViewById(R.id.information_id)
    private val informationTextView: TextView = itemView.findViewById(R.id.information_text)
    private var currentInformation: Information? = null

    init {
        itemView.setOnClickListener {
            currentInformation?.let {
                onClick(it)
            }
        }
    }

    fun bind(information: Information) {
        currentInformation = information
        informationTextView.text = information.name
        informationIdView.text = information.id
    }
}