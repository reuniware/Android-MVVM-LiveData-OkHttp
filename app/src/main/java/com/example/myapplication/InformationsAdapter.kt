package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class InformationsAdapter(private val onClick: (Information) -> Unit) : ListAdapter<Information, InformationViewHolder>(InformationDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.information_item, parent, false)
        return InformationViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val information = getItem(position)
        holder.bind(information)
    }
}