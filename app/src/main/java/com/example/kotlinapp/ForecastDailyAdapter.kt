package com.example.kotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ForecastDailyViewholder(view: View, private val tempDisplaySettings :TempDisplaySettings) : RecyclerView.ViewHolder(view) {

    private val txtTemp = view.findViewById<TextView>(R.id.txtTemp)
    private val txtComment = view.findViewById<TextView>(R.id.txtComment)

    fun bind(forecastDaily: ForecastDaily) {
        txtTemp.text = formatTemp(forecastDaily.temp, tempDisplaySettings.getSetting())
        txtComment.text = forecastDaily.description
    }
}

class ForecastDailyAdapter(private val tempDisplaySettings: TempDisplaySettings,
                           private val clickHandler: (ForecastDaily) -> Unit
) : ListAdapter<ForecastDaily, ForecastDailyViewholder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object: DiffUtil.ItemCallback<ForecastDaily>() {
            override fun areItemsTheSame(oldItem: ForecastDaily, newItem: ForecastDaily): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: ForecastDaily,
                newItem: ForecastDaily
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDailyViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast, parent, false)
        return ForecastDailyViewholder(itemView, tempDisplaySettings)
    }

    override fun onBindViewHolder(holder: ForecastDailyViewholder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }
}