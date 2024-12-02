package com.example.retrofitforecaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

data class WeatherItem(val date: String, val description: String, val temperature: String, val iconRes: Int)

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val weatherItems = mutableListOf<WeatherItem>()

    fun submitList(items: List<WeatherItem>) {
        weatherItems.clear()
        weatherItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = weatherItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = weatherItems.size

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateView: TextView = itemView.findViewById(R.id.tv_date)
        private val tempView: TextView = itemView.findViewById(R.id.tv_temp)
        private val iconView: ImageView = itemView.findViewById(R.id.iv_icon)

        fun bind(item: WeatherItem) {
            dateView.text = item.date
            tempView.text = item.temperature
            iconView.setImageResource(item.iconRes)
        }
    }
}
