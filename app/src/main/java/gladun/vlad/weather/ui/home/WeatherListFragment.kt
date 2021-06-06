package gladun.vlad.weather.ui.home

import androidx.fragment.app.viewModels
import gladun.vlad.weather.R
import gladun.vlad.weather.ui.common.BaseFragment

class WeatherListFragment : BaseFragment<WeatherListViewModel>(R.layout.fragment_weather_list) {
    override val viewModel: WeatherListViewModel by viewModels()

    override fun handleLoading(isLoading: Boolean): Boolean {
        return false
    }
}