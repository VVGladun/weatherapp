package gladun.vlad.weather.ui.home.list

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gladun.vlad.weather.R
import gladun.vlad.weather.data.model.WeatherListItem
import gladun.vlad.weather.databinding.ItemVenueWeatherBinding
import gladun.vlad.weather.ui.common.BaseListAdapter
import gladun.vlad.weather.util.toFullDateString

class WeatherListAdapter(private val resources: Resources) : BaseListAdapter<WeatherListItem, WeatherListAdapter.WeatherListItemViewHolder>() {
    override fun areItemsTheSame(oldItem: WeatherListItem, newItem: WeatherListItem): Boolean {
        return oldItem.venueId == newItem.venueId
    }

    override fun areContentsTheSame(oldItem: WeatherListItem, newItem: WeatherListItem): Boolean {
        return oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListAdapter.WeatherListItemViewHolder {
        val binding = ItemVenueWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherListItemViewHolder(binding)
    }

    override fun bindItem(holder: WeatherListItemViewHolder, item: WeatherListItem, position: Int) {
        holder.binding.itemVenueTitle.text = item.venueName
        holder.binding.itemVenueCondition.text = item.condition
        holder.binding.itemVenueTemperature.text = resources.getString(R.string.temp_celsius, item.temp)
        holder.binding.itemVenueUpdated.text = resources.getString(R.string.last_updated, item.lastUpdated.toFullDateString())
    }

    inner class WeatherListItemViewHolder(val binding: ItemVenueWeatherBinding) : RecyclerView.ViewHolder(binding.root)
}