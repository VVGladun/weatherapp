package gladun.vlad.weather.data.network

import gladun.vlad.weather.WeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import java.lang.Exception

interface WeatherApi {
//    @GET("venues/weather.json")
    fun getWeather(): WeatherResponse
}

//TODO: inject!
class WeatherApiClient(private val weatherApi: WeatherApi, private val coroutineDispatcher: CoroutineDispatcher) {

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