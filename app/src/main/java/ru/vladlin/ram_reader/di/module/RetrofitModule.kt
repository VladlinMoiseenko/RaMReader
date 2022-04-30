package ru.vladlin.ram_reader.di.module

import com.squareup.moshi.Moshi
import ru.vladlin.ram_reader.data.source.retrofit.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.vladlin.ram_reader.BuildConfig
import ru.vladlin.ram_reader.di.MoshiDefault
import ru.vladlin.ram_reader.di.MoshiNetwork
import ru.vladlin.ram_reader.di.scope.AppScope

@Module
class RetrofitModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @AppScope
    fun provideHttpLogginInteractor(): HttpLoggingInterceptor {
        val httpInteractor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            httpInteractor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpInteractor.level = HttpLoggingInterceptor.Level.NONE

        return httpInteractor
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Provides
    @AppScope
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @MoshiNetwork moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://rickandmortyapi.com/api/")
        .build()

    @Provides
    @MoshiDefault
    fun provideMoshi(
        builder: Moshi.Builder
    ): Moshi = builder.build()

    @Provides
    fun provideMoshiBuilder() = Moshi.Builder()

}
