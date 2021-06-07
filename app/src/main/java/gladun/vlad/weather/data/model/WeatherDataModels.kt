package gladun.vlad.weather.data.model

import android.content.res.Resources
import gladun.vlad.weather.R
import gladun.vlad.weather.data.persistence.entity.CountryEntity
import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
import gladun.vlad.weather.util.toFullDateString
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

data class WeatherListItem(
    val venueId: String,
    val venueName: String,
    val condition: String,
    val temp: String,
    val countryId: String,
    val lastUpdated: ZonedDateTime
) {
    companion object {
        fun fromEntity(entity: VenueForecastEntity): WeatherListItem {
            val localDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(entity.lastUpdated),
                ZoneId.systemDefault()
            )

            return WeatherListItem(
                venueId = entity.id,
                venueName = entity.venueName,
                condition = entity.condition.orEmpty(),
                temp = entity.temp.orEmpty(),
                countryId = entity.countryId,
                lastUpdated = localDateTime)
        }
    }
}

data class CountryFilterItem(
    val countryId: String,
    val countryName: String,
    val isSelected: Boolean
) {
    companion object {
        fun fromEntity(entity: CountryEntity, isSelected: Boolean): CountryFilterItem {
            return CountryFilterItem(
                countryId = entity.id,
                countryName = entity.countryName,
                isSelected = isSelected
            )
        }
    }
}

data class WeatherDetails(
    val venueId: String,
    val venueName: String,
    val condition: String,
    val temp: String,
    val feelsLike: String?,
    val humidity: String?,
    val wind: String?,
    val lastUpdated: String
) {
    companion object {
        fun fromEntity(entity: VenueForecastEntity, resources: Resources): WeatherDetails {
            val localDateTime = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(entity.lastUpdated),
                ZoneId.systemDefault()
            )

            fun getTempString(field: String?) = if (field.isNullOrBlank()) "N/A"
                else resources.getString(R.string.temp_celsius, field)

            return WeatherDetails(
                venueId = entity.id,
                venueName = entity.venueName,
                condition = entity.condition.orEmpty(),
                temp = getTempString(entity.temp),
                feelsLike = getTempString(entity.feelsLike),
                humidity = entity.humidity,
                wind = entity.wind,
                lastUpdated = resources.getString(R.string.last_updated, localDateTime.toFullDateString()))
        }
    }
}