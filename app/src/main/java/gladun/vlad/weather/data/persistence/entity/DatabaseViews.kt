package gladun.vlad.weather.data.persistence.entity

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView("SELECT v.*, " +
        "c.id as c_id, c.country_name as c_country_name " +
        "FROM venue_forecast v " +
        "INNER JOIN country c ON v.country_id = c.id " +
        "ORDER BY c_id, v.venue_name")
data class VenuesWithCountryDetails(
    @Embedded
    val venueForecast: VenueForecastEntity,
    @Embedded(prefix = "c_")
    val country: CountryEntity
)