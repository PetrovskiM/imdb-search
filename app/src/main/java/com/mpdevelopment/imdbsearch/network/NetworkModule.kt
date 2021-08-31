package com.mpdevelopment.imdbsearch.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mpdevelopment.imdbsearch.BuildConfig
import com.mpdevelopment.imdbsearch.model.MovieDetails
import com.mpdevelopment.imdbsearch.network.adapter.MovieDetailsTypeAdapter
import com.mpdevelopment.imdbsearch.network.interceptor.AuthInterceptor
import com.mpdevelopment.imdbsearch.network.interceptor.ErrorInterceptor
import com.mpdevelopment.imdbsearch.network.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("baseUrl")
    fun provideApiBaseUrl(): String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(MovieDetails::class.java, MovieDetailsTypeAdapter())
        .create()

    @Provides
    fun provideOkHttpClient(
        @Named("log") loggingInterceptor: Interceptor,
        @Named("error") errorInterceptor: Interceptor,
        @Named("auth") authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(errorInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @Named("log")
    fun provideLogInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    @Named("error")
    fun provideErrorInterceptor(): Interceptor =
        ErrorInterceptor()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthInterceptor(): Interceptor =
        AuthInterceptor()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

/* Error Subject / Flow mentioned in [ErrorInterceptor]
    @Provides
    @Singleton
    @ErrorStream
    fun provideApiErrorStream(): MutableSharedFlow<ApiError> = MutableSharedFlow()

    @Provides
    @Singleton
    @ErrorStream
    fun provideApiErrorStreamFlow(@ErrorStream stream: MutableSharedFlow<ApiError>):  Flow<ApiError> =
        mutableFlow.asSharedFlow()
*/

}