package gladun.vlad.weather.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ForecastDao : BaseDao<VenueForecastEntity>() {
    @Query("SELECT * FROM venue_forecast")
    abstract fun getAllForecasts(): Flow<List<VenueForecastEntity>>

    @Query("SELECT * FROM venue_forecast WHERE country_id = :countryId ORDER BY venue_name LIMIT 1")
    abstract fun getForecastsForCountry(countryId: String): Flow<List<VenueForecastEntity>>

    @Query("SELECT * FROM venue_forecast WHERE id = :venueId")
    abstract fun getForecastForVenue(venueId: String): Flow<List<VenueForecastEntity>>
}