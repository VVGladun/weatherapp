package gladun.vlad.weather.ui.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.FragmentWeatherListBinding
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class WeatherListFragment
    : BaseFragment<WeatherListViewModel>(R.layout.fragment_weather_list), SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentWeatherListBinding? = null
    override val viewModel: WeatherListViewModel by viewModels()

    override val screenTitleResId = R.string.screen_weather_title
    override val showUp = false

    override fun handleLoading(isLoading: Boolean): Boolean {
        binding?.weatherListSwiperefresh?.isRefreshing = isLoading
        return binding != null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.weatherListRecyclerview.apply {
            adapter = WeatherListAdapter(context.applicationContext.resources) {
                viewModel.navigateToDetails(it.venueId)
            }
        }

        viewModel.weatherData.observe {
            (binding?.weatherListRecyclerview?.adapter as? WeatherListAdapter)?.items = it
        }

        binding!!.weatherListSwiperefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        viewModel.initDataRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}