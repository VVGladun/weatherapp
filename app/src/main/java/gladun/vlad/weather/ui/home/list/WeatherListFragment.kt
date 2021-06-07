package gladun.vlad.weather.ui.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayoutMediator
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isWeatherDataVisibleData.observe { isFilterVisible ->
            binding?.weatherListFilterButton?.isVisible = isFilterVisible
            binding?.itemVenueDivider?.isVisible = isFilterVisible
        }

        binding!!.weatherListPager.apply {
            adapter = TabsPageAdapter(this@WeatherListFragment)
        }

        val tabLayout = binding!!.weatherListTabs
        TabLayoutMediator(tabLayout, binding!!.weatherListPager) { tab, position ->
            tab.text = SortSettings.values()[position].displayName
        }.attach()

        binding!!.weatherListFilterButton.setOnClickListener { viewModel.onFilterClicked() }
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

enum class SortSettings(val displayName: String) {
    VenueName("A-Z"),
    Temperature("Temperature"),
    LastUpdated("Last updated")
}