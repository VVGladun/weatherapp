package gladun.vlad.weather.ui.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.WeatherListPageBinding
import gladun.vlad.weather.ui.common.Action
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class WeatherListPageFragment(private val sortSettings: SortSettings)
    : BaseFragment<WeatherListPageViewModel>(R.layout.weather_list_page) {

    private var binding: WeatherListPageBinding? = null
    override val viewModel: WeatherListPageViewModel by viewModels()

    override val screenTitleResId = R.string.screen_weather_title
    override val showUp = false

    override fun handleLoading(isLoading: Boolean) = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherListPageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.weatherListRecyclerview.apply {
            adapter = WeatherListAdapter(context.applicationContext.resources) {
                viewModel.navigateToDetails(it.venueId)
            }
        }

        viewModel.weatherData.observe { weatherItem ->
            (binding?.weatherListRecyclerview?.adapter as? WeatherListAdapter)?.items = weatherItem.sortedWith(
                when (sortSettings) {
                    SortSettings.Temperature -> compareBy { it.temp.toInt() }
                    SortSettings.LastUpdated -> compareBy { it.lastUpdated }
                    else ->  compareBy { it.venueName }
                }
            )
        }
    }

    override fun executeAction(action: Action) {
        (parentFragment as? BaseFragment<*>)?.executeAction(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}