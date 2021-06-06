package gladun.vlad.weather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse (
    @Json(name = "isOkay")
    val isOkay: Boolean = false,

    @Json(name = "data")
    val data: List<VenueForecast>
)

@JsonClass(generateAdapter = false)
data class VenueForecast(
    @Json(name = "_venueID")
    val venueId: String?,

    @Json(name = "_name")
    val venueName: String?,

    @Json(name = "_country")
    val country: CountryDto?,

    @Json(name = "_weatherCondition")
    val weatherCondition: String?,

    @Json(name = "_weatherWind")
    val weatherWind: String?,

    @Json(name = "_weatherHumidity")
    val weatherHumidity: String?,

    @Json(name = "_weatherTemp")
    val weatherTemp: String?,

    @Json(name = "_weatherFeelsLike")
    val weatherFeelsLike: String?,

    @Json(name = "_weatherLastUpdated")
    val lastUpdated: Long?
)

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "_countryID")
    val countryId: String = "",
    @Json(name = "_name")
    val countryName: String = ""
)

@JsonClass(generateAdapter = true)
data class SportDto(
    @Json(name = "sportID")
    val sportId: String? = null,

    @Json(name = "description")
    val description: String? = null
)

