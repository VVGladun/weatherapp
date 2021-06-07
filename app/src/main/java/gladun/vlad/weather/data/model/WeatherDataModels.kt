package gladun.vlad.weather.data.model

import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
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
)