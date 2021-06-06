package gladun.vlad.weather.data.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import gladun.vlad.weather.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Alternative to Shared Preferences.
 * Fields can be set asynchronously or observed as Flows.
 */
class PreferenceStore(private val context: Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        private const val COUNTRY_PREF_NAME = "selected_country"
        val COUNTRY_ID_KEY = stringPreferencesKey(name = COUNTRY_PREF_NAME)
    }

    suspend fun saveSelectedCountry(countryId: String) {
        context.dataStore.edit { prefs ->
            prefs[COUNTRY_ID_KEY] = countryId
        }
    }

    suspend fun clearFilter() = saveSelectedCountry(Constants.NO_COUNTRY_CODE)

    val selectedCountry: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[COUNTRY_ID_KEY] ?: Constants.NO_COUNTRY_CODE
    }
}