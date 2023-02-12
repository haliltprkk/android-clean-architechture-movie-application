package com.haliltprkk.movieapplication.di

import android.content.Context
import android.util.Log.VERBOSE
import com.haliltprkk.movieapplication.BuildConfig
import com.haliltprkk.movieapplication.data.api_services.MovieApiService
import com.haliltprkk.movieapplication.data.services.LocalStorageService
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): MovieApiService {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: LoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            },
        ).build()
        return client
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): LoggingInterceptor =
        LoggingInterceptor.Builder().setLevel(Level.BODY).log(VERBOSE).build()

    @Singleton
    @Provides
    fun provideLocalStorageService(@ApplicationContext appContext: Context) = LocalStorageService(appContext)
}
