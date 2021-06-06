package gladun.vlad.weather.data

import gladun.vlad.weather.data.network.WeatherApiClient
import gladun.vlad.weather.data.persistence.PreferenceStore
import gladun.vlad.weather.inject.BackgroundDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    weatherApiClient: WeatherApiClient,
    preferenceStore: PreferenceStore,
    @BackgroundDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    // TODO: update weather data - trigger data update

    // TODO: get filtered list

    // TODO: get details

    // TODO: get countries

    // TODO: update filter

}