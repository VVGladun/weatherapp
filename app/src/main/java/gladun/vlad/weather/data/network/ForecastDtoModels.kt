package gladun.vlad.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class WeatherResponse (
    @Json(name = "isOkay")
    val isOkay: Boolean = false

//TODO: add other fields
)


