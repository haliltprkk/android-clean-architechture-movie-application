package com.haliltprkk.movieapplication.di

import android.content.Context
import com.haliltprkk.movieapplication.BuildConfig
import com.haliltprkk.movieapplication.data.local.CacheHelper
import com.haliltprkk.movieapplication.data.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): MovieService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            })
            .build()
        return client
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

    @Singleton
    @Provides
    fun provideCacheHelper(@ApplicationContext appContext: Context) = CacheHelper(appContext)
}