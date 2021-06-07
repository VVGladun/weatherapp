package gladun.vlad.weather.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ForecastDao : BaseDao<VenueForecastEntity>() {
    @Query("SELECT * FROM venue_forecast")
    abstract fun getAllForecasts(): Flow<List<VenueForecastEntity>>

    @Query("SELECT * FROM venue_forecast WHERE country_id = :countryId ORDER BY venue_name")
    abstract fun getForecastsForCountry(countryId: String): Flow<List<VenueForecastEntity>>

    @Query("SELECT * FROM venue_forecast WHERE id = :venueId LIMIT 1")
    abstract fun getForecastForVenue(venueId: String): Flow<VenueForecastEntity>

    @Query("SELECT count(*) FROM venue_forecast")
    abstract fun getForecastItemCount(): Flow<Int>
}