package gladun.vlad.weather.data.network

import gladun.vlad.weather.data.model.WeatherResponse
import gladun.vlad.weather.inject.BackgroundDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import java.lang.Exception
import javax.inject.Inject

interface WeatherApi {
    @GET("venues/weather.json")
    suspend fun getWeather(): WeatherResponse
}


class WeatherApiClient @Inject constructor(
    private val weatherApi: WeatherApi,
    @BackgroundDispatcher private val coroutineDispatcher: CoroutineDispatcher) {

    suspend fun getWeatherData(): WeatherResponse = withContext(coroutineDispatcher) {
        try {
            val result = weatherApi.getWeather()
            if (!result.isOkay) {
                throw InvalidDataException("Data is not OK!")
            } else {
                result
            }
        } catch (e: Exception) {
            //TODO: map to known errors to handle specific scenarios -> move the error mapping to some base API client class
            throw e
        }
    }
}