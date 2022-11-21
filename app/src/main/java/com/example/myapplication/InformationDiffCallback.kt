package com.example.myapplication

import androidx.recyclerview.widget.DiffUtil

object InformationDiffCallback : DiffUtil.ItemCallback<Information>() {
    override fun areItemsTheSame(oldItem: Information, newItem: Information): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Information, newItem: Information): Boolean {
        return oldItem.id == newItem.id
    }
}