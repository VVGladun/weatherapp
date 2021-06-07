package gladun.vlad.weather.ui.home.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.FragmentWeatherFilterBinding
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class WeatherFilterFragment : BaseFragment<WeatherFilterViewModel>(R.layout.fragment_weather_filter) {

    private var binding: FragmentWeatherFilterBinding? = null
    override val viewModel: WeatherFilterViewModel by viewModels()

    override val screenTitleResId = R.string.screen_filter_title
    override val showUp = true
    override fun handleLoading(isLoading: Boolean) = false
    override fun getBackButtonIcon() = R.drawable.ic_close_24dp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherFilterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.weatherFilterRecyclerview.apply {
            adapter = WeatherFilterAdapter {
                viewModel.onCountrySelected(it.countryId)
            }
        }

        viewModel.weatherData.observe {
            (binding?.weatherFilterRecyclerview?.adapter as? WeatherFilterAdapter)?.items = it
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}