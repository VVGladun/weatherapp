package gladun.vlad.weather.ui.home.list

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.weather.data.WeatherRepository
import gladun.vlad.weather.data.model.WeatherListItem
import gladun.vlad.weather.data.network.KnownException
import gladun.vlad.weather.ui.common.BaseViewModel
import gladun.vlad.weather.ui.home.list.WeatherListFragmentDirections.Companion.actionHomeToWeatherDetails
import gladun.vlad.weather.ui.home.list.WeatherListFragmentDirections.Companion.actionHomeToWeatherFilter
import gladun.vlad.weather.util.asLiveData
import gladun.vlad.weather.util.postIfRequired
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {
    private val _weatherData = MutableLiveData<List<WeatherListItem>>(emptyList())
    val weatherData = _weatherData.asLiveData()

    private val _isFilterVisibleData = MutableLiveData(false)
    val isFilterVisibleData = _isFilterVisibleData.asLiveData()

    init {
        initDataRefresh()
        launchWithErrorHandling {
            weatherRepository.getFilteredWeatherList().collect {
                // TODO: move sorting out and combine with the current sort preferences when tab selected
                _weatherData.postValue(it.sortedBy { it.venueName })
                _isFilterVisibleData.postValue(it.isNotEmpty())
            }
        }
    }

    fun initDataRefresh() {
        loading.value = true
        launchWithErrorHandling {
            weatherRepository.updateWeatherData()
            loading.postIfRequired(false)
        }
    }

    fun navigateToDetails(venueId: String) {
        navigateTo(actionHomeToWeatherDetails(venueId = venueId))
    }

    fun onFilterClicked() {
        navigateTo(actionHomeToWeatherFilter())
    }
}