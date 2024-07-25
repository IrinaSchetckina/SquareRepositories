package com.shchotkina.squarerepositories.di

import com.shchotkina.squarerepositories.BuildConfig
import com.shchotkina.squarerepositories.network.RepositoriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Provides
    @Singleton
    fun provideRepositoriesApi(okHttpCallFactory: Call.Factory): RepositoriesApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(okHttpCallFactory)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(RepositoriesApi::class.java)
    }
}
