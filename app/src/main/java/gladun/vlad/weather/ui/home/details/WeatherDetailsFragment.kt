package gladun.vlad.weather.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.FragmentWeatherDetailsBinding
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment<WeatherDetailsViewModel>(R.layout.fragment_weather_details) {

    override val viewModel: WeatherDetailsViewModel by viewModels()
    private var binding: FragmentWeatherDetailsBinding? = null

    override val screenTitleResId = R.string.screen_weather_title
    override val showUp = true

    override fun handleLoading(isLoading: Boolean): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherData.observe {
            binding?.apply {
                detailsVenueTitle.text = it.venueName
                detailsCondition.text = it.condition
                detailsTemperature.text = it.temp
                detailsFeelsLikeValue.text = it.feelsLike
                detailsHumidityValue.text = it.humidity
                detailsWindValue.text = it.wind
                detailsUpdated.text = it.lastUpdated
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}