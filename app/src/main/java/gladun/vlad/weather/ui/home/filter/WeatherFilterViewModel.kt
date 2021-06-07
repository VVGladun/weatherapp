package gladun.vlad.weather.ui.home.filter

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.weather.data.WeatherRepository
import gladun.vlad.weather.data.model.CountryFilterItem
import gladun.vlad.weather.data.model.WeatherListItem
import gladun.vlad.weather.ui.common.BaseViewModel
import gladun.vlad.weather.ui.home.list.WeatherListFragmentDirections.Companion.actionHomeToWeatherDetails
import gladun.vlad.weather.util.asLiveData
import gladun.vlad.weather.util.postIfRequired
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherFilterViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {
    private val _weatherData = MutableLiveData<List<CountryFilterItem>>(emptyList())
    val weatherData = _weatherData.asLiveData()

    init {
        launchWithErrorHandling {
            weatherRepository.getCountryFilterItems().collect {
                _weatherData.postValue(it)
            }
        }
    }

    fun onCountrySelected(countryId: String) {
        launchWithErrorHandling {
            weatherRepository.setFilterByCountry(countryId)
            navigateBack()
        }
    }
}