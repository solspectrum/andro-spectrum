package org.androdevlinux.spectrum.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.androdevlinux.spectrum.BuildConfig
import org.androdevlinux.spectrum.data.PreferenceProvider
import org.androdevlinux.spectrum.data.network.ApiService
import org.androdevlinux.spectrum.data.network.NetworkConnectionInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val BASE_URL = "https://test.privatespectrum.xyz"

    @Provides
    fun providePreferenceProvider(@ApplicationContext context: Context) =
        PreferenceProvider(context)

    @Provides
    fun provideApiService(@ApplicationContext context: Context, pref: PreferenceProvider)
            : ApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor {
                it.proceed(it.request().newBuilder().also { req ->

                }.build())
            }.also {
                if (BuildConfig.DEBUG)
                    it.addInterceptor(logging)
            }.connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
