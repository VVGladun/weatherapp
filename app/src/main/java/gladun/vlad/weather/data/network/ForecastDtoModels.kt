package gladun.vlad.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse (
    @Json(name = "isOkay")
    val isOkay: Boolean = true

//TODO: add other fields
)


