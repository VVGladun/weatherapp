package gladun.vlad.weather.data

import android.content.res.Resources
import gladun.vlad.weather.R
import gladun.vlad.weather.data.model.CountryFilterItem
import gladun.vlad.weather.data.model.WeatherDetails
import gladun.vlad.weather.data.model.WeatherListItem
import gladun.vlad.weather.data.model.toEntity
import gladun.vlad.weather.data.network.WeatherApiClient
import gladun.vlad.weather.data.persistence.PreferenceStore
import gladun.vlad.weather.data.persistence.dao.CountryDao
import gladun.vlad.weather.data.persistence.dao.ForecastDao
import gladun.vlad.weather.inject.BackgroundDispatcher
import gladun.vlad.weather.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApiClient: WeatherApiClient,
    private val countryDao: CountryDao,
    private val forecastDao: ForecastDao,
    private val preferenceStore: PreferenceStore,
    private val resources: Resources,
    @BackgroundDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    /**
     * Triggers full data update - updates both countries and forecasts
     */
    suspend fun updateWeatherData() = withContext(coroutineDispatcher) {
        // load the data from the backend
        val newWeatherData = weatherApiClient.getWeatherData()

        // cleanse the weather data, to avoid storing incomplete / useless items
        // - we can add analytics to log non-fatal exceptions for them later
        val forecastDtos = newWeatherData.data.filter {
            it.weatherTemp?.toIntOrNull() != null && it.lastUpdated != null && it.country?.countryId != null
                    && !it.venueName.isNullOrBlank() && !it.venueId.isNullOrEmpty()
        }

        // save into the database
        forecastDao.upsert(forecastDtos.map { it.toEntity() })
        countryDao.upsert(forecastDtos
            .distinctBy { it.country!!.countryId }
            .map { it.country!!.toEntity() })
    }

    /**
     * Flow of filtered weather data for the list of forecasts
     */
    fun getFilteredWeatherList(): Flow<List<WeatherListItem>> {
        return combine(
            forecastDao.getAllForecasts(),
            preferenceStore.selectedCountry
        ) { forecasts, countryFilter ->
            if (countryFilter == Constants.ID_ALL_COUNTRIES) {
                forecasts
            } else {
                forecasts.filter { it.countryId == countryFilter }
            }.map { WeatherListItem.fromEntity(it) }
        }
    }

    fun getWeatherDataItemCount(): Flow<Int> = forecastDao.getForecastItemCount()

    fun getWeatherDetails(venueId: String): Flow<WeatherDetails> {
        return forecastDao.getForecastForVenue(venueId).mapNotNull {
            WeatherDetails.fromEntity(it, resources)
        }
    }

    private val showAllCountriesItem = CountryFilterItem(
        countryId = Constants.ID_ALL_COUNTRIES,
        countryName = resources.getString(R.string.filter_all_countries),
        isSelected = true
    )

    fun getCountryFilterItems(): Flow<List<CountryFilterItem>> {
        return combine(
            countryDao.getAll(),
            preferenceStore.selectedCountry
        ) { countries, countryFilter ->
            if (countryFilter == Constants.ID_ALL_COUNTRIES) {
                countries.map { CountryFilterItem.fromEntity(it, false) }.toMutableList().apply {
                    add(0, showAllCountriesItem)
                }
            } else {
                countries.map { CountryFilterItem.fromEntity(it, it.id == countryFilter) }.toMutableList().apply {
                    add(0, showAllCountriesItem.copy(isSelected = false))
                }
            }
        }
    }

    suspend fun setFilterByCountry(countryId: String) {
        preferenceStore.saveSelectedCountry(countryId)
    }

}