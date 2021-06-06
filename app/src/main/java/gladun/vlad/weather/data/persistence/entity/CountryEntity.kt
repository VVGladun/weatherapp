package gladun.vlad.weather.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "country_name") val countryName: String
)