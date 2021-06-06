package gladun.vlad.weather.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import gladun.vlad.weather.data.persistence.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao : BaseDao<CountryEntity>() {
    @Query("select * from country")
    abstract fun getAll(): Flow<List<CountryEntity>>
}