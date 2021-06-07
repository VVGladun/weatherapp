package gladun.vlad.weather.ui.home.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import gladun.vlad.weather.data.WeatherRepository
import gladun.vlad.weather.data.model.WeatherDetails
import gladun.vlad.weather.data.network.InvalidDataException
import gladun.vlad.weather.ui.common.BaseViewModel
import gladun.vlad.weather.util.asLiveData
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val venueId: String = savedStateHandle.get<String>("venueId")
        ?: throw InvalidDataException("Venue ID must be set!")

    private val _weatherData = MutableLiveData<WeatherDetails>()
    val weatherData = _weatherData.asLiveData()

    init {
        launchWithErrorHandling {
            weatherRepository.getWeatherDetails(venueId).collect {
                _weatherData.postValue(it)
            }
        }
    }
}