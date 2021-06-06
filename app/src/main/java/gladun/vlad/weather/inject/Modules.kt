package gladun.vlad.weather.inject

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gladun.vlad.weather.BuildConfig
import gladun.vlad.weather.data.network.WeatherApi
import gladun.vlad.weather.data.network.WeatherApiClient
import gladun.vlad.weather.data.persistence.AppDatabase
import gladun.vlad.weather.data.persistence.addAppMigrations
import gladun.vlad.weather.data.persistence.dao.CountryDao
import gladun.vlad.weather.data.persistence.dao.ForecastDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideRetrofitBuilder(moshi: Moshi): Retrofit.Builder = Retrofit.Builder().addConverterFactory(
        MoshiConverterFactory.create(moshi)
    )

    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Reusable
    fun provideOkHttpClient(clientBuilder: OkHttpClient.Builder): OkHttpClient = clientBuilder.build()

    @Provides
    @Reusable
    fun provideWeatherApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): WeatherApi {
        return retrofitBuilder
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .build()
            .create(WeatherApi::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "weather"
            )
            .addAppMigrations()
            .build()
    }

    @Provides
    fun provideCountryDao(appDatabase: AppDatabase): CountryDao {
        return appDatabase.countryDao()
    }

    @Provides
    fun provideForecastDao(appDatabase: AppDatabase): ForecastDao {
        return appDatabase.forecastDao()
    }

}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @BackgroundDispatcher
    fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

// Qualifiers to inject two different dispatchers based on the annotation
@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class BackgroundDispatcher
