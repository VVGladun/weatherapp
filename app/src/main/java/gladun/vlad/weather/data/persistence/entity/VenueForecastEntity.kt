package gladun.vlad.weather.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "venue_forecast",
    indices = [
        Index("country_id", unique = false)
    ]
)
data class VenueForecastEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "venue_name") val venueName: String,
    @ColumnInfo(name = "country_id") val countryId: String,
    @ColumnInfo(name = "condition") val condition: String,
    @ColumnInfo(name = "wind") val wind: String,
    @ColumnInfo(name = "humidity") val humidity: String,
    @ColumnInfo(name = "temp") val temp: String,
    @ColumnInfo(name = "feels_like") val feelsLike: String,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long
)