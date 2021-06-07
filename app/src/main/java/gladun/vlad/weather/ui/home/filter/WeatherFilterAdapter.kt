package gladun.vlad.weather.ui.home.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gladun.vlad.weather.data.model.CountryFilterItem
import gladun.vlad.weather.databinding.ItemFilterBinding
import gladun.vlad.weather.ui.common.BaseListAdapter

class WeatherFilterAdapter(private val onItemClicked: (CountryFilterItem) -> Unit) : BaseListAdapter<CountryFilterItem, WeatherFilterAdapter.WeatherFilterItemViewHolder>() {
    override fun areItemsTheSame(oldItem: CountryFilterItem, newItem: CountryFilterItem): Boolean {
        return oldItem.countryId == newItem.countryId
    }

    override fun areContentsTheSame(oldItem: CountryFilterItem, newItem: CountryFilterItem): Boolean {
        return oldItem.countryId == newItem.countryId && oldItem.isSelected == newItem.isSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherFilterAdapter.WeatherFilterItemViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherFilterItemViewHolder(binding)
    }

    override fun bindItem(holder: WeatherFilterItemViewHolder, item: CountryFilterItem, position: Int) {
        holder.binding.itemFilterTitle.text = item.countryName
        holder.binding.itemFilterIcon.isVisible = item.isSelected
        holder.binding.root.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }

    inner class WeatherFilterItemViewHolder(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root)
}