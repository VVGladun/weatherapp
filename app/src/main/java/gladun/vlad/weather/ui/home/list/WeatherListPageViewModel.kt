package gladun.vlad.weather.ui.home.list

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.weather.data.WeatherRepository
import gladun.vlad.weather.data.model.WeatherListItem
import gladun.vlad.weather.ui.common.BaseViewModel
import gladun.vlad.weather.ui.home.list.WeatherListFragmentDirections.Companion.actionHomeToWeatherDetails
import gladun.vlad.weather.util.asLiveData
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherListPageViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {
    private val _weatherData = MutableLiveData<List<WeatherListItem>>(emptyList())
    val weatherData = _weatherData.asLiveData()

    init {
        launchWithErrorHandling {
            weatherRepository.getFilteredWeatherList().collect {
                _weatherData.postValue(it)
            }
        }
    }

    fun navigateToDetails(venueId: String) {
        navigateTo(actionHomeToWeatherDetails(venueId = venueId))
    }
}