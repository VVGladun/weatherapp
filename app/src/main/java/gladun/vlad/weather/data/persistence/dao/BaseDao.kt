package gladun.vlad.weather.data.persistence.dao

import androidx.room.*

@Dao
abstract class BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entities: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnore(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIgnore(entities: List<T>): List<Long>

    @Update
    abstract fun update(entity: T)

    @Update
    abstract fun update(entities: List<T>)

    @Delete
    abstract fun delete(vararg entity: T)

    fun upsert(entity: T) {
        val id = insertIgnore(entity)
        if (id < 0) {
            update(entity)
        }
    }

    fun upsert(entities: List<T>) {

        // Insert entities; indices that already exist will have a -1 id returned
        val insertionIds = insertIgnore(entities)

        // Now we figure out what entities already existed and update them
        insertionIds
            // Zip to create a list of pair<insertion index, entity>
            .zip(entities)
            // We only care about entities that were not inserted, ie. already exist
            .filter { it.first < 0 }
            .map { it.second }
            // We now have list of entities that need updating - so do it
            .takeIf { it.isNotEmpty() }
            ?.let { update(it) }
    }
}