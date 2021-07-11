package com.example.kotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class ForecastDailyViewholder(view: View, private val tempDisplaySettings :TempDisplaySettings) : RecyclerView.ViewHolder(view) {

    private val txtTemp = view.findViewById<TextView>(R.id.txtTemp)
    private val txtComment = view.findViewById<TextView>(R.id.txtComment)
    private val txtDate = view.findViewById<TextView>(R.id.txtDate)

    fun bind(dailyForecast: DailyForecast) {
        txtTemp.text = formatTemp(dailyForecast.temp.max, tempDisplaySettings.getSetting())
        txtComment.text = dailyForecast.weather[0].description
        txtDate.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))
    }
}

class ForecastDailyAdapter(private val tempDisplaySettings: TempDisplaySettings,
                           private val clickHandler: (DailyForecast) -> Unit
) : ListAdapter<DailyForecast, ForecastDailyViewholder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object: DiffUtil.ItemCallback<DailyForecast>() {
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
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