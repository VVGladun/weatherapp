package gladun.vlad.weather.inject

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

//TODO: Hilt/Dagger modules: okhttp, moshi, retrofit clients, room etc

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Reusable
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
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
}

object DatabaseModule {
    
}