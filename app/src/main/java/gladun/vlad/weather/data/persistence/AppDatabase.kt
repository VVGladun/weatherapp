package gladun.vlad.weather.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import gladun.vlad.weather.data.persistence.dao.CountryDao
import gladun.vlad.weather.data.persistence.dao.ForecastDao
import gladun.vlad.weather.data.persistence.entity.CountryEntity
import gladun.vlad.weather.data.persistence.entity.VenueForecastEntity
import gladun.vlad.weather.data.persistence.entity.VenuesWithCountryDetails

@Database(
    entities = [CountryEntity::class, VenueForecastEntity::class],
    views = [VenuesWithCountryDetails::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    abstract fun forecastDao(): ForecastDao
}

/**
 * Add app database migrations to the Room builder
 */
fun <T : RoomDatabase> RoomDatabase.Builder<T>.addAppMigrations(): RoomDatabase.Builder<T> {

    fun migration(from: Int, to: Int, migrationBlock: (SupportSQLiteDatabase) -> Unit): Migration {
        return object : Migration(from, to) {
            override fun migrate(database: SupportSQLiteDatabase) = migrationBlock.invoke(database)
        }
    }

    // For the demo purposes only:
    // adding migration scripts if the DB structure (and version) has changed
    addMigrations(
        migration(1, 2) {
            it.execSQL("ALTER TABLE venue_forecast ADD COLUMN condition_icon TEXT")
        }
    )

    return this
}