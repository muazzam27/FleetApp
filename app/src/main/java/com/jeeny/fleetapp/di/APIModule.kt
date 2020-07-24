package com.jeeny.fleetapp.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jeeny.fleetapp.netwrok.FleetAPI
import com.jeeny.fleetapp.netwrok.FleetApiService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [AppModule::class])
open class APIModule {

    @Provides
    open fun provideFleetAPI(client:OkHttpClient):FleetAPI{
        return Retrofit.Builder()
            .baseUrl("https://pastebin.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FleetAPI::class.java)
    }

    @Provides
    open fun provideAPIService(api: FleetAPI):FleetApiService = FleetApiService(api)

    @Provides
    internal fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    internal fun provideOKhttpClient(cache: Cache):OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()

        client.cache(cache)
        client.addInterceptor(interceptor)
        client.connectTimeout(5000, TimeUnit.SECONDS)
        client.writeTimeout(5000, TimeUnit.SECONDS)
        client.readTimeout(5000, TimeUnit.SECONDS)
        return client.build()
    }

}