package gladun.vlad.weather.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ForecastDao : BaseDao<VenueForecastEntity>() {
    @Query("select * from venue_forecast")
    abstract fun getAllForecasts(): Flow<List<VenueForecastEntity>>

    @Query("select * from venue_forecast where country_id = :countryId order by venue_name")
    abstract fun getForecastsForCountry(countryId: String): Flow<List<VenueForecastEntity>>
}