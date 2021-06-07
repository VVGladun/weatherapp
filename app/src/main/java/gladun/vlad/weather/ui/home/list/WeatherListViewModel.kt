package gladun.vlad.weather.ui.home.list

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.weather.data.WeatherRepository
import gladun.vlad.weather.ui.common.BaseViewModel
import gladun.vlad.weather.ui.home.list.WeatherListFragmentDirections.Companion.actionHomeToWeatherFilter
import gladun.vlad.weather.util.asLiveData
import gladun.vlad.weather.util.postIfRequired
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {
    private val _isWeatherDataVisibleData = MutableLiveData(false)
    val isWeatherDataVisibleData = _isWeatherDataVisibleData.asLiveData()

    init {
        initDataRefresh()
        launchWithErrorHandling {
            weatherRepository.getWeatherDataItemCount().collect {
                _isWeatherDataVisibleData.postValue(it > 0)
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

    fun onFilterClicked() {
        navigateTo(actionHomeToWeatherFilter())
    }
}