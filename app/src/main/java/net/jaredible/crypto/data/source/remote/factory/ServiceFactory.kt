package net.jaredible.crypto.data.source.remote.factory

import net.jaredible.crypto.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    inline fun <reified T> newInstance(baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(getLogging()).build()
    }

    private fun getLogging() =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.RELEASE) HttpLoggingInterceptor.Level.NONE
            else HttpLoggingInterceptor.Level.BODY
        )

}