package gladun.vlad.weather.data.model

/**
 * Weather data to be used in View models
 */
data class WeatherData (
    val country: Country
    //TODO: all the other fields
)

data class Country(
    val countryId: String,
    val countryName: String
)