package gladun.vlad.weather.ui.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import gladun.vlad.weather.App
import gladun.vlad.weather.R
import gladun.vlad.weather.databinding.FragmentWeatherListBinding
import gladun.vlad.weather.ui.common.BaseFragment

@AndroidEntryPoint
class WeatherListFragment
    : BaseFragment<WeatherListViewModel>(R.layout.fragment_weather_list), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentWeatherListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override val viewModel: WeatherListViewModel by viewModels()

    override fun handleLoading(isLoading: Boolean): Boolean {
        _binding?.weatherListSwiperefresh?.isRefreshing = isLoading
        return _binding != null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        val view = _binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weatherListRecyclerview.apply {
            adapter = WeatherListAdapter(context.applicationContext.resources)
        }

        viewModel.weatherData.observe {
            (binding.weatherListRecyclerview.adapter as? WeatherListAdapter)?.items = it
        }

        binding.weatherListSwiperefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        viewModel.initDataRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}