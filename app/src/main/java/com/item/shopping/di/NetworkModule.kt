package com.item.shopping.di

import com.item.shopping.BuildConfig
import com.item.shopping.data.source.remote.service.ShoppingService
import com.item.shopping.util.InternalSSLSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.conscrypt.Conscrypt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Security
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/**
 * Network 모듈.
 */
@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {

        /**
         * API Low버전 TLS 1.3 지원을 위한 InternalSSLFactory 적용
         */
        Security.insertProviderAt(Conscrypt.newProvider(), 1)

        val tm: X509TrustManager = Conscrypt.getDefaultX509TrustManager()
        val sslContext = SSLContext.getInstance("TLS", "Conscrypt")
        sslContext.init(null, arrayOf(tm), null)


        val client = OkHttpClient.Builder()
            .sslSocketFactory(InternalSSLSocketFactory(sslContext.socketFactory), tm)
            .connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(Level.BODY)
            client.addInterceptor(logging)
        }

        return client.build()
    }


    @Singleton
    @Provides
    fun provideShoppingRetrofit(
        okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_V1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideShoppingService(retrofit: Retrofit): ShoppingService {
        return retrofit.create(ShoppingService::class.java)
    }
}