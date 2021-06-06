package gladun.vlad.weather.ui.home

import androidx.fragment.app.viewModels
import gladun.vlad.weather.R
import gladun.vlad.weather.ui.common.BaseFragment

class WeatherDetailsFragment : BaseFragment<WeatherDetailsViewModel>(R.layout.fragment_weather_details) {

    override val viewModel: WeatherDetailsViewModel by viewModels()

    override fun handleLoading(isLoading: Boolean): Boolean {
        return false
    }
}